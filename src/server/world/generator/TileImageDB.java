package server.world.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import server.world.object.WorldObjectType;

public class TileImageDB {
	
	public static void init() {
		
		putDefaults(WorldObjectType.TILE_GRASS, 0, 37);
		putDefaults(WorldObjectType.TILE_COBBLE, 4, 5, 6);
		
		put(WorldObjectType.TILE_GRASS,
				entry(WorldObjectType.TILE_WATER, RelativePosition.UP_AND_RIGHT, 85),
				entry(WorldObjectType.TILE_WATER, RelativePosition.UP_AND_LEFT, 83),
				entry(WorldObjectType.TILE_WATER, RelativePosition.DOWN_AND_RIGHT, 91),
				entry(WorldObjectType.TILE_WATER, RelativePosition.DOWN_AND_LEFT, 89),
				
				entry(WorldObjectType.TILE_WATER, RelativePosition.UP, 104, 105, 106),
				entry(WorldObjectType.TILE_WATER, RelativePosition.DOWN, 93, 94, 95),
				entry(WorldObjectType.TILE_WATER, RelativePosition.RIGHT, 97, 99, 101),
				entry(WorldObjectType.TILE_WATER, RelativePosition.LEFT, 98, 100, 102),
				
				entry(WorldObjectType.TILE_WATER, RelativePosition.UPRIGHT, 103),
				entry(WorldObjectType.TILE_WATER, RelativePosition.UPLEFT, 107),
				entry(WorldObjectType.TILE_WATER, RelativePosition.DOWNRIGHT, 92),
				entry(WorldObjectType.TILE_WATER, RelativePosition.DOWNLEFT, 96)
		);
		
		
	}
	
	
	
	private static final Map<WorldObjectType, DBEntry[]> data = new HashMap<WorldObjectType, DBEntry[]>();
	private static final Map<WorldObjectType, Integer[]> defaults = new HashMap<WorldObjectType, Integer[]>();
	
	private static void put(WorldObjectType tile, DBEntry...entries) {
		data.put(tile, entries);
	}
	
	private static DBEntry entry(WorldObjectType tile, RelativePosition position, int...imgIDs) {
		return new DBEntry(tile, position, imgIDs);
	}
	
	private static void putDefaults(WorldObjectType tile, Integer...imgIds) {
		defaults.put(tile, imgIds);
	}
	
	public static int getImgID(int x, int y, ChunkTilePlotter plotter) {
		WorldObjectType tile = plotter.getTile(x, y).getType();
		
		if (data.containsKey(tile)) {
		
			DBEntry[] entries = data.get(tile);
				
			for (DBEntry entry : entries) {
				if (entry.position.isTileAt(entry.tile, x, y, plotter)) {
					return getRandomInt(entry.imgIDs, plotter.getGenerator().getRandom());
				}
				
			}
			
		}
		
		return 
				defaults.containsKey(tile) ? 
				getRandomInt(defaults.get(tile), plotter.getGenerator().getRandom()) : 
				0;
		
	}
	
	private static int getRandomInt(int[] arr, Random random) {
		return arr[random.nextInt(arr.length)];
	}
	
	private static int getRandomInt(Integer[] arr, Random random) {
		return arr[random.nextInt(arr.length)];
	}
	
	private static class DBEntry {
		WorldObjectType tile;
		RelativePosition position;
		int[] imgIDs;
		
		DBEntry(WorldObjectType tile, RelativePosition position, int[] imgIDs) {
			this.tile = tile;
			this.position = position;
			this.imgIDs = imgIDs;
		}
		
	}

}
