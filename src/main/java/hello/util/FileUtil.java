package hello.util;

import java.io.File;

public class FileUtil {
	public static String getDir(File file) {
		if (file != null && file.exists() && file.isFile()) {
			return getDir(file.getAbsolutePath());
		} else
			return null;
	}
	
	public static String getDir(String path) {
		path = path.replaceAll("\\\\", "/");
		int endIndex = path.lastIndexOf('/');
		return path.substring(0, endIndex);
	}
}

