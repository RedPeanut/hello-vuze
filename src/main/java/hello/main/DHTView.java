package hello.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import gudy.azureus2.ui.swt.Utils;
import gudy.azureus2.ui.swt.components.graphics.SpeedGraphic;

public class DHTView {
	public static void main(String[] args) {
		try {
			new DHTView().exec(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exec(String[] args) {
		Display display = new Display();
		
		Shell shell = new Shell(display);
		shell.setText("Hello, world!");
		
		Point minimumSize = shell.getMinimumSize();
		System.out.println("minimumSize = " + minimumSize);
		
		//Composite composite = shell;
		//shell.setLayout(new FormLayout());
		initialize(shell);
		//refresh();
		shell.open();
		
		// Set up the event loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in the event queue
				display.sleep();
			}
		}
		display.dispose();
	}
	
	Composite panel;
	Canvas in, out;
	SpeedGraphic inGraph, outGraph;
	
	private void initialize(Composite parent) {
		panel = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		panel.setLayout(layout);
		
		Group gTransport = new Group(panel, SWT.NONE);
		GridData data = new GridData(GridData.FILL_VERTICAL);
		data.widthHint = 350;
		data.verticalSpan = 2;
		Utils.setLayoutData(gTransport, data);
		
		layout = new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = true;
		gTransport.setLayout(layout);
		
		inGraph = new SpeedGraphic();
		in = new Canvas(gTransport,SWT.NO_BACKGROUND);
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 3;
		Utils.setLayoutData(in, data);
		inGraph.initialize(in);
	}
	
	private void refresh() {
		
	}
}
