package server.world.object;

import java.io.Serializable;
import java.util.Arrays;

import server.event.EventHandler;
import server.event.Listener;
import server.event.server.ServerTickEvent;
import server.event.world.object.WorldObjectMoveEvent;
import server.main.GameServer;
import server.world.Chunk;
import net.funkitech.util.Location;
import net.funkitech.util.server.messaging.Message;

public class WorldObject implements Serializable {

	private static final long serialVersionUID = -3138850811927672440L;
	
	private transient boolean save = true;
	private transient Listener serverTickListener = null;
	
	protected final Location location;
	private final WorldObjectType type;
	private final int id;
	private final Object[] customData;
	private boolean visible = true;

	public WorldObject(Location location, WorldObjectType type, Object...customData) {
		this.location = location;
		this.id = GameServer.inst.getWorld().getUnusedObjectID();
		this.type = type;
		this.customData = customData;
	}
	
	public void initializeFromChunk() {
		save = true;
		serverTickListener = null;
		onLoadFromChunk();
	}
	
	public Location getLocation() {
		return location.clone();
	}
	
	public boolean setLocation(Location location) {
		return move(location.subtract(this.location));
	}
	
	public void setSave(boolean b) {
		save = b;
	}
	
	public void setVisible(boolean b) {
		visible = b;
		
		if (!visible) {
			removeFromPlayers();
		} else {
			updateWithPlayers();
		}
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean canSave() {
		return save;
	}
	
	public boolean move(Location delta) {
		WorldObjectMoveEvent event = new WorldObjectMoveEvent(this, delta);
		GameServer.inst.getEventManager().callEvent(event);
		
		if (!event.isCancelled() && !event.getDelta().isZero()) {
			location.set(event.getNewLocation());
			
			updateWithPlayers();
			
			return true;
			
		}
		
		return false;
	}
	
	public int getId() {
		return id;
	}
	
	public WorldObjectType getType() {
		return type;
	}
	
	public Object[] getCustomData() {
		return customData;
	}
	
	public Chunk getChunk() {
		return GameServer.inst.getWorld().getChunk(location);
	}
	
	public void updateWithPlayers() {
		for (Player player : GameServer.inst.getOnlinePlayes()) {
			updateWithPlayer(player);
		}
	}
	
	public boolean updateWithPlayer(Player player) {
		if (player != this && player.canSeeObjectsChunk(this) && visible) {
			player.sendMessage(getMessage());
			return true;
		}
		
		return false;
	}
	
	public void removeFromPlayers() {
		for (Player player : GameServer.inst.getOnlinePlayes()) {
			removeFromPlayer(player);
		}
	}
	
	public boolean removeFromPlayer(Player player) {
		if (player != this) {
			player.sendMessage("worldobjectRemoved", id);
			return true;
		}
		
		return false;
	}
	
	public int getChunkX() {
		return Chunk.toChunkX(location.getX());
	}
	
	public int getChunkY() {
		return Chunk.toChunkY(location.getY());
	}
	
	public Message getMessage() {
		return new Message("worldobject", id, type.getId(), location.clone(), Arrays.copyOf(customData, customData.length));
	}
	
	public void onLoadFromChunk() {
		
	}
	
	public void enableServerTickListener() {
		serverTickListener = new ServerTickListener(this);
		GameServer.inst.getEventManager().registerListener(serverTickListener);
	}
	
	public void disableServerTickListener() {
		if (serverTickListener != null) {
			GameServer.inst.getEventManager().unregisterListener(serverTickListener);
			serverTickListener = null;
		}
	}
	
	public boolean isServerTickListenerEnabled() {
		return serverTickListener != null;
	}
	
	public void onServerTick(int ticks) {
		
	}
	
	public static class ServerTickListener implements Listener {
		
		private final WorldObject object;
		
		public ServerTickListener(WorldObject object) {
			this.object = object;
		}
		
		@EventHandler
		public void onTick(ServerTickEvent event) {
			object.onServerTick(event.getTicks());
		}
		
		
	}
	
	
	
}
