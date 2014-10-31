package server.event.player;

import server.event.Event;
import server.world.object.Player;


public class PlayerEvent extends Event {
	
	protected final Player player;
	
	public PlayerEvent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}

}
