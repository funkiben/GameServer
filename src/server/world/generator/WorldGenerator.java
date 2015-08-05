package server.world.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import server.world.Chunk;
import server.world.World;


public abstract class WorldGenerator {
	
	private static final Map<String, WorldGenerator> generatorMap = new HashMap<String, WorldGenerator>();
	private static int seed;
	
	public static void init(int s) {
		seed = s;
		new TestWorldGenerator();
		new TileV1WorldGenerator();
		new TileV2WorldGenerator();
	}
	
	public static WorldGenerator byName(String name) {
		return generatorMap.get(name);
	}
	
	private final String name;
	private final Random random;
	protected World world;
	
	public WorldGenerator(String name) {
		this.name = name;
		this.random = new Random(seed);
		generatorMap.put(name, this);
	}
	
	public String getName() {
		return name;
	}
	
	public int getSeed() {
		return seed;
	}
	
	public Random getRandom() {
		return random;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public abstract void populateChunk(Chunk chunk);

}
