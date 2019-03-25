package gudy.azureus2.ui.swt.mainwindow;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class Colors {
	
	public static Color black;
	public static Color light_grey;
	public static Color dark_grey;
	public static Color blue;
	public static Color green;
	public static Color fadedGreen;
	public static Color grey;
	public static Color red;
	public static Color fadedRed;
	public static Color yellow;
	public static Color fadedYellow;
	public static Color white;
	public static Color background;
	
	private static Colors instance;
	Display display;
	
	private Colors() {
		display = Display.getCurrent();
		allocateColors();
	}
	
	public static Colors getInstance() {
		if (instance == null)
			instance = new Colors();
		return instance;
	}
	
	private void allocateColors() {
		black 		= new Color(display, 0, 0, 0);
		light_grey 	= new Color(display, 192, 192, 192);
		dark_grey 	= new Color(display, 96, 96, 96);
		blue 		= new Color(display, 0, 0, 170);
		green 		= new Color(display, 0, 170, 0);
		fadedGreen 	= new Color(display, 96, 160, 96);
		grey 		= new Color(display, 170, 170, 170);
		red 		= new Color(display, 255, 0, 0);
		fadedRed 	= new Color(display, 160, 96, 96);
		yellow 		= new Color(display, 255, 255, 0);
		fadedYellow = new Color(display, 255, 255, 221);
		white 		= new Color(display, 255, 255, 255);
		background 	= new Color(display, 248, 248, 248);
	}
}
