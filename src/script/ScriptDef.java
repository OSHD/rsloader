
package script;


import environment.Data;

import java.awt.*;
import java.util.Random;

public abstract class ScriptDef extends Thread {
    public boolean isPaused = false;

    public static int random(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }

    public abstract void run();

    public abstract void repaint(Graphics g);

    @SuppressWarnings("deprecation")
    public void stopScript() {
        isPaused = false;
        if (this.equals(Data.currentScript)) {
            Data.appletFrame.startScriptOption.setLabel("Start Script");
            Data.appletFrame.pauseScriptOption.setLabel("Pause Script");
            Data.appletFrame.pauseScriptOption.setEnabled(false);
            Data.currentScript = null;
            Runtime.getRuntime().gc();
        }
        this.stop();
    }

    @SuppressWarnings("deprecation")
    public void pause() {
        isPaused = true;
        this.suspend();
    }

    @SuppressWarnings("deprecation")
    public void unpause() {
        isPaused = false;
        this.resume();
    }
}
