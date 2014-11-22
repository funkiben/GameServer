package server.world.generator.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.funkitech.util.Location;
import server.world.Chunk;
import server.world.object.WorldObjectType;

public class TileV1WorldGenerator extends TileWorldGenerator {
	
	private final static int tileSize = 80;
	
	private final List<TileStructure> tileStructures = new ArrayList<TileStructure>();
	private final Map<Location,ChunkTileArrangement> chunkArrangements = new HashMap<Location,ChunkTileArrangement>();
	
	public TileV1WorldGenerator() {
		super("tilesV1", tileSize);
		
	}
	
	public void addTileStructure(TileStructure ts) {
		tileStructures.add(ts);
	}
	
	private void plotArrangement(ChunkTilePlotter plotter, ChunkTileArrangement arrangement) {
		for (int y = 0; y < getTilesAlongSide(); y++) {
			for (int x = 0; x < getTilesAlongSide(); x++) {
				
				if (arrangement.getTileTypeAt(x, y) != null) {
					plotter.setTile(x, y, arrangement.getTileTypeAt(x, y), arrangement.getTileDataAt(x, y));
				}
				
				if (arrangement.getObjectTypeAt(x, y) != null) {
					plotter.setObject(x, y, arrangement.getObjectTypeAt(x, y), arrangement.getObjectDataAt(x, y));
				}
				
			}
		}
	}
	
	@Override
	protected void populateChunk(ChunkTilePlotter plotter) {
		
		Chunk chunk = plotter.getChunk();
		
		int tx = chunk.getX();
		int ty = chunk.getY();
		
		Location loc = new Location(tx, ty);
		
		Random random = plotter.getRandom();
		
		
		if (chunkArrangements.containsKey(loc)) {
			plotArrangement(plotter, chunkArrangements.get(loc));
			chunkArrangements.remove(loc);
			return;
		}
		
		
		
		int dirx = 1;
		int diry = 1;
		
		TileStructure tileStructure = null;
		
		for (TileStructure struct : tileStructures) {
			if (random.nextFloat() * 100 <= struct.getChance()) {
				
				outer:
				for (int x = -1; x <= 1; x += 2) {
					inner:
					for (int y = -1; y <= 1; y += 2) {
						
						for (ChunkTileArrangement a : struct.getArrangements()) {
							if (world.isChunkAt((a.getX() * x) + tx, (a.getX() * y) + ty)) {
								continue inner;
							}
						}
						
						dirx = x;
						diry = y;
						tileStructure = struct;
						break outer;
					}
				}
				
			}
		}
		
		if (tileStructure == null) {
			for (int x = 0; x < getTilesAlongSide(); x++) {
				for (int y = 0; y < getTilesAlongSide(); y++) {
					plotter.setTile(x, y, WorldObjectType.TILE_COBBLE);
				}
			}
			return;
		}
		
		for (ChunkTileArrangement a : tileStructure.getArrangements()) {
			if (a.getX() == 0 && a.getY() == 0) {
				plotArrangement(plotter, a);
			} else {
				chunkArrangements.put(new Location((a.getX() * dirx) + tx, (a.getY() * diry) + ty), a);
				world.getChunk((a.getX() * dirx) + tx, (a.getY() * diry) + ty);
			}
		}
		
		
		
		
	}
	
	

}
