package gudy.azureus2.plugins.platform;

import java.io.File;

public interface PlatformManager {
	
	public static final int	LOC_USER_DATA	= 1;
	
	public File	getLocation(long locationId);
}
