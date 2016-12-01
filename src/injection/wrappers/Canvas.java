
package injection.wrappers;

import environment.Data;
import gui.AppletFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

import api.methods.Client;
import api.methods.Players;

public class Canvas extends java.awt.Canvas {
    private static BufferedImage gameImage = new BufferedImage(765, 503, BufferedImage.TYPE_INT_RGB);
    
    @Override
    public void paint(Graphics g)
    {
    	
  
    }
    
    @Override
    public void update(Graphics g)
    {
    	
  
    }
    

    public void repaint(Graphics g)
    {
    	
  
    }
    
    @Override
    public Graphics getGraphics() {
        Graphics g = gameImage.getGraphics();
        g.setColor(Color.CYAN);
        if(Data.appletFrame != null && Client.getMyPlayer() != null)
        {
        	g.drawString("Screen Size - " + Data.appletFrame.getSize().getWidth() + "," + Data.appletFrame.getSize().getHeight(), 5, 15);
        	g.drawString("Base X - " + Client.getBaseX() + ", Base Y - " + Client.getBaseY(), 5, 30);
        	g.drawString("Player X - " + Client.getMyPlayer().getLocationX() + ", Player Y - " + Client.getMyPlayer().getLocationY(), 5, 45);
        	g.drawString("Region X - " + Client.getCurrentRegionX() + ", Region Y - " + Client.getCurrentRegionY(), 5, 60);
        	if((gameImage.getWidth() != Data.appletFrame.getSize().getWidth()) || (gameImage.getHeight() != Data.appletFrame.getSize().getHeight())) gameImage = new BufferedImage((int)Data.appletFrame.getSize().getWidth(), (int)Data.appletFrame.getSize().getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        
        if(Data.hd != AppletFrame.hdCheck.getState())
        {
        	Data.hd = AppletFrame.hdCheck.getState();
        	if(Data.hd)
        	{
        		Data.appletFrame.setSize(765, 503);
        		Data.CLIENT_APPLET.setBounds(5, 5, 765, 503);
        	}
        	if(!Data.debugFrame) Data.appletFrame.setVisible(Data.hd ? false : true);
        }
        
        if(RuneHD.bufImage == null) RuneHD.bufImage = gameImage;
        if(Data.hd) RuneHD.sendScreen = true;
        if(!Data.hd || Data.debugFrame) super.getGraphics().drawImage(gameImage, 0, 0, null);
        return g;
    }
}
