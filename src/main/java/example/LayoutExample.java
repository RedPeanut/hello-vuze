package example;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class LayoutExample {
	
	private TabFolder tabFolder;

	/**
	 * Creates an instance of a LayoutExample embedded inside the supplied
	 * parent Composite.
	 * 
	 * @param parent the container of the example
	 */
	public LayoutExample(Composite parent) {
		tabFolder = new TabFolder(parent, SWT.NULL);
		Tab[] tabs = new Tab[] { 
				new FillLayoutTab(this),
				new RowLayoutTab(this), 
				new GridLayoutTab(this),
				new FormLayoutTab(this), 
		};
		for (int i = 0; i < tabs.length; i++) {
			TabItem item = new TabItem(tabFolder, SWT.NULL);
			item.setText(tabs[i].getTabText());
			item.setControl(tabs[i].createTabFolderPage(tabFolder));
		}
	}

	/**
	 * Grabs input focus.
	 */
	public void setFocus() {
		tabFolder.setFocus();
	}

	/**
	 * Disposes of all resources associated with a particular instance of the
	 * LayoutExample.
	 */
	public void dispose() {
		tabFolder = null;
	}

	/**
	 * Invokes as a standalone program.
	 */
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		new LayoutExample(shell);
		shell.setText(getResourceString("window.title"));
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				Shell[] shells = display.getShells();
				for (int i = 0; i < shells.length; i++) {
					if (shells[i] != shell)
						shells[i].close();
				}
			}
		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Gets a string from the resource bundle. We don't want to crash because of
	 * a missing String. Returns the key if not found.
	 */
	static String getResourceString(String key) {
		return key;
	}

	/**
	 * Gets a string from the resource bundle and binds it with the given
	 * arguments. If the key is not found, return the key.
	 */
	static String getResourceString(String key, Object[] args) {
		try {
			return MessageFormat.format(getResourceString(key), args);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}
	}
}

