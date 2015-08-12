package server.world.generator;

import server.world.object.WorldObjectType;

public enum RelativePosition {
	
	
	UP				( 0, -1),
	DOWN			( 0,  1),
	LEFT			(-1,  0),
	RIGHT			( 1,  0),
	UPRIGHT			( 1, -1),
	UPLEFT			(-1, -1),
	DOWNRIGHT		( 1,  1),
	DOWNLEFT		(-1,  1),
	UP_AND_RIGHT	(RelativePosition.UP, RelativePosition.RIGHT),
	UP_AND_LEFT		(RelativePosition.UP, RelativePosition.LEFT),
	DOWN_AND_RIGHT	(RelativePosition.DOWN, RelativePosition.RIGHT),
	DOWN_AND_LEFT	(RelativePosition.DOWN, RelativePosition.LEFT),
	RIGHT_AND_LEFT	(RelativePosition.RIGHT, RelativePosition.RIGHT),
	UP_AND_DOWN		(RelativePosition.UP, RelativePosition.DOWN);
	
	
	
	private final int x;
	private final int y;
	private final RelativePosition[] positions;
	
	RelativePosition(int x, int y) {
		this.x = x;
		this.y = y;
		positions = null;
	}
	
	RelativePosition(RelativePosition...positions) {
		x = 0;
		y = 0;
		this.positions = positions;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isTileAt(WorldObjectType tile, int tx, int ty, ChunkTilePlotter plotter) {
		if (positions == null) {
			int dx = tx + x;
			int dy = ty + y;
			
			int size = plotter.tiles.length;
			
			if (dx >= 0 && dx < size && dy >= 0 && dy < size) {
				if (plotter.getTile(dx, dy).getType() == tile) {
					return true;
				}
			} else {
				if (plotter.getGenerator().getTileAt(plotter.getChunk(), dx, dy) == tile) {
					return true;
				}
			}
			
			return false;
			
		} else {
			
			for (RelativePosition pos : positions) {
				if (!pos.isTileAt(tile, tx, ty, plotter)) {
					return false;
				}
			}
			
			return true;
		}
	}
	
//	public WorldObjectType[] getTilesAt(int tx, int ty, ChunkTilePlotter plotter) {
//		WorldObjectType[] tiles = new WorldObjectType[4];
//		
//		if (positions == null) {
//			int dx = tx + x;
//			int dy = ty + y;
//			
//			int size = plotter.tiles.length;
//			
//			if (dx >= 0 && dx < size && dy >= 0 && dy < size) {
//				tiles[0] = plotter.getTile(dx, dy).getType();
//			} else {
//				tiles[0] = plotter.getGenerator().getTileAt(plotter.getChunk(), dx, dy);
//			}
//			
//		} else {
//			
//			for (int i = 0; i < positions.length; i++) {
//				tiles[i] = positions[i].getTilesAt(tx, ty, plotter)[0];
//			}
//		
//		}
//		
//		return tiles;
//		
//	}
	
}
