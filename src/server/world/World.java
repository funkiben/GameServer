package server.world;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.main.GameServer;
import server.world.object.WorldObject;
import net.funkitech.util.Location;


public class World {
	
	private final WorldGenerator generator;
	private final File folder = new File("world");
	private final Map<Location, Chunk> chunkMap = new HashMap<Location, Chunk>();

	public World(WorldGenerator generator) {
		this.generator = generator;
		generator.setWorld(this);
		
		folder.mkdir();
		
		load();
		
	}
	
	public void load() {
		chunkMap.clear();
		
		for (File file : folder.listFiles()) {
			if (!file.getName().equals(".DS_Store")) {
				Chunk chunk = new Chunk(file);
				chunkMap.put(chunk.getLocation(), chunk);
			}
		}
	}
	
	public File getFolder() {
		return folder;
	}
	
	public WorldGenerator getGenerator() {
		return generator;
	}
	
	public Chunk getChunk(Location location) {
		return getChunk(Chunk.toChunkX(location.getX()), Chunk.toChunkY(location.getY()));
	}
	
	public Chunk getChunk(int x, int y) {
		Chunk chunk = chunkMap.get(Chunk.getChunkLocation(x, y));
		
		if (chunk == null) {
			GameServer.inst.log("Generating new chunk " + x + ", " + y);
			chunk = new Chunk(this, x, y);
			chunkMap.put(chunk.getLocation(), chunk);
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
		getChunk(object.getLocation()).addObject(object);
	}
	
	public int getUnusedObjectID() {
		int id = (int) (Math.random() * Integer.MAX_VALUE);
			
		if (getObject(id) != null) {
			return getUnusedObjectID();
		}
		
		return id;
	}
	
	public void save() {
		for (Chunk chunk : chunkMap.values()) {
			chunk.save();
		}
	}

}
