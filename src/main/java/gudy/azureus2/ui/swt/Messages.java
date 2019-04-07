package gudy.azureus2.ui.swt;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

import gudy.azureus2.core3.internat.MessageText;

public class Messages {
	
	public static void setLanguageText(Widget widget, String key) {
		String message = MessageText.getString(key);
		if (widget instanceof Label)
			((Label) widget).setText(message.replaceAll("& ", "&& "));
		else if (widget instanceof Group)
			 ((Group) widget).setText(message);
	}
	
}
