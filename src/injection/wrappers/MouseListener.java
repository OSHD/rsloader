
package injection.wrappers;


import environment.Data;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;

public abstract class MouseListener extends FocusListener implements java.awt.event.MouseListener, MouseMotionListener{

    private int clientX;
	private int clientY;
	private int x;
	private int y;
	private int clientPressX = -1;
	private int clientPressY = -1;
	private long clientPressTime = -1;
	private boolean clientPresent;
	private boolean clientPressed;

	public abstract void _mouseClicked(MouseEvent e);

	public abstract void _mouseDragged(MouseEvent e);

	public abstract void _mouseEntered(MouseEvent e);

	public abstract void _mouseExited(MouseEvent e);

	public abstract void _mouseMoved(MouseEvent e);

	public abstract void _mousePressed(MouseEvent e);

	public abstract void _mouseReleased(MouseEvent e);

	public abstract void _mouseWheelMoved(MouseWheelEvent e);

	public abstract Component getComponent();

	public int getX() {
		if (clientX == -1) {
			return 0;
		}
		return clientX;
	}
	public int getRealX(){
		return x;
	}
	public int getY() {
		if (clientY == -1) {
			return 0;
		}
		return clientY;
	}
	public int getRealY(){
		return y;
	}
	public int getPressX() {
		return clientPressX;
	}

	public int getPressY() {
		return clientPressY;
	}

	public long getPressTime() {
		return clientPressTime;
	}

	public boolean isPressed() {
		return clientPressed;
	}

	public boolean isPresent() {
		return clientPresent;
	}

	public final void mouseClicked(final MouseEvent e) {
		x=e.getX();
		y=e.getY();
		if(Data.currentScript==null || Data.currentScript.isPaused){
			clientX = e.getX();
			clientY = e.getY();
			_mouseClicked(e);
		}
		e.consume();
	}

	public final void mouseDragged(final MouseEvent e) {
		x=e.getX();
		y=e.getY();
		if(Data.currentScript==null || Data.currentScript.isPaused){
			clientX = e.getX();
			clientY = e.getY();
			_mouseDragged(e);
		}
		e.consume();
	}

	public final void mouseEntered(final MouseEvent e) {
		x=e.getX();
		y=e.getY();
		if(Data.currentScript==null || Data.currentScript.isPaused){
			clientPresent = true;
			clientX = e.getX();
			clientY = e.getY();
			_mouseEntered(e);
		}
		e.consume();
	}

	public final void mouseExited(final MouseEvent e) {
		x=e.getX();
		y=e.getY();
		if(Data.currentScript==null || Data.currentScript.isPaused){
			clientPresent = false;
			clientX = e.getX();
			clientY = e.getY();
			_mouseExited(e);
		}
		e.consume();
	}

	public final void mouseMoved(final MouseEvent e) {
		x=e.getX();
		y=e.getY();
		if(Data.currentScript==null || Data.currentScript.isPaused){
			clientX = e.getX();
			clientY = e.getY();
			_mouseMoved(e);
		}
		e.consume();
	}

	public final void mousePressed(final MouseEvent e) {
		x=e.getX();
		y=e.getY();
		if(Data.currentScript==null || Data.currentScript.isPaused){
			clientPressed = true;
			clientX = e.getX();
			clientY = e.getY();
			clientPressX = e.getX();
			clientPressY = e.getY();
			clientPressTime = System.currentTimeMillis();
			_mousePressed(e);
		}
		e.consume();
	}

	public final void mouseReleased(final MouseEvent e) {
		x=e.getX();
		y=e.getY();
		if(Data.currentScript==null || Data.currentScript.isPaused){
			clientX = e.getX();
			clientY = e.getY();
			clientPressed = false;
			_mouseReleased(e);
		}
		e.consume();
	}

	public void mouseWheelMoved(final MouseWheelEvent e) {
		if(Data.currentScript==null || Data.currentScript.isPaused){
			try {
				_mouseWheelMoved(e);
			} catch (final AbstractMethodError ame) {
				// it might not be implemented!
			}
		}
		e.consume();
	}

	public final void sendEvent(final MouseEvent e) {
		clientX = e.getX();
		clientY = e.getY();
		try {
			if (e.getID() == MouseEvent.MOUSE_CLICKED) {
				_mouseClicked(e);
			} else if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
				_mouseDragged(e);
			} else if (e.getID() == MouseEvent.MOUSE_ENTERED) {
				clientPresent = true;
				_mouseEntered(e);
			} else if (e.getID() == MouseEvent.MOUSE_EXITED) {
				clientPresent = false;
				_mouseExited(e);
			} else if (e.getID() == MouseEvent.MOUSE_MOVED) {
				_mouseMoved(e);
			} else if (e.getID() == MouseEvent.MOUSE_PRESSED) {
				clientPressX = e.getX();
				clientPressY = e.getY();
				clientPressTime = System.currentTimeMillis();
				clientPressed = true;
				_mousePressed(e);
			} else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
				clientPressed = false;
				_mouseReleased(e);
			} else if (e.getID() == MouseEvent.MOUSE_WHEEL) {
				try {
					_mouseWheelMoved((MouseWheelEvent) e);
				} catch (final AbstractMethodError ignored) {
					// it might not be implemented!
				}
			} else {
				throw new InternalError(e.toString());
			}
		} catch (final NullPointerException ignored) {
			// client may throw NPE when a listener
			// is being re-instantiated.
		}
	}
}
