
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
import net.funkitech.util.Location;
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
		
		accountDB = new UserAccountDB(this);

		eventManager.registerListener(banManager);
		
		getMessageListeningManager().registerListeners(new PlayerMessageListener(), new PlayerButtonMessageListener(this));
		
		log("Loading world with " + config.getWorldGenerator().getName() + " WorldGenerator");
		world = new World(this, config.getWorldGenerator());
		log(world.getChunks().size() + " chunks loaded");
		
		new PlayerClickMouseListener(this);
		
		new ServerTickThread(this, config.getTPS());
		
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
	
	public Player enablePlayer(UserAccount account, ClientHandler handler) {
		Player player = (Player) world.getObject(account.getObjectID());
		
		if (player == null) {
			player = createNewPlayer(account.getName(), true);
			
			account.setObjectID(player.getId());
			account.save();
			
			log("WARNING: Player " + player.getName() + " already had an account but it being given a new Player instance!");
		} else {
			player.setVisible(true);
		}
		
		players.put(account.getName(), player);
		
		player.registerWithClient(handler);
		
		return player;
	}
	
	public Player createNewPlayer(String name, boolean visible) {
		Player player = new Player(new Location(0, 0), name);
		player.setVisible(visible);
		world.addObject(player);
		return player;
	}
	
	@Override
	public void onDisconnect(ClientHandler client) {
		Player player = getPlayer(client);
		
		if (player == null) {
			return;
		}
		
		PlayerDisconnectEvent event = new PlayerDisconnectEvent(player);
		eventManager.callEvent(event);
		
		players.remove(player.getName());
		
		player.setVisible(false);
		
		player.getAccount().updateLastOnlineDate();
		player.getAccount().save();
		
		for (Player p : players.values()) {
			p.sendMessage("playerDisconnect", player.getName());
		}
		
		log(player.getName() + " disconnected at " + player.getLocation());
	}
	
	@Override
	public void afterStopped() {
		log("Saving world...");
		int amount = world.save(false);
		log("Done. Saved " + amount + " chunks");
	}

}
