package server.main;

import net.funkitech.util.Location;
import server.event.EventHandler;
import server.event.Listener;
import server.event.player.PlayerButtonEvent.ButtonState;
import server.event.player.PlayerClickMouseEvent.MouseButton;
import server.event.player.PlayerClickMouseEvent;
import server.world.object.Arrow;

public class PlayerClickMouseListener implements Listener {
	
	private final GameServer server;
	
	public PlayerClickMouseListener(GameServer server) {
		this.server = server;
		
		server.getEventManager().registerListener(this);
	}
	
	@EventHandler
	public void onClickMouse(PlayerClickMouseEvent event) {
		if (event.getState() == ButtonState.PRESSED && event.getButton() == MouseButton.BUTTON_1) {
			Location location = event.getPlayer().getLocation();
			Location click = event.getLocation();
			
			double angle = new Location(0, 0).angleBetween(location.subtract(click));
			
			Location dir = new Location(10000, 0).rotate(angle);
			
			Arrow arrow = new Arrow(location, dir);
			
			server.getWorld().addObject(arrow);
			
			
			
		}
	}
	
}
