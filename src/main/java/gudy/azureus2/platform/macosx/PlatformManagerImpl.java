package gudy.azureus2.platform.macosx;

import java.io.File;

import gudy.azureus2.core3.util.SystemProperties;
import gudy.azureus2.platform.PlatformManager;

public class PlatformManagerImpl implements PlatformManager {
	
	public String getUserDataDirectory() {
		return new File(System.getProperty("user.home")
				+ "/Library/Application Support/"
				+ SystemProperties.APPLICATION_NAME).getPath()
				+ SystemProperties.SEP;
	}

	@Override
	public File getLocation(long locationId) {
		switch ((int)locationId) {
			case LOC_USER_DATA:
				return new File(getUserDataDirectory());
			default:
				return null;
		}
	}
}
