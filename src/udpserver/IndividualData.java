package udpserver;

import java.io.Serializable;
import java.net.InetAddress;

public class IndividualData implements Serializable {
	
	private static final long serialVersionUID = 8833302277659030921L;
	
	public double x, y, vs;
	public InetAddress address;
	public int port;
	public ClientData clientData;
	
	public void update(double dt) {
		//move according to keyboard input
		if(clientData.leftPressed)
			x -= 400*dt;
		if(clientData.rightPressed)
			x += 400*dt;
		if(clientData.upPressed && y >= 360)
			vs = -1200*dt;
		//apply gravity
		vs += 100*dt;
		y += vs;
		if(y > 360) {
			y = 360;
			vs = 0;
		}
	}
	
}
