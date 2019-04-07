package gudy.azureus2.core3.internat;

import java.util.Locale;
import java.util.ResourceBundle;

import hello.util.Log;

public class MessageText {
	
	private static String TAG = MessageText.class.getSimpleName();
	
	public static final Locale LOCALE_DEFAULT = Locale.getDefault();
	
	private static ResourceBundle RESOURCE_BUNDLE;
	
	static {
		getResourceBundle();
	}
	
	private static void getResourceBundle() {
		
		String name = "gudy.azureus2.internat.MessagesBundle";
		Locale loc = new Locale("ko", "KR");
		ClassLoader cl = MessageText.class.getClassLoader();
		
		Log.d(TAG, "loc = " + loc);
		
		RESOURCE_BUNDLE = ResourceBundle.getBundle(name, loc, cl);
	}
	
	public static String getString(String key) {
		if (key == null)
			return "";
		return RESOURCE_BUNDLE.getString(key);
	}

}
