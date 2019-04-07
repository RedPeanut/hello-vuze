package gudy.azureus2.ui.swt.components.graphics;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import aelitis.azureus.ui.swt.utils.ColorCache;
import gudy.azureus2.ui.swt.mainwindow.Colors;
import hello.util.Log;

public class BackGroundGraphic {
	
	private static String TAG = BackGroundGraphic.class.getSimpleName();
	
	protected Canvas drawCanvas;
	
	protected Image bufferBackground;
	
	protected Color lightGrey;
	protected Color lightGrey2;
	protected Color colorWhite;
	
	public void initialize(Canvas canvas) {
		this.drawCanvas = canvas;
		
		lightGrey = ColorCache.getColor(canvas.getDisplay(), 250, 250, 250);
		lightGrey2 = ColorCache.getColor(canvas.getDisplay(), 233, 233, 233);
		colorWhite = ColorCache.getColor(canvas.getDisplay(), 255, 255, 255);
	}
	
	protected void drawBackGround() {
		
		if (drawCanvas == null || drawCanvas.isDisposed())
			return;
		
		if (bufferBackground == null) {
			Rectangle bounds = drawCanvas.getClientArea();
			Log.d(TAG, "bounds = " + bounds);
			
			if (bounds.height < 30 || bounds.width < 100)
				return;
			
			if (bufferBackground != null && ! bufferBackground.isDisposed())
				bufferBackground.dispose();
			
			if (bounds.width > 10000 || bounds.height > 10000) 
				return;
			
			bufferBackground = new Image(drawCanvas.getDisplay(),bounds);
			
			Color colors[] = new Color[4];
			colors[0] = colorWhite;
			colors[1] = lightGrey;
			colors[2] = lightGrey2;
			colors[3] = lightGrey;
			GC gcBuffer = new GC(bufferBackground);
			for (int i = 0 ; i < bounds.height - 2 ; i++) {
				gcBuffer.setForeground(colors[i%4]);
				gcBuffer.drawLine(1,i+1,bounds.width-1,i+1);
			}
			gcBuffer.setForeground(Colors.black);
			gcBuffer.drawLine(bounds.width-70,0,bounds.width-70,bounds.height-1);

			gcBuffer.drawRectangle(0,0,bounds.width-1,bounds.height-1);
			gcBuffer.dispose();
		}
	}
	
	public void dispose() {
		if (bufferBackground != null && ! bufferBackground.isDisposed())
			bufferBackground.dispose();
	}
}
