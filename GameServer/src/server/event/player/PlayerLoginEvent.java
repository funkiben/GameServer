package server.event.player;

import server.world.object.Player;


public class PlayerLoginEvent extends PlayerEvent {
	
	public PlayerLoginEvent(Player player) {
		super(player);
	}

}
