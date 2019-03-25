package hello.util;

public class Util {

	public static void printStackTrace() {
		StackTraceElement[] trace = new Throwable().getStackTrace();
		for (StackTraceElement traceElement: trace)
			System.out.println("\tat " + traceElement);
	}

	private static char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	public static String toHexString(byte[] bytes) {
		String s = "";
		for (byte b: bytes) {
			s += hexDigits[b/16];
			s += hexDigits[b%16];
		}
		return s;
	}
}
