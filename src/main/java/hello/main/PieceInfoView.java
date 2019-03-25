package hello.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import gudy.azureus2.ui.swt.Utils;
import gudy.azureus2.ui.swt.mainwindow.Colors;

public class PieceInfoView {
	
	public static void main(String[] args) {
		try {
			new PieceInfoView().exec(args);
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
		refresh();
		shell.open();
		
		// Set up the event loop.
		while (!display.isDisposed()) {
			if (!display.readAndDispatch()) {
				// If no more entries in the event queue
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private final static int BLOCK_FILLSIZE = 14;
	private final static int BLOCK_SPACING = 3;
	private final static int BLOCK_SIZE = BLOCK_FILLSIZE + BLOCK_SPACING;
	private final static int BLOCKCOLOR_HAVE 		= 0;
	private final static int BLOCKCOLORL_NOHAVE 	= 1;
	private final static int BLOCKCOLOR_TRANSFER 	= 2;
	private final static int BLOCKCOLOR_NEXT 		= 3;
	private final static int BLOCKCOLOR_AVAILCOUNT 	= 4;
	
	private ScrolledComposite sc;
	protected Canvas canvas;
	private Color[] blockColors;
	
	private Font font = null;
	Image img = null;
	
	private PieceInfoView() {}
	
	private void initialize(Composite parent) {
		
		Colors.getInstance();
		blockColors = new Color[] {
			Colors.blue,
			Colors.white,
			Colors.red,
			Colors.fadedRed,
			Colors.black
		};
		
		GridLayout layout;
		GridData gridData;
		
		sc = new ScrolledComposite(parent, SWT.V_SCROLL);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		layout = new GridLayout();
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		sc.setLayout(layout);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1);
		sc.setLayoutData(gridData);
		sc.getVerticalBar().setIncrement(BLOCK_SIZE);
		
		canvas = new Canvas(sc, SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND);
		gridData = new GridData(GridData.FILL, SWT.DEFAULT, true, false);
		canvas.setLayoutData(gridData);
		
		Listener doNothingListener = new Listener() {
			public void handleEvent(Event event) {
			}
		};
		canvas.addListener(SWT.KeyDown, doNothingListener);
		
		sc.setContent(canvas);
		
		int iFontPixelsHeight = 10;
		int iFontPointHeight = (iFontPixelsHeight * 72)
				/ Utils.getDPIRaw(canvas.getDisplay()).y;
		Font f = canvas.getFont();
		FontData[] fontData = f.getFontData();
		fontData[0].setHeight(iFontPointHeight);
		font = new Font(canvas.getDisplay(), fontData);
		
	}
	
	protected void refresh() {
		
		if (canvas == null 
				|| canvas.isDisposed()
				|| !canvas.isVisible()) {
			return;
		}
		
		canvas.layout(true);
		Rectangle bounds = canvas.getClientArea();
		
		int nbPieces = 300;
		int iNumCols = bounds.width / BLOCK_SIZE;
		int iNeededHeight = (((nbPieces - 1) / iNumCols) + 1) * BLOCK_SIZE;
		
		if (img == null) {
			img = new Image(canvas.getDisplay(), bounds.width, iNeededHeight);
		}
		
		GC gcImg = new GC(img);
		
		int iRow = 0;
		int length = 200;
		
		// use advanced capabilities for faster drawText
		gcImg.setAdvanced(true);
		gcImg.setFont(font);
		
		int iCol = 0;
		for (int i = 0; i < length; i++) {
			if (iCol >= iNumCols) {
				iCol = 0;
				iRow++;
			}
			
			int iXPos = iCol * BLOCK_SIZE + 1;
			int iYPos = iRow * BLOCK_SIZE + 1;
			
			
			
			iCol++;
		}
	}
	
	private void drawDownloadIndicator(GC gcImg, int iXPos, int iYPos,
			boolean small) {
		gcImg.setBackground(blockColors[BLOCKCOLOR_TRANSFER]);
		gcImg.fillPolygon(new int[] {
			iXPos,
			iYPos,
			iXPos + BLOCK_FILLSIZE,
			iYPos,
			iXPos + (BLOCK_FILLSIZE / 2),
			iYPos + BLOCK_FILLSIZE
		});
	}
}
