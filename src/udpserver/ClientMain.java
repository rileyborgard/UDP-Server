package udpserver;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ClientMain extends BasicGame {
	
	private static GameClient gameClient;
	
	public static ServerData data = new ServerData();
	public static ClientData clientData = new ClientData();
	
	public ClientMain(String title) {
		super(title);
	}
	
	public static void main(String[] args) {
		gameClient = new GameClient();
		gameClient.start();
		AppGameContainer app;
		try {
			app = new AppGameContainer(new ClientMain("Client"));
			app.setDisplayMode(400, 400, false);
			app.setMinimumLogicUpdateInterval(15);
			app.setAlwaysRender(true);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void init(GameContainer gc) throws SlickException {}
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(gameClient.loaded) {
			g.setAntiAlias(true);
			g.setLineWidth(4);
			for(int i = 0; i < data.x.size(); i++) {
				if(i != data.index) {
					g.setColor(Color.blue);
					g.fillOval((int) (double) data.x.get(i), (int) (double) data.y.get(i), 40, 40);
					g.setColor(Color.white);
					g.drawOval((int) (double) data.x.get(i), (int) (double) data.y.get(i), 40, 40);
				}
			}
			g.setColor(Color.red);
			g.fillOval((int) (double) data.x.get(data.index), (int) (double) data.y.get(data.index), 40, 40);
			g.setColor(Color.white);
			g.drawOval((int) (double) data.x.get(data.index), (int) (double) data.y.get(data.index), 40, 40);
		}
	}
	public void update(GameContainer gc, int delta) throws SlickException {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			clientData.exited = true;
		clientData.delta = delta;
		clientData.leftPressed = Keyboard.isKeyDown(Keyboard.KEY_A);
		clientData.rightPressed = Keyboard.isKeyDown(Keyboard.KEY_D);
		clientData.upPressed = Keyboard.isKeyDown(Keyboard.KEY_W);
		clientData.downPressed = Keyboard.isKeyDown(Keyboard.KEY_S);
		try {
			gameClient.sendData(Serializer.serialize(clientData), GameSocket.serverIP, GameSocket.PORT);
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(clientData.exited) {
			gc.exit();
		}
	}
	
	public boolean closeRequested() {
		clientData.exited = true;
		return false;
	}
	
}
