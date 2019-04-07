package aelitis.azureus.ui.swt.utils;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

public class ColorCache {
	
	public static Color getColor(Device device, int red, int green, int blue) {
		if (device == null || device.isDisposed())
			return null;
		
		Color color = new Color(device, red, green, blue);
		return color;
	}
}
