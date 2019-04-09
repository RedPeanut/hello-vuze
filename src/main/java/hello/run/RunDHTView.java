package hello.run;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import gudy.azureus2.ui.swt.Messages;
import gudy.azureus2.ui.swt.Utils;
import gudy.azureus2.ui.swt.components.graphics.SpeedGraphic;
import gudy.azureus2.ui.swt.mainwindow.Colors;

public class RunDHTView {
	
	public static void main(String[] args) {
		try {
			new RunDHTView().exec(args);
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
		//FormLayout layout = new FormLayout();
		//shell.setLayout(layout);
		//shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		shell.setLayout(new FormLayout());
		
		Colors.getInstance();
		initialize(shell);

		shell.pack();
		refresh();
		
		shell.open();
		
		//shell.layout();
		//shell.setLayoutDeferred(false);
		//shell.setRedraw(true);
		
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
	
	Label lblUpTime,lblNumberOfUsers;
	Label lblKeys,lblValues,lblSize;
	
	Label lblReceivedPackets,lblReceivedBytes;
	
	private void initialize(Composite parent) {
		panel = new Composite(parent,SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		panel.setLayout(layout);
		
		initialiseGeneralGroup();
		initialiseDBGroup();
		
		initialiseTransportDetailsGroup();
	}
	
	private void initialiseGeneralGroup() {
		Group gGeneral = new Group(panel,SWT.NONE);
		gGeneral.setText("일반");
		
		GridData data = new GridData();
		data.verticalAlignment = SWT.BEGINNING;
		data.widthHint = 350;
		//Utils.setLayoutData(gGeneral, data);
		gGeneral.setLayoutData(data);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 6;
		gGeneral.setLayout(layout);
		
		Label label = new Label(gGeneral,SWT.NONE);
		label.setText("업시간:");
		
		lblUpTime = new Label(gGeneral,SWT.NONE);
		//Utils.setLayoutData(lblUpTime, new GridData(SWT.FILL,SWT.TOP,true,false));
		lblUpTime.setLayoutData(new GridData(SWT.FILL,SWT.TOP,true,false));
	}
	
	private void initialiseDBGroup() {
		Group gDB = new Group(panel,SWT.NONE);
		gDB.setText("데이터베이스");
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.verticalAlignment = SWT.FILL;
		Utils.setLayoutData(gDB, data);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 6;
		layout.makeColumnsEqualWidth = true;
		gDB.setLayout(layout);

		Label label = new Label(gDB,SWT.NONE);
		label.setText("키");
		
		lblKeys = new Label(gDB,SWT.NONE);
		Utils.setLayoutData(lblKeys, new GridData(SWT.FILL,SWT.TOP,true,false));
	}
	
	private void initialiseTransportDetailsGroup() {
		
		Group gTransport = new Group(panel,SWT.NONE);
		Messages.setLanguageText(gTransport,"DHTView.transport.title");
		
		GridData data = new GridData(GridData.FILL_VERTICAL);
		data.widthHint = 350;
		data.verticalSpan = 2;
		Utils.setLayoutData(gTransport, data);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = true;
		gTransport.setLayout(layout);
		
		Label label;
		
		label = new Label(gTransport,SWT.NONE); // blank

		label = new Label(gTransport,SWT.NONE);
		Messages.setLanguageText(label,"DHTView.transport.packets");
		Utils.setLayoutData(label, new GridData(SWT.FILL,SWT.TOP,true,false));

		label = new Label(gTransport,SWT.NONE);
		Messages.setLanguageText(label,"DHTView.transport.bytes");
		Utils.setLayoutData(label, new GridData(SWT.FILL,SWT.TOP,true,false));

		label = new Label(gTransport,SWT.NONE);
		Messages.setLanguageText(label,"DHTView.transport.received");
		
		lblReceivedPackets = new Label(gTransport,SWT.NONE);
		Utils.setLayoutData(lblReceivedPackets, new GridData(SWT.FILL,SWT.TOP,true,false));

		lblReceivedBytes = new Label(gTransport,SWT.NONE);
		Utils.setLayoutData(lblReceivedBytes, new GridData(SWT.FILL,SWT.TOP,true,false));
		
		label = new Label(gTransport,SWT.NONE);
		Messages.setLanguageText(label,"DHTView.transport.in");
		data = new GridData();
		data.horizontalSpan = 3;
		Utils.setLayoutData(label, data);
		
		in = new Canvas(gTransport,SWT.NO_BACKGROUND);
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 3;
		Utils.setLayoutData(in, data);
		
		inGraph = new SpeedGraphic();
		inGraph.initialize(in);
	}
	
	private void refresh() {
		inGraph.refresh();
	}
}
