package hello.run;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class RunTransferStatsView {
	public static void main(String[] args) {
		try {
			new RunTransferStatsView().exec(args);
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
		shell.setLayout(new FormLayout());
		initialize(shell);
		//refresh();
		shell.pack();
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
	
	Composite mainPanel;
	
	private void initialize(Composite parent) {
		mainPanel = new Composite(parent, SWT.NULL);
		GridLayout mainLayout = new GridLayout();
		mainPanel.setLayout(mainLayout);
		
		
	}
	
	private void refresh() {
		
	}
}
