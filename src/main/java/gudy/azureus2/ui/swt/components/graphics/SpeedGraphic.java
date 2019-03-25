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

public class SpeedGraphic {

	private static String TAG = SpeedGraphic.class.getSimpleName();
	
	protected Canvas drawCanvas;
	protected Image bufferImage;
	
	public void initialize(Canvas in) {
		this.drawCanvas = in;
		drawCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				Log.d(TAG, "paintControl() is called...");
				if (bufferImage != null && !bufferImage.isDisposed()) {
					Log.d(TAG, "bufferImage != null && !bufferImage.isDisposed()");
					Rectangle bounds = bufferImage.getBounds();
				}
			}
		});
		drawCanvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				//drawChart(true);
			}
		});
	}
	
	private void drawChart(boolean sizeChanged) {
		if (drawCanvas == null || drawCanvas.isDisposed() || !drawCanvas.isVisible()) {
			return;
		}
		
		GC gcImage = null;
		Rectangle bounds = drawCanvas.getClientArea();
		if (bounds.isEmpty())
			return;
		
		bufferImage = new Image(drawCanvas.getDisplay(), bounds);
		gcImage = new GC(bufferImage);
		
	}
}
