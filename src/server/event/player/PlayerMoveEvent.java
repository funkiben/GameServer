package server.event.player;

import net.funkitech.util.Location;
import server.event.world.object.WorldObjectMoveEvent;
import server.world.object.Player;

public class PlayerMoveEvent extends WorldObjectMoveEvent {
	
	public PlayerMoveEvent(Player player, Location delta) {
		super(player, delta);
	}
	
	public Player getPlayer() {
		return (Player) getWorldObject();
	}

}
