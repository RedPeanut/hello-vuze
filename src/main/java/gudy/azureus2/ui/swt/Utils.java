package gudy.azureus2.ui.swt;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import gudy.azureus2.core3.util.Constants;

public class Utils {

	private static String TAG = Utils.class.getSimpleName();
	
	private static final int DEFAULT_DPI = Constants.isOSX ? 72 : 96;
	
	public static int adjustPXForDPI(int unadjustedPX) {
		if (unadjustedPX == 0) {
			return unadjustedPX;
		}
		int xDPI = getDPI().x;
		if (xDPI == 0) {
			return unadjustedPX;
		}
		return unadjustedPX * xDPI / DEFAULT_DPI;
	}
	
	//private static Point dpi;
	private static Point getDPI() {
		Display _current = Display.getCurrent();
		Display _default = Display.getDefault();
		Point dpi = getDPIRaw(_default);
		return dpi;
	}
	
	public static Point	getDPIRaw(Device device) {
		Point p = device.getDPI();
		if (p.x < 0 || p.y < 0 || p.x > 8192 || p.y > 8192) {
			return (new Point(96, 96));
		}
		return (p);
	}
	
	
	
	public static void setLayoutData(Control widget, GridData layoutData) {
		//adjustPXForDPI(layoutData);
		widget.setLayoutData(layoutData);
	}
	
	public static FormData getFilledFormData() {
		FormData formData = new FormData();
		formData.top = new FormAttachment(0, 0);
		formData.left = new FormAttachment(0, 0);
		formData.right = new FormAttachment(100, 0);
		formData.bottom = new FormAttachment(100, 0);
		return formData;
	}
	
	public static void setLayout(Composite composite, RowLayout layout) {
		composite.setLayout(layout);
	}
	
}
