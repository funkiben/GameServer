package server.world;

import server.world.object.WorldObject;
import server.world.object.WorldObjectType;
import net.funkitech.util.Location;


public class TestWorldGenerator extends WorldGenerator {
	
	public TestWorldGenerator() {
		super("test");
	}

	@Override
	public void populateChunk(Chunk chunk) {
		
		double blocks = 200;
		
		for (int x = 0; x < Chunk.size / blocks; x++) {
			Location location = chunk.getLocation().add(x * blocks, 50);
			WorldObject object = new WorldObject(world, location, WorldObjectType.BLOCK);
			chunk.addObject(object);
		}
		
		for (int x = 0; x < Chunk.size / blocks; x++) {
			Location location = chunk.getLocation().add(x * blocks, Chunk.size - 50);
			WorldObject object = new WorldObject(world, location, WorldObjectType.BLOCK);
			chunk.addObject(object);
		}
		
		for (int y = 0; y < Chunk.size / blocks; y++) {
			Location location = chunk.getLocation().add(50, y * blocks);
			WorldObject object = new WorldObject(world, location, WorldObjectType.BLOCK);
			chunk.addObject(object);
		}
		
		for (int y = 0; y < Chunk.size / blocks; y++) {
			Location location = chunk.getLocation().add(Chunk.size - 50, y * blocks);
			WorldObject object = new WorldObject(world, location, WorldObjectType.BLOCK);
			chunk.addObject(object);
		}
		
	}

}
