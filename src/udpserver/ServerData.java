package udpserver;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class ServerData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public ArrayList<Double> x = new ArrayList<Double>();
	public ArrayList<Double> y = new ArrayList<Double>();
	public ArrayList<Double> vs = new ArrayList<Double>();
	public ArrayList<InetAddress> addresses = new ArrayList<InetAddress>();
	public ArrayList<Integer> ports = new ArrayList<Integer>();
	
	public int index;
	
	public static final double SPEED = 4;
	
	public boolean processData(ClientData clientData, InetAddress address, int port) {
		//check if we have received data from this client before
		index = -1;
		for(int i = 0; i < addresses.size(); i++) {
			if(address.equals(addresses.get(i)) && port == ports.get(i)) {
				index = i;
				break;
			}
		}
		//create space for new data if this is a new client
		if(index == -1) {
			addresses.add(address);
			ports.add(port);
			x.add(180.0);
			y.add(0.0);
			vs.add(0.0);
			index = x.size()-1;
		}
		//exit if the user exits
		if(clientData.exited) {
			addresses.remove(index);
			ports.remove(index);
			x.remove(index);
			y.remove(index);
			vs.remove(index);
			return false;
		}else {
			//keyboard movements: left, right, up, down
			if(clientData.leftPressed)
				x.set(index, x.get(index) - SPEED);
			if(clientData.rightPressed)
				x.set(index, x.get(index) + SPEED);
			if(clientData.upPressed && y.get(index) >= 360.0)
				vs.set(index, -5.0);
			
			//apply gravity
			vs.set(index, vs.get(index) + 0.1);
			y.set(index, y.get(index) + vs.get(index));
			if(y.get(index) > 360) {
				y.set(index, 360.0);
				vs.set(index, 0.0);
			}
		}
		return true;
	}
	
}
