package gudy.azureus2.core3.util;

import java.util.List;
import java.util.Map;

public class BEncoder {

	public static byte[] encode(Map object) {
		BEncoder encoder = new BEncoder();
		encoder.encodeObject(object);
		return encoder.toByteArray();
	}

	private byte[] toByteArray() {
		return null;
	}

	private void encodeObject(Object object) {
		if (object instanceof String || object instanceof Float || object instanceof Double) {
			
		} else if (object instanceof Map) {
			
		} else if (object instanceof List) {
			
		}
	}
	
}
