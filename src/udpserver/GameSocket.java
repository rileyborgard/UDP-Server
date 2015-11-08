package udpserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class GameSocket extends Thread {
	
	protected DatagramSocket socket;
	
	public static InetAddress serverIP;
	
	public static final int PORT = 6789;
	
	public GameSocket() {
		try {
			serverIP = InetAddress.getByName("192.168.1.115");
		} catch (Exception e) {}
	}
	
	public void sendData(byte[] data, InetAddress ipAddress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
				readData(packet);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public abstract void readData(DatagramPacket packet);
	
}
