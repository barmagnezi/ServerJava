package sView;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * BasicWindow abstract class extending Observable and implements Runnable,
 * setting the default window parameters.
 * 
 * @author Bar Magnezi and Senia Kalma
 * @version 1.0
 * @since 31.5.2015
 */
public abstract class BasicWindow extends Observable implements Runnable {

	protected Display display;
	protected Shell shell;

	public BasicWindow(String title, int width, int height) {
		this.display = new Display();
		shell = new Shell(display);
		shell.setText(title);
		shell.setSize(width, height);
	}

	public BasicWindow(String title, int width, int height, Display disp) {
		this.setDisplay(disp);
		shell = new Shell(display);
		shell.setText(title);
		shell.setSize(width, height);
	}

	protected abstract void initWidgets();

	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		while (!shell.isDisposed()) { // while window isn't closed

			// 1. read events, put then in a queue.
			// 2. dispatch the assigned listener
			if (!display.readAndDispatch()) { // if the queue is empty
				display.sleep(); // sleep until an event occurs
			}

		} // shell is disposed

		display.dispose(); // dispose OS components
	}

	// http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	// http://stackoverflow.com/questions/10038570/implementing-select-on-focus-behavior-for-an-eclipse-text-control
	public static void addSelectOnFocusToText(Text text) {
		Listener listener = new Listener() {

			private boolean hasFocus = false;
			private boolean hadFocusOnMousedown = false;

			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.FocusIn: {
					Text t = (Text) e.widget;

					// Covers the case where the user focuses by keyboard.
					t.selectAll();

					// The case where the user focuses by mouse click is special
					// because Eclipse,
					// for some reason, fires SWT.FocusIn before SWT.MouseDown,
					// and on mouse down
					// it cancels the selection. So we set a variable to keep
					// track of whether the
					// control is focused (can't rely on isFocusControl()
					// because sometimes it's wrong),
					// and we make it asynchronous so it will get set AFTER
					// SWT.MouseDown is fired.
					t.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							hasFocus = true;
						}
					});

					break;
				}
				case SWT.FocusOut: {
					hasFocus = false;
					((Text) e.widget).clearSelection();

					break;
				}
				case SWT.MouseDown: {
					// Set the variable which is used in SWT.MouseUp.
					hadFocusOnMousedown = hasFocus;

					break;
				}
				case SWT.MouseUp: {
					Text t = (Text) e.widget;
					if (t.getSelectionCount() == 0 && !hadFocusOnMousedown) {
						((Text) e.widget).selectAll();
					}

					break;
				}
				}
			}

		};

		text.addListener(SWT.FocusIn, listener);
		text.addListener(SWT.FocusOut, listener);
		text.addListener(SWT.MouseDown, listener);
		text.addListener(SWT.MouseUp, listener);
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public Display getDisplay() {
		return display;
	}

}
