package injection;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Dumper {

	public static void dump(int region, int[] keys) {
		try {
			System.out.print("Dumping region: " + region + " (" + (region >> 8) + "," + (region & 0xFF) + "), [");
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./data/xteas/" + region + ".txt")));
			for (int i = 0; i < keys.length; i++) {
				writer.write(String.valueOf(keys[i]));
				System.out.print(keys[i]+" ");
				writer.newLine();
			}
			writer.close();
			System.out.println("]");
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
	
	public static void dumpPacket(int multiplier, int region) {
//		System.out.println("PacketReceived: " + (region*multiplier));
	}
	
	public static void dumpPacketData(byte[] data) {
//		System.out.println("Data="+new String(data));
	}

}
