package server.world.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import server.world.Chunk;
import server.world.World;
import server.world.generator.tile.TileV1WorldGenerator;


public abstract class WorldGenerator {
	
	private static final Map<String, WorldGenerator> generatorMap = new HashMap<String, WorldGenerator>();
	
	static {
		new TestWorldGenerator();
		new TileV1WorldGenerator();
	}
	
	public static WorldGenerator byName(String name) {
		return generatorMap.get(name);
	}
	
	private final String name;
	protected World world;
	private int seed;
	
	public WorldGenerator(String name) {
		this.name = name;
		generatorMap.put(name, this);
	}
	
	public String getName() {
		return name;
	}
	
	public int getSeed() {
		return seed;
	}
	
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public Random getRandomForChunk(int x, int y) {
		return new Random(getChunksSeed(x, y));
	}
	
	public Random getRandomForChunk(Chunk chunk) {
		return new Random(getChunksSeed(chunk.getX(), chunk.getY()));
	}
	
	public int getChunksSeed(int x, int y) {
		return (y << seed) ^ x;
	}
	
	public int getChunksSeed(Chunk chunk) {
		return getChunksSeed(chunk.getX(), chunk.getY());
	}
	
	public abstract void populateChunk(Chunk chunk);

}
