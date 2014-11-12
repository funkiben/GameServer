package server.world.generator;

import server.world.object.WorldObjectType;

public class TileV1WorldGenerator extends TileWorldGenerator {
	
	private final static int tileSize = 80;
	
	public TileV1WorldGenerator() {
		super("tilesV1", tileSize);
		
	}

	@Override
	protected void populateChunk(ChunkTilePlotter plotter) {
		
		for (int x = 0; x < getTilesAlongSide(); x++) {
			for (int y = 0; y < getTilesAlongSide(); y++) {
				plotter.setTile(x, y, WorldObjectType.TILE_COBBLE, (int) (Math.random() * 10));
			}
		}
		
		for (int i = 0; i < Math.random() * 5; i++) {
			int x = (int) (Math.random() * getTilesAlongSide());
			int y = (int) (Math.random() * getTilesAlongSide());
			
			plotter.setObject(x, y, WorldObjectType.TREE, 150, 250, (int) (Math.random() * 2));
		}
		
		
		
		
		if (plotter.getChunk().getX() == 0 && plotter.getChunk().getY() == 0) {
			plotter.getChunk().setData(0, getTilesAlongSide() / 2);
		}
		
		
		
		
		
		
	}

}
