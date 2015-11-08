package udpserver;

import java.io.Serializable;

public class ClientData implements Serializable {
	
	private static final long serialVersionUID = -8993832649108867633L;

	public boolean exited = false;
	
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean upPressed;
	public boolean downPressed;
	
}
