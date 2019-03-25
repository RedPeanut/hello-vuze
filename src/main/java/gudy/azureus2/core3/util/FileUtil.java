package gudy.azureus2.core3.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;

public class FileUtil {

	public static Map readResilientConfigFile(String filename) throws Exception {
		File dir = new File(SystemProperties.getUserPath());
		return readResilientFile(dir, filename);
	}

	private static Map readResilientFile(File parentDir, String filename) throws Exception {
		File file = new File(parentDir, filename);
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file), 16384);;
		BDecoder decoder = new BDecoder();
		Map	res = decoder.decodeStream(bin);
		return res;
	}

	public static void writeResilientConfigFile(String filename, Map data) throws Exception {
		File parentDir = new File(SystemProperties.getUserPath());
		writeResilientFile(parentDir, filename, data);
	}

	private static boolean writeResilientFile(File parentDir, String filename, Map data) throws Exception {
		
		File temp = new File(parentDir, filename + ".saving");
		BufferedOutputStream baos = null;
		byte[] encodedData = BEncoder.encode(data);
		FileOutputStream tempOS = new FileOutputStream(temp, false);
		baos = new BufferedOutputStream(tempOS, 8192);
		baos.write(encodedData);
		baos.flush();
		baos.close();
		baos = null;
		
		if (temp.length() > 1L) {
			File file = new File(parentDir, filename);
			if (file.exists()) {
				if (file.delete()) {
					if (temp.renameTo(file)) {
						return (true);
					}
				}
			}
		}
		
		return false;
	}

}
