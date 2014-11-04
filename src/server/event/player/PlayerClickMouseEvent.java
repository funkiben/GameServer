package server.event.player;

import net.funkitech.util.Location;
import server.world.object.Player;

public class PlayerClickMouseEvent extends PlayerButtonEvent {
	
	private final MouseButton button;
	private final Location location;
	
	public PlayerClickMouseEvent(Player player, ButtonState state, MouseButton button, Location location) {
		super(player, state);
		
		this.button = button;
		this.location = location;
		
	}
	
	public MouseButton getButton() {
		return (MouseButton) button;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public boolean hasLocation() {
		return location != null;
	}
	
	public enum MouseButton {
		NO_BUTTON,
		BUTTON_1,
		BUTTON_2,
		BUTTON_3;
		
		public static MouseButton parse(int button) {
			return values()[button];
		}
		
	}
	
	
	
}
