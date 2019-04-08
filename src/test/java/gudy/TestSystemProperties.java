package gudy;

import org.junit.Test;

public class TestSystemProperties {
	
	String SEP = System.getProperty("file.separator");
	
	public static final String OSName = System.getProperty("os.name");
	
	public static final boolean isOSX			= OSName.toLowerCase().startsWith("mac os");
	public static final boolean isLinux			= OSName.equalsIgnoreCase("Linux");
	public static final boolean isSolaris		= OSName.equalsIgnoreCase("SunOS");
	public static final boolean isFreeBSD		= OSName.equalsIgnoreCase("FreeBSD");
	public static final boolean isWindowsXP		= OSName.equalsIgnoreCase("Windows XP");
	public static final boolean isWindows95		= OSName.equalsIgnoreCase("Windows 95");
	public static final boolean isWindows98		= OSName.equalsIgnoreCase("Windows 98");
	public static final boolean isWindows2000	= OSName.equalsIgnoreCase("Windows 2000");
	public static final boolean isWindowsME		= OSName.equalsIgnoreCase("Windows ME");
	public static final boolean isWindows9598ME	= isWindows95 || isWindows98 || isWindowsME;
	
	@Test
	public void testSystemProperties() {
		System.out.println("OSName = " + OSName);
	}
}
