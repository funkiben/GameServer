package server.world.object;

import java.io.Serializable;
import java.util.Arrays;

import server.event.world.object.WorldObjectMoveEvent;
import server.main.GameServer;
import server.world.Chunk;
import server.world.World;
import net.funkitech.util.Location;
import net.funkitech.util.server.messaging.Message;

public class WorldObject implements Serializable {

	private static final long serialVersionUID = -3138850811927672440L;
	
	private final transient World world;
	private transient boolean save = true;
	
	protected final Location location;
	private final WorldObjectType type;
	private final int id;
	private final Object[] customData;
	private boolean visible = true;

	public WorldObject(World world, Location location, WorldObjectType type, Object...customData) {
		this.world = world;
		this.location = location;
		this.id = world.getUnusedObjectID();
		this.type = type;
		this.customData = customData;
	}
	
	public World getWorld() {
		return world;
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
		return world.getChunk(location);
	}
	
	public void updateWithPlayers() {
		for (Player player : GameServer.inst.getOnlinePlayes()) {
			updateWithPlayer(player);
		}
	}
	
	public boolean updateWithPlayer(Player player) {
		if (player != this && player.canSeeObjectsChunk(this) && isVisible()) {
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
	
}
