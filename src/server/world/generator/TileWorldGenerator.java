package server.world.generator;

import server.world.Chunk;
import server.world.object.WorldObjectType;

public abstract class TileWorldGenerator extends WorldGenerator {
	
	private final int tileSize;
	private final int tilesAlongSide;
	
	public TileWorldGenerator(String name, int tileSize) {
		super(name);
		
		this.tileSize = tileSize;
		
		tilesAlongSide = Chunk.size / tileSize;
	}
	
	public int getTileSize() {
		return tileSize;
	}
	
	public int getTilesAlongSide() {
		return tilesAlongSide;
	}

	@Override
	public final void populateChunk(Chunk chunk) {

		ChunkTilePlotter plotter = new ChunkTilePlotter(this, chunk);
		
		populateChunk(plotter);
		
		plotter.setImageIDs();
		
		for (int y = 0; y < tilesAlongSide; y++) {
			for (int x = 0; x < tilesAlongSide; x++) {
				
				if (plotter.tiles[x][y] != null) {
					chunk.addObject(plotter.tiles[x][y]);
				}
				
				if (plotter.objects[x][y] != null) {
					chunk.addObject(plotter.objects[x][y]);
				}
				
			}
		}
		
	}
	
	protected abstract void populateChunk(ChunkTilePlotter plotter);
	protected abstract WorldObjectType getTileAt(Chunk chunk, int x, int y);

}
