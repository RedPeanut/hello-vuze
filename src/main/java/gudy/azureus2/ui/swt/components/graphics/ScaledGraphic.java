package gudy.azureus2.ui.swt.components.graphics;

import org.eclipse.swt.widgets.Canvas;

public class ScaledGraphic extends BackGroundGraphic {
	
	//protected Canvas drawCanvas;
	
	protected void drawScale() {
		if (drawCanvas == null || drawCanvas.isDisposed()) // || !drawCanvas.isVisible())
			return;
		
		drawBackGround();
		
	}
}
