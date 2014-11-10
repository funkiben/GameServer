package server.world;

import net.funkitech.util.Location;
import server.world.object.WorldObject;
import server.world.object.WorldObjectType;

public class TestWorldGenerator extends WorldGenerator {
	
	public TestWorldGenerator() {
		super("test");
	}

	@Override
	public void populateChunk(Chunk chunk) {
		
		WorldObject object;
		
		int tileSize = 50;
		
		for (int x = 0; x < Chunk.size; x += tileSize) {
			for (int y = 0; y < Chunk.size; y += tileSize) {
				object = new WorldObject(new Location(x, y).add(chunk.getLocation()), WorldObjectType.TILE_COBBLE, tileSize, tileSize, (int) (Math.random() * 10));
				chunk.addObject(object);
			}
		}
		
		int trees = (int) (3 + (Math.random() * 5));
		
		int treeWidth = 100;
		int treeHeight = 200;
		
		for (int i = 0; i < trees; i++) {
			Location loc = new Location(Math.random() * Chunk.size, Math.random() * Chunk.size).add(chunk.getLocation());
			object = new WorldObject(loc, WorldObjectType.TREE, treeWidth, treeHeight, (int) (Math.random() * 4));
			chunk.addObject(object);
		}
		
		
		
		
	}

}
