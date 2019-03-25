package gudy.azureus2.platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import gudy.azureus2.core3.util.Constants;

public class PlatformManagerFactory {
	
	public static PlatformManager getPlatformManager() throws Exception {
		String cla;
		int platformType = getPlatformType();
		switch (platformType) {
			case PlatformManager.PT_WINDOWS:
				cla = "gudy.azureus2.platform.win32.PlatformManagerImpl";
				break;
			case PlatformManager.PT_MACOSX:
				cla = "gudy.azureus2.platform.macosx.PlatformManagerImpl";
				break;
			case PlatformManager.PT_UNIX:
				cla = "gudy.azureus2.platform.unix.PlatformManagerImpl";
				break;
			default:
				cla = "gudy.azureus2.platform.dummy.PlatformManagerImpl";
				break;
		}
		
		Class<?> platformManagerClass = Class.forName(cla);
		PlatformManager platformManager = null;

		try {
			Method methGetSingleton = platformManagerClass.getMethod("getSingleton");
			platformManager = (PlatformManager) methGetSingleton.invoke(null);
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		
		if (platformManager == null) {
			platformManager = (PlatformManager)Class.forName(cla).newInstance();
		}
		
		return (platformManager);
	}
	
	public static int getPlatformType() {
		if (Constants.isWindows) {
			return (PlatformManager.PT_WINDOWS);
		} else if (Constants.isOSX) {
			return (PlatformManager.PT_MACOSX);
		} else if (Constants.isUnix) {
			return (PlatformManager.PT_UNIX);
		} else {
			return (PlatformManager.PT_OTHER);
		}
	}
}
