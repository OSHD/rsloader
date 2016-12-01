
package injection.wrappers;

import java.awt.event.FocusEvent;

public abstract class FocusListener implements java.awt.event.FocusListener{
	public abstract void _focusGained(FocusEvent fe);
	public abstract void _focusLost(FocusEvent fe);
	@Override
	public void focusGained(FocusEvent arg0) {
		_focusGained(arg0);
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		_focusLost(arg0);
	}
}
