package server.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import server.event.EventHandler;
import server.event.Listener;
import server.event.player.PlayerButtonEvent.ButtonState;
import server.event.player.PlayerClickMouseEvent;
import server.event.player.PlayerClickMouseEvent.MouseButton;
import server.event.player.PlayerDisconnectEvent;
import server.event.player.PlayerLoginEvent;
import server.event.player.PlayerPressKeyEvent;
import server.event.server.ServerTickEvent;
import server.world.object.Player;
import net.funkitech.util.Location;
import net.funkitech.util.server.ClientHandler;
import net.funkitech.util.server.messaging.MessageHandler;
import net.funkitech.util.server.messaging.MessageListener;

public class PlayerButtonMessageListener implements MessageListener, Listener {
	
	private final Map<Player, Set<Character>> keysDown = new HashMap<Player, Set<Character>>();
	private final Map<Player, Set<MouseButton>> mouseButtonsDown = new HashMap<Player, Set<MouseButton>>();
	
	private final GameServer server;
	
	public PlayerButtonMessageListener(GameServer server) {
		this.server = server;
		
		server.getEventManager().registerListener(this);
	}
	
	@EventHandler
	public void onServerTick(ServerTickEvent event) {
		for (Map.Entry<Player, Set<Character>> e1 : keysDown.entrySet()) {
			Player player = e1.getKey();
			
			for (char key : e1.getValue()) {
				
				server.getEventManager().callEvent(new PlayerPressKeyEvent(player, ButtonState.DOWN, key));
				
			}
		}
		
		for (Map.Entry<Player, Set<MouseButton>> e1 : mouseButtonsDown.entrySet()) {
			Player player = e1.getKey();
			
			for (MouseButton button : e1.getValue()) {
				
				server.getEventManager().callEvent(new PlayerClickMouseEvent(player, ButtonState.DOWN, button, null));
				
				
			}
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		keysDown.remove(event.getPlayer());
		mouseButtonsDown.remove(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event) {
		keysDown.remove(event.getPlayer());
		mouseButtonsDown.remove(event.getPlayer());
	}
	
	
	
	
	
	
	@MessageHandler(names = "keyPressed")
	public void pressKey(ClientHandler handler, Character key) {
		Player player = server.getPlayer(handler);
		
		Set<Character> set = keysDown.get(player);
		
		if (set == null) {
			set = new HashSet<Character>();
			keysDown.put(player, set);
		}
		
		set.add(key);
		
		
		PlayerPressKeyEvent event = new PlayerPressKeyEvent(player, ButtonState.PRESSED, key);
		
		server.getEventManager().callEvent(event);
		
	}
	
	@MessageHandler(names = "keyReleased")
	public void releaseKey(ClientHandler handler, Character key) {
		Player player = server.getPlayer(handler);
		
		keysDown.get(player).remove(key);
		
		PlayerPressKeyEvent event = new PlayerPressKeyEvent(player, ButtonState.RELEASED, key);
		
		server.getEventManager().callEvent(event);
		
	}
	
	
	
	
	
	
	
	
	
	
	@MessageHandler(names = "mouseButtonPressed")
	public void pressMouseButton(ClientHandler handler, Integer button, Location location) {
		Player player = server.getPlayer(handler);
		
		Set<MouseButton> set = mouseButtonsDown.get(player);
		
		if (set == null) {
			set = new HashSet<MouseButton>();
			mouseButtonsDown.put(player, set);
		}
		
		set.add(MouseButton.parse(button));
		
		
		PlayerClickMouseEvent event = new PlayerClickMouseEvent(player, ButtonState.PRESSED, MouseButton.parse(button), location);
		
		server.getEventManager().callEvent(event);
	}
	
	@MessageHandler(names = "mouseButtonReleased")
	public void releaseMouseButton(ClientHandler handler, Integer button, Location location) {
		Player player = server.getPlayer(handler);
		
		mouseButtonsDown.get(player).remove(MouseButton.parse(button));
		
		PlayerClickMouseEvent event = new PlayerClickMouseEvent(player, ButtonState.RELEASED, MouseButton.parse(button), location);
		
		server.getEventManager().callEvent(event);
		
	}
	
	
	
	
}
