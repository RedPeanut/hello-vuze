package gudy.azureus2.core3.util;

import java.io.File;

import gudy.azureus2.platform.PlatformManager;
import gudy.azureus2.platform.PlatformManagerFactory;

public class SystemProperties {
	
	public static final String SEP = System.getProperty("file.separator");
	
	public static final String	AZ_APP_ID	= "az";
	public static String APPLICATION_NAME 	= "HelloAzureus";
	private static String APPLICATION_ID 	= AZ_APP_ID;
	
	private static final String WIN_DEFAULT = "Application Data";
	private static final String OSX_DEFAULT = "Library" + SEP + "Application Support";
	
	private static String userPath;
	
	/**
	 * Under unix, ~/.azureus
	 * Under Windows, .../Documents and Settings/username/Application Data/Azureus
	 * Under OSX, /Users/username/Library/Application Support/
	 * @return
	 * @throws Exception 
	 */
	public static String getUserPath() throws Exception {
		
		if (userPath != null)
			return userPath;
		
		String tempUserPath = null;
		try {
			PlatformManager platformManager = PlatformManagerFactory.getPlatformManager();
			File loc = platformManager.getLocation(PlatformManager.LOC_USER_DATA);
			tempUserPath = loc.getPath() + SEP;
			
			//if the directory doesn't already exist, create it
			File dir = new File(tempUserPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return tempUserPath;
		} finally {
			userPath = tempUserPath;
		}
	}

}
