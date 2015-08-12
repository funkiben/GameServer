package server.world;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.main.GameServer;
import server.world.generator.WorldGenerator;
import server.world.object.WorldObject;
import server.world.object.WorldObjectType;
import net.funkitech.util.Location;


public class World {
	
	private final GameServer server;
	private final WorldGenerator generator;
	private final File folder = new File("world");
	private final Map<Location, Chunk> chunkMap = new HashMap<Location, Chunk>();

	public World(GameServer server, WorldGenerator generator) {
		this.server = server;
		this.generator = generator;
		generator.setWorld(this);
		
		folder.mkdir();
		
		load();
		
		server.getEventManager().registerListener(new WorldObjectMoveListener(this));
		
	}
	
	public void load() {
		chunkMap.clear();
		
		for (File file : folder.listFiles()) {
			if (!file.getName().equals(".DS_Store")) {
				Chunk chunk = new Chunk(this, file);
				chunkMap.put(chunk.getLocation(), chunk);
			}
		}
	}
	
	public File getFolder() {
		return folder;
	}
	
	public GameServer getServer() {
		return server;
	}
	
	public WorldGenerator getGenerator() {
		return generator;
	}
	
	public Chunk getChunk(Location location, boolean createNew) {
		return getChunk(Chunk.toChunkX(location.getX()), Chunk.toChunkY(location.getY()), createNew);
	}
	
	public Chunk getChunk(int x, int y, boolean createNew) {
		Chunk chunk = chunkMap.get(Chunk.getChunkLocation(x, y));
		
		if (chunk == null) {
			if (createNew) {
				server.log("Generating new chunk " + x + ", " + y);
				chunk = new Chunk(this, x, y);
				chunkMap.put(chunk.getLocation(), chunk);
			} else {
				return null;
			}
		}
		
		return chunk;
	}
	
	public List<Chunk> getChunks() {
		return new ArrayList<Chunk>(chunkMap.values());
	}
	
	public WorldObject getObject(int id) {
		for (Chunk chunk : chunkMap.values()) {
			WorldObject obj = chunk.getObject(id);
			
			if (obj != null) {
				return obj;
			}
		}
		
		return null;
	}
	
	public List<WorldObject> getObjects() {
		List<WorldObject> list = new ArrayList<WorldObject>();
		
		for (Chunk chunk : chunkMap.values()) {
			list.addAll(chunk.getObjects());
		}
		
		return list;
	}
	
	public List<WorldObject> getObjects(WorldObjectType type) {
		List<WorldObject> list = new ArrayList<WorldObject>();
		
		for (Chunk chunk : chunkMap.values()) {
			list.addAll(chunk.getObjects(type));
		}
		
		return list;
	}
	
	public boolean removeObject(int id) {
		for (Chunk chunk : chunkMap.values()) {
			if (chunk.removeObject(id)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean removeObject(WorldObject object) {
		return removeObject(object.getId());
	}
	
	public void addObject(WorldObject object) {
		getChunk(object.getLocation(), true).addObject(object);
	}
	
	public int getUnusedObjectID() {
		int id = (int) (Math.random() * Integer.MAX_VALUE);
			
		if (getObject(id) != null) {
			return getUnusedObjectID();
		}
		
		return id;
	}
	
	public int getUnsavedChunks() {
		int chunks = 0;
		
		for (Chunk chunk : chunkMap.values()) {
			if (chunk.needsSave()) {
				chunks++;
			}
		}
		
		return chunks;
	}
	
	public int save(boolean all) {
		int chunks = 0;
		
		for (Chunk chunk : chunkMap.values()) {
			if (chunk.needsSave() || all) {
				chunk.save();
				chunks++;
			}
		}
		
		return chunks;
	}
	
	public double getSize() {
		long size = 0;
		
		for (Chunk chunk : chunkMap.values()) {
			size += chunk.getSize();
		}
		
		return size / 1000D;
	}

}
