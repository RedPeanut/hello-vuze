package hello.run;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import gudy.azureus2.core3.util.SystemProperties;
import hello.util.AzSettings;
import hello.util.Log;

public class RunSashView {
	
	private static String TAG = RunSashView.class.getSimpleName();
	
	public static void main(String[] args) {
		try {
			new RunSashView().exec(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exec(String[] args) throws Exception {
		
		Log.d(TAG, "System.getProperty(\"os.name\") = " + System.getProperty("os.name"));
		Log.d(TAG, "System.getProperty(\"user.home\") = " + System.getProperty("user.home"));
		Log.d(TAG, "System.getProperty(\"file.separator\") = " + System.getProperty("file.separator"));
		
		/*System.out.println("===== print system properties : start =====");
		Properties p = System.getProperties();
		Enumeration keys = p.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = (String) p.get(key);
			System.out.println(key + " = " + value);
		}
		System.out.println("===== print system properties : end =====");*/
		
		String userPath = SystemProperties.getUserPath();
		Log.d(TAG, "userPath = " + userPath);
		
		Display display = new Display();
		
		Log.d(TAG, "display = " + display);
		Display _current = Display.getCurrent();
		Display _default = Display.getDefault();
		Log.d(TAG, "_current = " + _current);
		Log.d(TAG, "_default = " + _default);
		
		Shell shell = new Shell(display);
		shell.setText("Hello, world!");
		
		//Point minimumSize = shell.getMinimumSize();
		//System.out.println("minimumSize = " + minimumSize);
		
		//Composite composite = shell;
		//shell.setLayout(new FormLayout());
		
		//Log.d(TAG, "[1] shell.getLocation() = " + shell.getLocation());
		//Log.d(TAG, "[1] shell.getSize() = " + shell.getSize());
		
		//initialize(shell);
		//refresh();
		initShell(shell);
		initSidebar(shell);
		
		//Log.d(TAG, "[2] shell.getLocation() = " + shell.getLocation());
		//Log.d(TAG, "[2] shell.getSize() = " + shell.getSize());
		
		shell.open();
		
		//Log.d(TAG, "[2] shell.getLocation() = " + shell.getLocation());
		//Log.d(TAG, "[2] shell.getSize() = " + shell.getSize());
		
		// Set up the event loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in the event queue
				display.sleep();
			}
		}
		Log.d(TAG, ">>> outer loop");
		display.dispose();
	}
	
	private void initShell(final Composite shell) throws Exception {
		
		String[] locations = AzSettings.get(AzSettings.KEY_MAIN_LOCATION).split(",");
		String[] sizes = AzSettings.get(AzSettings.KEY_MAIN_SIZE).split(",");
		int x = Integer.parseInt(locations[0]);
		int y = Integer.parseInt(locations[1]);
		int w = Integer.parseInt(sizes[0]);
		int h = Integer.parseInt(sizes[1]);
		shell.setLocation(new Point(x, y));
		shell.setSize(new Point(w, h));
		
		shell.setLayout(new FormLayout());
		
		Listener l = new Listener() {
			public void handleEvent(Event event) {
				if (event.type == SWT.Close) {
					Log.d(TAG, "SWT.Close is occured");
					try {
						Point location = shell.getLocation();
						Point size = shell.getSize();
						AzSettings.set(AzSettings.KEY_MAIN_LOCATION, location.x+","+location.y);
						AzSettings.set(AzSettings.KEY_MAIN_SIZE, size.x+","+size.y);
					} catch (Exception _e) {
						_e.printStackTrace();
					}
				} 
			}
		};
		//shell.getShell().addListener(SWT.Activate, l);
		shell.getShell().addListener(SWT.Close, l);
	}
	
	private void initSidebar(Composite parent) throws Exception {
		//int valMainX = Integer.parseInt(AzSettings.get(AzSettings.KEY_MAIN_X));
		//int valMainY = Integer.parseInt(AzSettings.get(AzSettings.KEY_MAIN_Y));
		//int valMainW = Integer.parseInt(AzSettings.get(AzSettings.KEY_MAIN_W));
		//int valMainH = Integer.parseInt(AzSettings.get(AzSettings.KEY_MAIN_H));
		int valSidebarLeft = Integer.parseInt(AzSettings.get(AzSettings.KEY_SIDEBAR_LEFT));
		//int valTabNum = Integer.parseInt(AzSettings.get(AzSettings.KEY_BOTTOM_TAB_NUM));
		String valBottomTabItems = AzSettings.get(AzSettings.KEY_BOTTOM_TAB_ITEMS);
		int valBootomTabTop = Integer.parseInt(AzSettings.get(AzSettings.KEY_BOTTOM_TAB_TOP));
		//Log.d(TAG, "valMainX = " + valMainX);
		//Log.d(TAG, "valMainY = " + valMainY);
		//Log.d(TAG, "valMainW = " + valMainW);
		//Log.d(TAG, "valMainH = " + valMainH);
		Log.d(TAG, "valSidebarLeft = " + valSidebarLeft);
		Log.d(TAG, "valBottomTabItems = " + valBottomTabItems);
		Log.d(TAG, "valBootomTabTop = " + valBootomTabTop);
		
		// Create the sash first, so the other controls
		// can be attached to it.
		final Sash sash = new Sash(parent, SWT.VERTICAL);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		//Rectangle parentArea = sash.getParent().getClientArea();
		//Log.d(TAG, "parentArea = " + parentArea);
		//Log.d(TAG, "valSidebarLeft * 100 / parentArea.width = " + valSidebarLeft * 100 / parentArea.width);
		//data.left = new FormAttachment(valSidebarLeft * 100 / parentArea.width, 0);
		data.left = new FormAttachment(0, valSidebarLeft);
		sash.setLayoutData(data);

		//int valSidebarLeft = Integer.parseInt(AzSettings.get(AzSettings.KEY_SIDEBAR_LEFT));
		sash.setData(AzSettings.KEY_SIDEBAR_LEFT, valSidebarLeft);
		
		Listener l = new Listener() {
			public void handleEvent(Event event) {
				
				if (event.type == SWT.MouseDown) {
					//Log.d(TAG, "SWT.MouseDown is occured");
					//Log.d(TAG, "e = " + event);
					/*Throwable t = new Throwable();
					t.printStackTrace();*/
				} else if (event.type == SWT.MouseMove) {
					//Log.d(TAG, "SWT.MouseMove is occured");
					//Log.d(TAG, "e = " + e);
				} else if (event.type == SWT.MouseUp) {
					//Log.d(TAG, "SWT.MouseUp is occured");
					//Log.d(TAG, "e = " + event);
				} else if (event.type == SWT.Selection) {
					/*if (event.detail == SWT.DRAG)
						return;
					
					Log.d(TAG, "SWT.Selection is occured");
					Log.d(TAG, "e = " + event);*/
					
					Rectangle parentArea = sash.getParent().getClientArea();
					FormData formData = (FormData) sash.getLayoutData();
					formData.left = new FormAttachment(0, event.x);
					sash.getParent().layout();
					
					sash.setData(AzSettings.KEY_SIDEBAR_LEFT, new Long(event.x));
					
				} else if (event.type == SWT.Show) {
					Log.d(TAG, "SWT.Show is occured");
				} else if (event.type == SWT.Hide) {
					Log.d(TAG, "SWT.Hide is occured");
				}/* else if (event.type == SWT.Activate) {
					Log.d(TAG, "SWT.Activate is occured");
					try {
						int valSidebarLeft = Integer.parseInt(AzSettings.get(AzSettings.KEY_SIDEBAR_LEFT));
						sash.setData(AzSettings.KEY_SIDEBAR_LEFT, valSidebarLeft);
					} catch (Exception _e) {
						_e.printStackTrace();
					}
				} else if (event.type == SWT.Deactivate) {
					Log.d(TAG, "SWT.Deactivate is occured");
				} */else if (event.type == SWT.Close) {
					Log.d(TAG, "SWT.Close is occured");
					try {
						AzSettings.set(AzSettings.KEY_SIDEBAR_LEFT, sash.getData(AzSettings.KEY_SIDEBAR_LEFT).toString());
					} catch (Exception _e) {
						_e.printStackTrace();
					}
				} 
			}
		};
		sash.addListener(SWT.Selection, l);
		//sash.addListener(SWT.MouseDown, l);
		//sash.addListener(SWT.MouseMove, l);
		//sash.addListener(SWT.MouseUp, l);
		//sash.getShell().addListener(SWT.Show, l);
		//sash.getShell().addListener(SWT.Hide, l);
		//sash.getShell().addListener(SWT.Activate, l);
		sash.getShell().addListener(SWT.Close, l);
		
		Composite left = new Composite(parent, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(sash, 0);
		left.setLayoutData(data);
		
		Composite right = new Composite(parent, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(sash, 0);
		data.right = new FormAttachment(100, 0);
		right.setLayoutData(data);
		
		//right.setLayout(new FormLayout());
		//parent.layout();
		
		//Log.d(TAG, "left.getClientArea() = " + left.getClientArea());
		//Log.d(TAG, "right.getClientArea() = " + right.getClientArea());
		
		createContents(right);
		
		/*
		// Create the first text box and attach its right edge
		// to the sash
		Text one = new Text(parent, SWT.BORDER);
		one.setText("one...");
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(sash, 0);
		one.setLayoutData(data);

		// Create the second text box and attach its left edge
		// to the sash
		Text two = new Text(parent, SWT.BORDER);
		two.setText("two...");
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(sash, 0);
		data.right = new FormAttachment(100, 0);
		two.setLayoutData(data);
		//*/
		
		//new Composite(parent);
		
	}
	
	private void createContents(Composite parent) throws Exception {
		
		parent.setLayout(new FormLayout());
		
		int valBottomTabTop = Integer.parseInt(AzSettings.get(AzSettings.KEY_BOTTOM_TAB_TOP));
		
		final Sash sash = new Sash(parent, SWT.HORIZONTAL);
		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, valBottomTabTop);
		sash.setLayoutData(data);

		//int valSidebarLeft = Integer.parseInt(AzSettings.get(AzSettings.KEY_SIDEBAR_LEFT));
		sash.setData(AzSettings.KEY_BOTTOM_TAB_TOP, valBottomTabTop);
		
		Listener l = new Listener() {
			public void handleEvent(Event event) {
				
				if (event.type == SWT.Selection) {
					//Rectangle parentArea = sash.getParent().getClientArea();
					FormData formData = (FormData) sash.getLayoutData();
					formData.top = new FormAttachment(0, event.y);
					sash.getParent().layout();
					
					sash.setData(AzSettings.KEY_BOTTOM_TAB_TOP, new Long(event.y));
				} else if (event.type == SWT.Close) {
					Log.d(TAG, "SWT.Close is occured");
					try {
						AzSettings.set(AzSettings.KEY_BOTTOM_TAB_TOP, sash.getData(AzSettings.KEY_BOTTOM_TAB_TOP).toString());
					} catch (Exception _e) {
						_e.printStackTrace();
					}
				} 
			}
		};
		sash.addListener(SWT.Selection, l);
		sash.getShell().addListener(SWT.Close, l);
		
		Composite top = new Composite(parent, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.bottom = new FormAttachment(sash, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		top.setLayoutData(data);
		
		
		
		Composite bottom = new Composite(parent, SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(sash, 0);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		bottom.setLayoutData(data);
		
		
	}
	
	private void initBootomTab(Composite parent) throws Exception {
		
	}

	private void initChild(Composite parent) throws Exception {
		
	}
	
	private void refresh() {
		
	}
}
