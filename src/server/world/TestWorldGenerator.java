package server.world;

import server.world.object.WorldObject;
import server.world.object.WorldObjectType;

public class TestWorldGenerator extends WorldGenerator {
	
	public TestWorldGenerator() {
		super("test");
	}

	@Override
	public void populateChunk(Chunk chunk) {
		
		WorldObject object;
		
		
		for (int i = 100; i <= 700; i+= 100) {
			object = new WorldObject(world, chunk.getLocation().add(i, 100), WorldObjectType.BLOCK);
			chunk.addObject(object);
			
			object = new WorldObject(world, chunk.getLocation().add(i, 700), WorldObjectType.BLOCK);
			chunk.addObject(object);
			
			object = new WorldObject(world, chunk.getLocation().add(700, i), WorldObjectType.BLOCK);
			chunk.addObject(object);
			
			object = new WorldObject(world, chunk.getLocation().add(100, i), WorldObjectType.BLOCK);
			chunk.addObject(object);
			
		}
		
//		for (WorldObject obj : chunk.getObjects()) {
//			if (obj.getChunkX() != chunk.getX() || obj.getChunkY() != chunk.getY()) {
//				System.out.println("NOPE!");
//			}
//		}
		
		
	}

}
