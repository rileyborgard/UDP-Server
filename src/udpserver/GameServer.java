package udpserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class GameServer extends GameSocket {
	
	private static ServerData data = new ServerData();
	
	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.start();
		AppGameContainer app;
		try {
			app = new AppGameContainer(new ServerGame("Server"));
			app.setDisplayMode(400, 400, false);
			//app.setMinimumLogicUpdateInterval(15);
			app.setAlwaysRender(true);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public GameServer() {
		super();
		try {
			this.socket = new DatagramSocket(PORT);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readData(DatagramPacket packet) {
		try {
			if(data.processData((ClientData) Serializer.deserialize(packet.getData()), packet.getAddress(), packet.getPort()))
				sendData(Serializer.serialize(data), packet.getAddress(), packet.getPort());
		}catch(Exception e) {}
	}
	
	private static class ServerGame extends BasicGame {
		
		public ServerGame(String title) {
			super(title);
		}
		
		public void init(GameContainer gc) throws SlickException {}
		public void render(GameContainer gc, Graphics g) throws SlickException {
			g.setColor(Color.blue);
			for(int i = 0; i < data.x.size(); i++) {
				g.drawRect((int) (double) data.x.get(i), (int) (double) data.y.get(i), 40, 40);
			}
		}
		public void update(GameContainer gc, int delta) throws SlickException {
			data.update((double) delta / 1000.0);
		}
		
	}
	
}
