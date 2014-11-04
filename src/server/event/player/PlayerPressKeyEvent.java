package server.event.player;

import server.world.object.Player;

public class PlayerPressKeyEvent extends PlayerButtonEvent {
	
	private final char key;
	
	public PlayerPressKeyEvent(Player player, ButtonState state, char key) {
		super(player, state);
		
		this.key = key;
		
	}
	
	public char getKey() {
		return key;
	}
	
}
