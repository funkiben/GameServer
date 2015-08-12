package server.event.player;

import net.funkitech.util.Location;
import server.world.object.Player;

public class PlayerTeleportEvent extends PlayerMoveEvent {

	public PlayerTeleportEvent(Player player, Location delta) {
		super(player, delta);
	}

}
