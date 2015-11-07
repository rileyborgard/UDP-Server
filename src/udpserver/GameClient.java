package udpserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class GameClient extends GameSocket {
	
	boolean loaded = false;
	
	public GameClient() {
		super();
		try {
			this.socket = new DatagramSocket();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readData(DatagramPacket packet) {
		try {
			ClientMain.data = (ServerData) Serializer.deserialize(packet.getData());
			loaded = true;
		}catch(Exception e) {}
	}
	
}
