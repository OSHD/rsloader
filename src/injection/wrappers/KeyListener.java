
package injection.wrappers;

import java.awt.event.KeyEvent;

public abstract class KeyListener extends FocusListener implements java.awt.event.KeyListener{
    public abstract void _keyPressed(KeyEvent e);
	public abstract void _keyReleased(KeyEvent e);
	public abstract void _keyTyped(KeyEvent e);
	@Override
	public void keyPressed(KeyEvent e) {
		_keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		_keyReleased(e);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		_keyTyped(e);
	}
}
