package udpserver;

import java.io.Serializable;

public class ClientData implements Serializable {
	
	private static final long serialVersionUID = 2L;

	public boolean exited = false;
	
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean upPressed;
	public boolean downPressed;
	
}
