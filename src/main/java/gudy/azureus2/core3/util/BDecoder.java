package gudy.azureus2.core3.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Map;

public class BDecoder {

	private static final int MAX_MAP_KEY_SIZE		= 64*1024;
	
	// reuseable objects for key decoding
	private ByteBuffer keyBytesBuffer = ByteBuffer.allocate(32);
	private CharBuffer keyCharsBuffer = CharBuffer.allocate(32);
	private final CharsetDecoder keyDecoder = Constants.BYTE_CHARSET.newDecoder();
	
	public Map decodeStream(BufferedInputStream data) throws Exception {
		return (Map<String, Object>) decodeInputStream(data, "", 0);
	}

	private Object decodeInputStream(InputStream is, String context, int nesting) throws Exception {
		is.mark(1);
		int read = is.read();
		switch (read) {
			case 'd': {
				//create a new dictionary object
				LightHashMap map = new LightHashMap();

				byte[] prevKey = null;
				//get the key
				while (true) {
					is.mark(1);
					read = is.read();
					if (read == 'e' || read == -1)
						break; // end of map
					is.reset();
					// decode key strings manually so we can reuse the bytebuffer
					int keyLength = (int)getPositiveNumberFromStream(is, ':');
					int skipBytes = 0;
					if (keyLength > MAX_MAP_KEY_SIZE) {
						skipBytes = keyLength - MAX_MAP_KEY_SIZE;
						keyLength = MAX_MAP_KEY_SIZE;
						//new Exception().printStackTrace();
						//throw (new IOException( msg));
					}
					if (keyLength < keyBytesBuffer.capacity()) {
						keyBytesBuffer.position(0).limit(keyLength);
						keyCharsBuffer.position(0).limit(keyLength);
					} else {
						keyBytesBuffer = ByteBuffer.allocate(keyLength);
						keyCharsBuffer = CharBuffer.allocate(keyLength);
					}
					getByteArrayFromStream(is, keyLength, keyBytesBuffer.array());
					if (skipBytes > 0) {
						is.skip(skipBytes);
					}
					
					keyDecoder.reset();
					keyDecoder.decode(keyBytesBuffer,keyCharsBuffer,true);
					keyDecoder.flush(keyCharsBuffer);
					String key = new String(keyCharsBuffer.array(),0,keyCharsBuffer.limit());

					//decode value
					Object value = decodeInputStream(is,key,nesting+1);
					
					// recover from some borked encodings that I have seen whereby the value has
					// not been encoded. This results in, for example,
					// 18:azureus_propertiesd0:e
					// we only get null back here if decoding has hit an 'e' or end-of-file
					// that is, there is no valid way for us to get a null 'value' here
					if (value == null) {
						System.err.println("Invalid encoding - value not serialsied for '" + key + "' - ignoring: map so far=" + map + ",loc=" + Debug.getCompressedStackTrace());
						break;
					}
					if (skipBytes > 0) {
						String msg = "dictionary key is too large - "
								+ (keyLength + skipBytes) + ":, max=" + MAX_MAP_KEY_SIZE
								+ ": skipping key starting with " + new String(key.substring(0, 128));
						System.err.println(msg);
					} else {
						if (map.put(key, value) != null) {
							Debug.out("BDecoder: key '" + key + "' already exists!");
						}
					}
				}
				
				is.mark(1);
				read = is.read();
				is.reset();
				if (nesting > 0 && read == -1) {
					throw (new BEncodingException("BDecoder: invalid input data, 'e' missing from end of dictionary"));
				}
				//map.compactify(-0.9f);
				//return the map
				return map;
			}
			case 'l':
				//create the list
				ArrayList list = new ArrayList();

				Object tempElement = null;
				while ((tempElement = decodeInputStream(is, context, nesting+1)) != null) {
					//add the element
					list.add(tempElement);
				}
				list.trimToSize();
				is.mark(1);
				read = is.read();
				is.reset();
				if (nesting > 0 && read == -1) {
					throw (new BEncodingException("BDecoder: invalid input data, 'e' missing from end of list"));
				}
				
				//return the list
				return list;
			case 'e':
			case -1:
				return null;
			case 'i':
				return Long.valueOf(getNumberFromStream(is, 'e'));
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				//move back one
				is.reset();
				//get the string
				return getByteArrayFromStream(is, context);
			default: {
				int	remLen = is.available();
				if (remLen > 256) {
					remLen = 256;
				}
				byte[] remData = new byte[remLen];
				is.read(remData);
				throw (new BEncodingException(
						"BDecoder: unknown command '" + read + ", remainder = " + new String(remData)));
			}
		}
	}

	private Object getByteArrayFromStream(InputStream is, String context) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getNumberFromStream(InputStream is, char c) {
		// TODO Auto-generated method stub
		return null;
	}

	private void getByteArrayFromStream(InputStream is, int keyLength, byte[] array) {
		// TODO Auto-generated method stub
		
	}

	private int getPositiveNumberFromStream(InputStream is, char c) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
