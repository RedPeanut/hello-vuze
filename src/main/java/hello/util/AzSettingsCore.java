package hello.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import gudy.azureus2.core3.util.SystemProperties;

/**
 * 가변 설정값 읽기/저장 클래스
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
public class AzSettingsCore {
	
	//private static File propertyFile;
	private static String defaultPropertiesPath = "";
	private static Properties props = null;
	
	private static AzSettingsCore instance;
	public static AzSettingsCore getInstance() throws Exception {
		if (instance == null) {
			instance = new AzSettingsCore();
			instance.init();
		}
		return instance;
	}
	
	protected void init() throws Exception {
		
		defaultPropertiesPath = SystemProperties.getUserPath() + "settings.properties";
		
		boolean bFirst = false;
		
		File dir = new File(FileUtil.getDir(defaultPropertiesPath));
		if (!dir.exists())
			dir.mkdirs();
		
		File file = new File(defaultPropertiesPath);
		if (!file.exists()) {
			bFirst = true;
			file.createNewFile();
		}
		
		Properties p = new Properties();
		InputStream is = new FileInputStream(new File(defaultPropertiesPath));
		p.load(is);
		is.close();
		props = p;
		
		//if (bFirst) setToDefault();
	}
	
	/*private void setToDefault() throws Exception {
		//set(AzSettings.KEY_MAIN_X, "10");
		//set(AzSettings.KEY_MAIN_Y, "10");
		//set(AzSettings.KEY_MAIN_W, "400");
		//set(AzSettings.KEY_MAIN_H, "300");
		set(AzSettings.KEY_MAIN_LOCATION, AzSettings.DEF_MAIN_LOCATION);
		set(AzSettings.KEY_MAIN_SIZE, AzSettings.DEF_MAIN_SIZE);
		set(AzSettings.KEY_SIDEBAR_LEFT, AzSettings.DEF_SIDEBAR_LEFT);
		set(AzSettings.KEY_BOTTOM_TAB_ITEMS, AzSettings.DEF_BOTTOM_TAB_ITEMS);
		set(AzSettings.KEY_BOTTOM_TAB_TOP, AzSettings.KEY_BOTTOM_TAB_TOP);
	}*/
	
	private void setToRestore() {
	}
	
	public void set(String key, String value) throws Exception {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(defaultPropertiesPath);
			props.put(key, value);
			props.store(os, "");
		} finally {
			os.close();
		}
	}
	
	public void set(String key, boolean value) throws Exception {
		set(key, String.valueOf(value));
	}
	
	public void set(String key, int value) throws Exception {
		set(key, String.valueOf(value));
	}

	public void set(String key, float value) throws Exception {
		set(key, String.valueOf(value));
	}
	
	public String get(String key, String defValue) {
		return LangUtil.nvl(props.getProperty(key), defValue); 
	}
	
	public String get(String key) {
		String defValue = AzSettingsDefault.getInstance().get(key);
		return LangUtil.nvl(props.getProperty(key), defValue); 
	}
}
