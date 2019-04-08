package hello.run;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class RunDHT {
	
	public static void main(String[] args) {
		try {
			new RunDHT().exec(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exec(String[] args) {
		Display display = new Display();
		
		Shell shell = new Shell(display);
		shell.setText("Hello, world!");
		
		shell.setLayout(new FormLayout());
		initialize(shell);
		shell.pack();
		refresh();
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
	
	private void initialize(Composite parent) {
		panel = new Composite(parent,SWT.NULL);
	}

	private void refresh() {
	}
}
