package hello.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import gudy.azureus2.ui.swt.Utils;
import gudy.azureus2.ui.swt.mainwindow.Colors;
import hello.util.Log;

public class PieceInfoView {
	
	private static String TAG = PieceInfoView.class.getSimpleName();
	
	protected Display display;
	
	public static void main(String[] args) {
		try {
			new PieceInfoView().exec(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exec(String[] args) {
		
		display = new Display();
		
		final Shell shell = new Shell(display);
		shell.setText("Hello, world!");
		
		Point minimumSize = shell.getMinimumSize();
		System.out.println("minimumSize = " + minimumSize);
		//shell.setMinimumSize(width, height);
		
		shell.setSize(new Point(BLOCK_SIZE*50, BLOCK_SIZE*10));
		//shell.setMinimumSize(new Point(1000, 500));
		
		//Composite composite = shell;
		//shell.setLayout(new FormLayout());
		initialize(shell);
		
		//shell.pack();
		//refresh();
		
		/*SimpleTimer.addPeriodicEvent("refresh timer...", 500, new TimerEventPerformer() {
			@Override
			public void perform(TimerEvent event) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						refresh();
					}
				});
			}
		});*/
		
		//shell.pack();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						refresh();
						//shell.pack();
						//shell.open();
					}
				});
			}
		}).start();
		
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
	
	private Composite pieceInfoComposite;
	private ScrolledComposite sc;
	protected Canvas pieceInfoCanvas;
	private Color[] blockColors;
	
	private Label topLabel;
	private String topLabelLHS = "";
	private String topLabelRHS = "";

	private Label imageLabel;
	
	private Font font = null;
	Image img = null;
	
	private PieceInfoView() {}
	
	private void initialize(Composite shell) {
		
		Colors.getInstance();
		blockColors = new Color[] {
			Colors.blues[Colors.BLUES_DARKEST],
			Colors.white,
			Colors.red,
			Colors.fadedRed,
			Colors.black
		};
		
		shell.setLayout(new GridLayout());
		shell.setLayoutData(new GridData(GridData.FILL_BOTH));
		//shell.setLayout(new FormLayout());
		
		GridLayout layout;
		GridData gridData;
		
		pieceInfoComposite = new Composite(shell, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		pieceInfoComposite.setLayout(layout);
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		pieceInfoComposite.setLayoutData(gridData);
		
		imageLabel = new Label(pieceInfoComposite, SWT.NULL);
		gridData = new GridData();
		imageLabel.setLayoutData(gridData);

		topLabel = new Label(pieceInfoComposite, SWT.NULL);
		gridData = new GridData(SWT.FILL, SWT.DEFAULT, false, false);
		topLabel.setLayoutData(gridData);
		
		sc = new ScrolledComposite(shell, SWT.V_SCROLL);
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
		
		pieceInfoCanvas = new Canvas(sc, SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND);
		gridData = new GridData(GridData.FILL, SWT.DEFAULT, true, false);
		pieceInfoCanvas.setLayoutData(gridData);
		pieceInfoCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				
				if (e.width <= 0 || e.height <= 0)
					return;
				
				try {
					Rectangle bounds = (img == null) ? null : img.getBounds();
					if (bounds == null) {
						e.gc.fillRectangle(e.x, e.y, e.width, e.height);
					} else {
						if (e.x + e.width > bounds.width)
							e.gc.fillRectangle(bounds.width, e.y, e.x+e.width-bounds.width+1, e.height);
						if (e.y + e.height > bounds.height)
							e.gc.fillRectangle(e.x, bounds.height, e.width, e.y+e.height-bounds.height+1);

						int width = Math.min(e.width, bounds.width-e.x);
						int height = Math.min(e.height, bounds.height-e.y);
						e.gc.drawImage(img, e.x, e.y, width, height, e.x, e.y, width, height);
					}
				} catch (Exception ex) {
				}
			}
		});
		
		
		Listener doNothingListener = new Listener() {
			public void handleEvent(Event event) {
			}
		};
		pieceInfoCanvas.addListener(SWT.KeyDown, doNothingListener);
		
		/*
		pieceInfoCanvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event e) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						if (img != null)
							refresh();
					}
				});
			}
		});
		//*/
		
		sc.setContent(pieceInfoCanvas);
		
		int iFontPixelsHeight = 10;
		int iFontPointHeight = (iFontPixelsHeight * 72)
				/ Utils.getDPIRaw(pieceInfoCanvas.getDisplay()).y;
		Font f = pieceInfoCanvas.getFont();
		FontData[] fontData = f.getFontData();
		fontData[0].setHeight(iFontPointHeight);
		font = new Font(pieceInfoCanvas.getDisplay(), fontData);
		
	}
	
	protected void refresh() {
		
		if (pieceInfoCanvas == null 
				|| pieceInfoCanvas.isDisposed()
				|| !pieceInfoCanvas.isVisible()) {
			Log.d(TAG, "not refreshed...");
			return;
		}
		
		pieceInfoCanvas.layout(true);
		Rectangle bounds = pieceInfoCanvas.getClientArea();
		
		int nbPieces, length;
		nbPieces = length = 1440;
		int iNumCols = bounds.width / BLOCK_SIZE;
		int iNeededHeight = (((nbPieces - 1) / iNumCols) + 1) * BLOCK_SIZE;
		
		if (img == null) {
			img = new Image(pieceInfoCanvas.getDisplay(), bounds.width, iNeededHeight);
		}
		
		GC gcImg = new GC(img);
		BlockInfo[] newBlockInfo = new BlockInfo[length];
		
		int iRow = 0;
		
		// use advanced capabilities for faster drawText
		gcImg.setAdvanced(true);
		gcImg.setFont(font);
		
		int iCol = 0;
		for (int i = 0; i < length; i++) {
			if (iCol >= iNumCols) {
				iCol = 0;
				iRow++;
			}
			
			newBlockInfo[i] = new BlockInfo();
			
			int colorIndex;
			int iXPos = iCol * BLOCK_SIZE + 1;
			int iYPos = iRow * BLOCK_SIZE + 1;
			
			int width = BLOCK_FILLSIZE;
			int iNewWidth = width;
			newBlockInfo[i].haveWidth = iNewWidth;
			
			gcImg.setBackground(pieceInfoCanvas.getBackground());
			gcImg.fillRectangle(iCol * BLOCK_SIZE, iRow * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
			
			colorIndex = BLOCKCOLOR_HAVE;
			gcImg.setBackground(blockColors[colorIndex]);
			gcImg.fillRectangle(iXPos, iYPos, newBlockInfo[i].haveWidth, BLOCK_FILLSIZE);

			colorIndex = BLOCKCOLORL_NOHAVE;
			gcImg.setBackground(blockColors[colorIndex]);
			gcImg.fillRectangle(iXPos + newBlockInfo[i].haveWidth, iYPos, BLOCK_FILLSIZE - newBlockInfo[i].haveWidth, BLOCK_FILLSIZE);
			
			if (i%3 == 0 && i%5 == 0) {
				drawDownloadIndicator(gcImg, iXPos, iYPos, true);
				drawUploadIndicator(gcImg, iXPos, iYPos, true);
			} else if (i%3 == 0) {
				drawDownloadIndicator(gcImg, iXPos, iYPos, false);
			} else if (i%5 == 0) {
				drawUploadIndicator(gcImg, iXPos, iYPos, false);
			}
			
			gcImg.setLineStyle(SWT.LINE_DOT);
			gcImg.setForeground(blockColors[BLOCKCOLOR_AVAILCOUNT]);
			gcImg.drawRectangle(iXPos - 1, iYPos - 1, BLOCK_FILLSIZE + 1, BLOCK_FILLSIZE + 1);
			gcImg.setLineStyle(SWT.LINE_SOLID);
			
			iCol++;
		}
		
		pieceInfoCanvas.redraw();
		
	}
	
	private void drawDownloadIndicator(GC gcImg, int iXPos, int iYPos, boolean small) {
		if (!small) {
			gcImg.setBackground(blockColors[BLOCKCOLOR_TRANSFER]);
			gcImg.fillPolygon(new int[] {
					iXPos,
					iYPos,
					iXPos + BLOCK_FILLSIZE,
					iYPos,
					iXPos + (BLOCK_FILLSIZE / 2),
					iYPos + BLOCK_FILLSIZE
			});
		} else {
			gcImg.setBackground(blockColors[BLOCKCOLOR_NEXT]);
			gcImg.fillPolygon(new int[] {
				iXPos + 2,
				iYPos + 2,
				iXPos + BLOCK_FILLSIZE - 1,
				iYPos + 2,
				iXPos + (BLOCK_FILLSIZE / 2),
				iYPos + BLOCK_FILLSIZE - 1
			});
		}
	}
	
	private void drawUploadIndicator(GC gcImg, int iXPos, int iYPos, boolean small) {
		if (!small) {
			gcImg.setBackground(blockColors[BLOCKCOLOR_TRANSFER]);
			gcImg.fillPolygon(new int[] {
				iXPos,
				iYPos + BLOCK_FILLSIZE,
				iXPos + BLOCK_FILLSIZE,
				iYPos + BLOCK_FILLSIZE,
				iXPos + (BLOCK_FILLSIZE / 2),
				iYPos
			});
		} else {
			// Small Up Arrow each upload request
			gcImg.setBackground(blockColors[BLOCKCOLOR_NEXT]);
			gcImg.fillPolygon(new int[] {
				iXPos + 1,
				iYPos + BLOCK_FILLSIZE - 2,
				iXPos + BLOCK_FILLSIZE - 2,
				iYPos + BLOCK_FILLSIZE - 2,
				iXPos + (BLOCK_FILLSIZE / 2),
				iYPos + 2
			});
		}
	}
	
	private static class BlockInfo {
		
		public int haveWidth;
		int availNum;
		boolean availDotted;
		boolean uploadingIndicator;
		boolean uploadingIndicatorSmall;
		boolean downloadingIndicator;

		public BlockInfo() {
			haveWidth = -1;
		}

		public boolean sameAs(BlockInfo otherBlockInfo) {
			return haveWidth == otherBlockInfo.haveWidth
					&& availNum == otherBlockInfo.availNum
					&& availDotted == otherBlockInfo.availDotted
					&& uploadingIndicator == otherBlockInfo.uploadingIndicator
					&& uploadingIndicatorSmall == otherBlockInfo.uploadingIndicatorSmall
					&& downloadingIndicator == otherBlockInfo.downloadingIndicator;
		}
	}
	
}
