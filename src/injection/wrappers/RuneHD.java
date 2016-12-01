package injection.wrappers;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import api.methods.Client;
import api.wrappers.NPCNode;
import api.wrappers.Player;

import java.awt.Canvas;

public class RuneHD implements Runnable {

	public static BufferedImage bufImage;
	private static byte[] imageBytes;
	private static byte[] gameInfo;
	public static DatagramSocket socket;
	public static boolean sendScreen = false;
	
	@Override
	public void run() {
		System.out.println("Starting OSHD Server");
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		while(true)
		{
			if(!sendScreen)
			{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(sendScreen)
			{
				sendScreen = false;
				try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bufImage, "jpg", baos);
				baos.flush();
				imageBytes = PacketType((byte)0,baos.toByteArray());
				baos.close();
						GetGameInfo();
						gameInfo = PacketType((byte)1,gameInfo);
						socket.send(toDatagram(imageBytes,
								InetAddress.getByName("127.0.0.1"), 43592));
						
						socket.send(toDatagram(gameInfo,
								InetAddress.getByName("127.0.0.1"), 43592));
				} catch (Exception e) {
				e.printStackTrace();
			}
			}
		}
	}
	
	public static void GetGameInfo()
	{
		String gameData = "";
		
		gameData += "Players:";
		Player[] allPlayers = Client.getPlayerArray();
		for(Player player : allPlayers)
		{
			if(player != null)
			{
				gameData += player.getPlayerName() + ",";
				gameData += player.getLocationX() + ",";
				gameData += player.getLocationY() + ",";
				gameData += player.getHeight() + ",";
				gameData += player.getLevel();
				gameData += "#";
			}
		}
		gameData += "}";
		
		gameData += "Npcs:";
		NPCNode[] allNpcs = Client.getNPCNodeArray();
		for(NPCNode npc : allNpcs)
		{
			if(npc != null && npc.getNPC() != null)
			{
				gameData += npc.getNPC().getNPCName() + ",";
				gameData += npc.getNPC().getLocationX() + ",";
				gameData += npc.getNPC().getLocationY() + ",";
				gameData += npc.getNPC().getHeight() + ",";
				gameData += npc.getNPC().getLevel() + ",";
				gameData += npc.getUnique();
				gameData += "#";
			}
		}
		gameData += "}";
		
		gameData += "GameInfo:";
		gameData += Client.getBaseX() + ",";
		gameData += Client.getBaseY() + ",";
		gameData += Client.getCameraX() + ",";
		gameData += Client.getCameraY() + ",";
		gameData += Client.getCameraZ() + ",";
		gameData += Client.getCameraPitch() + ",";
		gameData += Client.getCameraYaw() + ",";
		gameData += Client.getCurrentRegionX() + ",";
		gameData += Client.getCurrentRegionY();
		gameData += "}";
		
		try {
			gameInfo = gameData.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static byte[] PacketType(byte packetId, byte[] input)
	{
		byte[] returnBytes = new byte[input.length + 1];
		returnBytes[0] = packetId;
		for(int i = 0; i < input.length; ++i) returnBytes[i+1] = input[i];
		return returnBytes;
	}
	
	public static DatagramPacket toDatagram(byte[] buf, InetAddress destIA,
			int destPort) {
		// Deprecated in Java 1.1, but it works:
		if(buf.length >= 64000) return new DatagramPacket(new byte[]{buf[0]}, 1, destIA, destPort);
		return new DatagramPacket(buf, buf.length, destIA, destPort);
	}

}