package hello.util;

import java.util.HashMap;

public class AzSettingsDefault {
	
	private HashMap<String, String> def = new HashMap<String, String>();
	private static AzSettingsDefault instance = null;
	public static AzSettingsDefault getInstance() {
		if (instance == null)
			instance = new AzSettingsDefault();
		return instance;
	}
	private AzSettingsDefault() {
		load();
	}
	
	private void load() {
		def.put(AzSettings.KEY_MAIN_LOCATION, AzSettings.DEF_MAIN_LOCATION);
		def.put(AzSettings.KEY_MAIN_SIZE, AzSettings.DEF_MAIN_SIZE);
		def.put(AzSettings.KEY_SIDEBAR_LEFT, AzSettings.DEF_SIDEBAR_LEFT);
		def.put(AzSettings.KEY_BOTTOM_TAB_ITEMS, AzSettings.DEF_BOTTOM_TAB_ITEMS);
		def.put(AzSettings.KEY_BOTTOM_TAB_TOP, AzSettings.DEF_BOTTOM_TAB_TOP);
	}
	
	public String get(String key) {
		if (def.containsKey(key))
			return def.get(key);
		else 
			return "";
	}
}
