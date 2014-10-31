
package server.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.accounts.*;
import server.event.EventManager;
import server.event.player.PlayerDisconnectEvent;
import server.world.World;
import server.world.object.Player;
import net.funkitech.util.server.ClientHandler;
import net.funkitech.util.server.Server;

public class GameServer extends Server {
		
	
	public static GameServer inst;
	
	private final Map<String,Player> players = new HashMap<String,Player>();
	private final EventManager eventManager = new EventManager();
	private final BanManager banManager = new BanManager();
	private final World world;
	private final UserAccountDB accountDB;
	private final ServerConfig config;
	
	public GameServer(ServerConfig config) throws IOException {
		super("GameServer", config.getPort());
		log("Server started on port " + config.getPort());
		
		inst = this;
		
		this.config = config;
		
		getMessageListeningManager().registerListeners(new CreateAccountMessageListener(), new LoginMessageListener(), new PlayerMessageListener());
		
		eventManager.registerListener(banManager);
		
		accountDB = new UserAccountDB();
		
		log("Loading world with " + config.getWorldGenerator().getName() + " WorldGenerator");
		world = new World(config.getWorldGenerator());
		log(world.getChunks().size() + " chunks loaded");
		
	}
	
	public ServerConfig getConfig() {
		return config;
	}
	
	public World getWorld() {
		return world;
	}
	
	public UserAccountDB getUserAccountDB() {
		return accountDB;
	}
	
	public EventManager getEventManager() {
		return eventManager;
	}
	
	public BanManager getBanManager() {
		return banManager;
	}
	
	public List<Player> getOnlinePlayes() {
		List<Player> list = new ArrayList<Player>();
		list.addAll(players.values());
		return list;
	}
	
	public Player getPlayer(String player) {
		return players.get(player);
	}
	
	public Player getPlayer(ClientHandler clientHandler) {
		for (Player player : players.values()) {
			if (player.getClientHandler().equals(clientHandler)) {
				return player;
			}
		}
		
		return null;
	}
	
	public Player getPlayerByIP(String ip) {
		for (Player player : players.values()) {
			if (player.getIPAddress().equals(ip)) {
				return player;
			}
		}
		
		return null;
	}
	
	public Player addPlayer(UserAccount account, ClientHandler handler) {
		Player player = new Player(account, handler);
		players.put(account.getName(), player);
		world.addObject(player);
		return player;
	}
	
	@Override
	public void onDisconnect(ClientHandler client) {
		Player player = getPlayer(client);
		
		if (player == null) {
			return;
		}
		
		players.remove(player.getName());
		world.removeObject(player);
		
		player.getAccount().setLocation(player.getLocation());
		player.getAccount().updateLastOnlineDate();
		player.getAccount().save();
		
		for (Player p : players.values()) {
			p.sendMessage("playerDisconnect", player.getName());
		}
		

		PlayerDisconnectEvent event = new PlayerDisconnectEvent(player);
		eventManager.callEvent(event);
		
		log(player.getName() + " disconnected at " + player.getLocation());
	}
	
	@Override
	public void afterStopped() {
		log("Saving world...");
		world.save();
		log("Done");
	}

}
