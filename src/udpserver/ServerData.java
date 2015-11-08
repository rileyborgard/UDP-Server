package udpserver;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class ServerData implements Serializable {
	
	private static final long serialVersionUID = 584460438362147743L;

	public ArrayList<IndividualData> indieData = new ArrayList<IndividualData>();
	
	public int index;
	
	public static final double SPEED = 40;
	public static final double JUMP_SPEED = 0.03;
	public static final double FALL_ACCELERATION = 0.32;
	
	public boolean processData(ClientData data, InetAddress address, int port) {
		//check if we have received data from this client before
		index = -1;
		for(int i = 0; i < indieData.size(); i++) {
			if(address.equals(indieData.get(i).address) && port == indieData.get(i).port) {
				index = i;
				indieData.get(i).clientData = data;
				break;
			}
		}
		//create space for new data if this is a new client
		if(index == -1) {
			index = indieData.size();
			IndividualData iData = new IndividualData();
			iData.address = address;
			iData.port = port;
			iData.x = 180;
			iData.y = 0;
			iData.vs = 0;
			iData.clientData = data;
			indieData.add(iData);
		}
		//exit if the user exits
		if(data.exited) {
			indieData.remove(index);
			return false;
		}
		return true;
	}
	
	public void update(double dt) {
		for(int i = 0; i < indieData.size(); i++) {
			indieData.get(i).update(dt);
		}
	}
	
}
