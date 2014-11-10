package server.main;

import server.world.object.Player;
import net.funkitech.util.Location;
import net.funkitech.util.server.ClientHandler;
import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;


public class PlayerMessageListener implements MessageListener {
	
	private final GameServer server = GameServer.inst;
	
	@MessageHandler(names = "move")
	public void move(ClientHandler client, Location delta) {
		
		Player player = server.getPlayer(client);
		
		player.move(delta);
		
	}
	
}
