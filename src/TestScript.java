import script.ScriptDef;

import java.awt.*;

public class TestScript extends ScriptDef {

    @Override
    public void run() {
        //System.out.println("Starting test script... : " + Data.CLIENT_APPLET.getComponentAt(0, 0).getMousePosition());

    }

    @Override
    public void repaint(Graphics g) {
        g.drawString("asdasdasdasdasdada"  , 10, 50);
       // System.out.println(Data.CLIENT_APPLET.getComponentAt(0, 0).getMousePosition());



    }
}
