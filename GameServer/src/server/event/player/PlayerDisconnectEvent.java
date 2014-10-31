package server.event.player;

import server.world.object.Player;


public class PlayerDisconnectEvent extends PlayerEvent {

	public PlayerDisconnectEvent(Player player) {
		super(player);
	}

}
