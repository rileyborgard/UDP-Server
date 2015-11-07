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
	public ArrayList<ClientData> clientData = new ArrayList<ClientData>();
	
	public int index;
	
	public static final double SPEED = 40;
	public static final double JUMP_SPEED = 0.03;
	public static final double FALL_ACCELERATION = 0.32;
	
	public boolean processData(ClientData data, InetAddress address, int port) {
		//check if we have received data from this client before
		index = -1;
		for(int i = 0; i < addresses.size(); i++) {
			if(address.equals(addresses.get(i)) && port == ports.get(i)) {
				index = i;
				clientData.set(i, data);
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
			clientData.add(data);
			index = x.size()-1;
		}
		//exit if the user exits
		if(data.exited) {
			addresses.remove(index);
			ports.remove(index);
			x.remove(index);
			y.remove(index);
			vs.remove(index);
			clientData.remove(index);
			return false;
		}
		return true;
	}
	
	public void update(double dt) {
		try {
			for(int i = 0; i < x.size(); i++) {
				//move according to keyboard input
				if(clientData.get(i).leftPressed)
					x.set(i, x.get(i) - 400*dt);
				if(clientData.get(i).rightPressed)
					x.set(i, x.get(i) + 400*dt);
				if(clientData.get(i).upPressed && y.get(i) >= 360.0)
					vs.set(i, -1200*dt);
				//apply gravity
				vs.set(i, vs.get(i) + 100*dt);
				y.set(i, y.get(i) + vs.get(i));
				if(y.get(i) > 360) {
					y.set(i, 360.0);
					vs.set(i, 0.0);
				}
			}
		}catch(Exception e) {
			System.out.println("probrem?");
		}
	}
	
}
