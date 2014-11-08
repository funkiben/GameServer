package server.world.object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.funkitech.util.Location;
import net.funkitech.util.server.ClientHandler;
import net.funkitech.util.server.messaging.Message;
import server.accounts.UserAccount;
import server.event.player.PlayerMoveEvent;
import server.main.GameServer;
import server.world.Chunk;

public class Player extends WorldObject {
	
	private static final long serialVersionUID = -6691489083508913780L;
	
	public static List<Chunk> getPlayersChunks(Location location) {
		
		List<Chunk> list = new ArrayList<Chunk>();
		
		int tx = Chunk.toChunkX(location.getX());
		int ty = Chunk.toChunkY(location.getY());
		
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				list.add(GameServer.inst.getWorld().getChunk(x + tx, y + ty));
			}
		}
		
		return list;
	}
	
	
	
	private transient String name;
	private transient UserAccount account;
	private transient ClientHandler handler = null;
	
	public Player(Location location, String name) {
		super(location, WorldObjectType.PLAYER, name);
		
		this.name = name;
		account = GameServer.inst.getUserAccountDB().getAccount(name);
		
	}
	
	@Override
	public void onLoadFromChunk() {
		name = (String) getCustomData()[0];
		account = GameServer.inst.getUserAccountDB().getAccount(name);
		setVisible(false);
	}
	
	public String getName() {
		return name;
	}
	
	public UserAccount getAccount() {
		return account;
	}
	
	public ClientHandler getClientHandler() {
		return handler;
	}
	
	public void kick(String reason) {
		handler.disconnect(reason);
	}
	
	public void sendMessage(String name, Object...args) {
		try {
			handler.sendMessage(new Message(name, args));
		} catch (IOException e) {
			System.out.println("Error sending " + name + " message: ");
		}
	}
	
	public void sendMessage(Message...msgs) {
		try {
			handler.sendMessage(msgs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getIPAddress() {
		return handler.getAddress().toString();
	}
	
	@Override
	public boolean move(Location delta) {
		PlayerMoveEvent event = new PlayerMoveEvent(this, delta);
		GameServer.inst.getEventManager().callEvent(event);
		
		Location prevloc = event.getPrevLocation().clone();
		Location newloc = event.getNewLocation().clone();
		
		if (!event.isCancelled() && !event.getDelta().isZero()) {
			location.set(event.getNewLocation());
			
			updateWithPlayers();
			
			sendNewChunks(prevloc, newloc);
			
			return true;
			
		}
		
		return false;
	}
	
	public List<Chunk> getPlayersChunks() {
		return getPlayersChunks(location);
	}
	
	public void registerWithClient(ClientHandler handler) {
		this.handler = handler;
		
		Message msg = getMessage();
		msg.getArgs()[1] = WorldObjectType.CONTROLLED_PLAYER.getId();
		sendMessage(msg);
		
		for (Chunk chunk : getPlayersChunks()) {
			sendChunk(chunk);
		}
	}
	
	public void sendChunk(Chunk chunk) {
		for (WorldObject obj : chunk.getObjects()) {
			obj.updateWithPlayer(this);
		}
	}
	
	public void removeChunk(Chunk chunk) {
		for (WorldObject obj : chunk.getObjects()) {
			obj.removeFromPlayer(this);
		}
	}
	
	public boolean canSeeChunk(int x, int y) {
		int tx = getChunkX();
		int ty = getChunkY();
		
		return x <= tx + 1 && x >= tx - 1 && y <= ty + 1 && y >= ty - 1;
	}
	
	public boolean canSeeObjectsChunk(WorldObject object) {
		return canSeeChunk(object.getChunkX(), object.getChunkY());
	}
	
	private void sendNewChunks(Location prevloc, Location newloc) {
		List<Chunk> newChunks = Player.getPlayersChunks(newloc);
		List<Chunk> oldChunks = Player.getPlayersChunks(prevloc);
		
		outer:
		for (Chunk newchunk : newChunks) {
			for (Chunk oldchunk : oldChunks) {
				if (newchunk == oldchunk) {
					continue outer;
				}
			}
			sendChunk(newchunk);
		}
		
		outer:
		for (Chunk oldchunk : oldChunks) {
			for (Chunk newchunk : newChunks) {
				if (newchunk == oldchunk) {
					continue outer;
				}
			}
			
			removeChunk(oldchunk);
		}
	}
	
	public void sendChatMsg(String msg) {
		sendMessage("chat", msg);
	}
	
}
