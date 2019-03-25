package hello.util;

public class LangUtil {
	public static String nvl(String src, String ret) {
		return src != null && !src.equals("null") && !src.equals("") ? src : ret;
	}
}
