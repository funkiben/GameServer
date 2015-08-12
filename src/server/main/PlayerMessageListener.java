package server.main;

import server.event.player.PlayerMoveEvent;
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
		
		PlayerMoveEvent event = new PlayerMoveEvent(player, delta);
		GameServer.inst.getEventManager().callEvent(event);
		
		Location prevloc = event.getPrevLocation();
		Location newloc = event.getNewLocation();
		
		if (!event.isCancelled() && !event.getDelta().isZero()) {
			player.onMove(newloc);
			
			player.updateWithPlayers(false);
			
			player.sendNewChunks(prevloc, newloc);
			
			return;
			
		}
		
		return;
		
	}
	
}
