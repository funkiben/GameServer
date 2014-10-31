package server.world;

import java.util.HashMap;
import java.util.Map;


public abstract class WorldGenerator {
	
	private static final Map<String, WorldGenerator> generatorMap = new HashMap<String, WorldGenerator>();
	
	static {
		new TestWorldGenerator();
	}
	
	public static WorldGenerator byName(String name) {
		return generatorMap.get(name);
	}
	
	private final String name;
	protected World world;
	
	public WorldGenerator(String name) {
		this.name = name;
		generatorMap.put(name, this);
	}
	
	public String getName() {
		return name;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public abstract void populateChunk(Chunk chunk);

}
