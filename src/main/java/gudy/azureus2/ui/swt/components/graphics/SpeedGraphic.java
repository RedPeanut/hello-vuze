package gudy.azureus2.ui.swt.components.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import hello.util.Log;

public class SpeedGraphic extends ScaledGraphic {

	private static String TAG = SpeedGraphic.class.getSimpleName();
	
	protected Image bufferImage;
	
	public void initialize(Canvas canvas) {
		
		super.initialize(canvas);
		
		drawCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Log.d(TAG, "paintControl() is called...");
				if (bufferImage != null && !bufferImage.isDisposed()) {
					Log.d(TAG, "bufferImage != null && !bufferImage.isDisposed()");
					Rectangle bounds = bufferImage.getBounds();
					if (bounds.width >= e.width && bounds.height >= e.height) {
						e.gc.drawImage(bufferImage,
								e.x, e.y, e.width, e.height,
								e.x, e.y, e.width, e.height
						);
					}
				}
			}
		});
		drawCanvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				//drawChart(true);
			}
		});
	}
	
	private void drawChart() {
		
		if (drawCanvas == null || drawCanvas.isDisposed()) // || !drawCanvas.isVisible())
			return;
		
		GC gcImage = null;
		
		drawScale();
		
		Rectangle bounds = drawCanvas.getClientArea();
		if (bounds.isEmpty())
			return;
		
		//If bufferedImage is not null, dispose it
		if (bufferImage != null && !bufferImage.isDisposed())
			bufferImage.dispose();
		
		bufferImage = new Image(drawCanvas.getDisplay(), bounds);
		gcImage = new GC(bufferImage);
		gcImage.drawImage(bufferBackground, 0, 0);
		
		gcImage.setAntialias(SWT.ON);
	}
	
	public void refresh() {
		
		if (drawCanvas == null || drawCanvas.isDisposed())
			return;
		
		Rectangle bounds = drawCanvas.getClientArea();
		
		drawChart();
		
		drawCanvas.redraw();
		drawCanvas.update();
	}
}
