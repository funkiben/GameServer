package server.event.player;

import server.world.object.Player;

public abstract class PlayerButtonEvent extends PlayerEvent {
	
	
	public enum ButtonState {
		PRESSED,
		DOWN,
		RELEASED
		
	}
	
	private final ButtonState state;
	
	public PlayerButtonEvent(Player player, ButtonState state) {
		super(player);
		
		this.state = state;
		
	}
	
	public ButtonState getState() {
		return state;
	}
	
	
	
	
}
