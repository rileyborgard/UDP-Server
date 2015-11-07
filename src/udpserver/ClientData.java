package udpserver;

import java.io.Serializable;

import org.newdawn.slick.Input;

public class ClientData implements Serializable {
	
	public boolean exited = false;
	public double delta = 0;
	
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean upPressed;
	public boolean downPressed;
	
}
