package hello.util;

/**
 * 가변 설정값 읽기/저장 래퍼 클래스
 * 
 * @author 김진규
 * @since 2017-10-17
 * @see 
 * <pre>
 * 수정일				수정자	수정내용
 * ----------		------	---------------------------
 * 2017. 10. 17.	김진규	최초생성
 * 2019.  2.  7.	김진규	코어,래퍼 분리
 * </pre>
 */
public class AzSettings {
	
	/*public static final String KEY_MAIN_X				= "az.main.x";
	public static final String KEY_MAIN_Y				= "az.main.y";
	public static final String KEY_MAIN_W				= "az.main.w";
	public static final String KEY_MAIN_H				= "az.main.h";*/
	public static final String KEY_MAIN_LOCATION		= "az.main.location";
	public static final String KEY_MAIN_SIZE			= "az.main.size";
	public static final String DEF_MAIN_LOCATION		= "0,34";
	public static final String DEF_MAIN_SIZE			= "900,563";
	
	public static final String KEY_SIDEBAR_LEFT			= "az.sidebar.left";
	public static final String DEF_SIDEBAR_LEFT			= "225";
	
	//public static final String KEY_BOTTOM_TAB_NUM 	= "az.bottom.tab.num";
	public static final String KEY_BOTTOM_TAB_ITEMS		= "az.bottom.tab.items";
	public static final String DEF_BOTTOM_TAB_ITEMS		= "ConsoleView|";
	public static final String KEY_BOTTOM_TAB_TOP 		= "az.bottom.tab.top";
	public static final String DEF_BOTTOM_TAB_TOP 		= "359";
	
	public static void set(String key, String value) throws Exception {
		AzSettingsCore.getInstance().set(key, value);
	}
	
	public static void set(String key, boolean value) throws Exception {
		AzSettingsCore.getInstance().set(key, value);
	}
	
	public static void set(String key, int value) throws Exception {
		AzSettingsCore.getInstance().set(key, value);
	}

	public static void set(String key, float value) throws Exception {
		AzSettingsCore.getInstance().set(key, value);
	}
	
	public static String get(String key) throws Exception {
		return AzSettingsCore.getInstance().get(key); 
	}
	
	public static String get(String key, String defValue) throws Exception {
		return AzSettingsCore.getInstance().get(key, defValue); 
	}
}
