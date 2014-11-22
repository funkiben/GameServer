package server.world.generator.tile;

import server.world.object.WorldObjectType;

public class ChunkTileArrangement {
	
	private final int x;
	private final int y;
	private final WorldObjectType[][] tiles;
	private final Object[][][] tileData;
	private final WorldObjectType[][] objects;
	private final Object[][][] objectData;
	
	public ChunkTileArrangement(int x, int y, WorldObjectType[][] tiles, Object[][][] tileData, WorldObjectType[][] objects, Object[][][] objectData) {
		this.x = x;
		this.y = y;
		this.tiles = tiles;
		this.tileData = tileData;
		this.objects = objects;
		this.objectData = objectData;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public WorldObjectType getTileTypeAt(int x, int y) {
		return tiles[y][x];
	}
	
	public Object[] getTileDataAt(int x, int y) {
		return tileData[y][x];
	}
	
	public WorldObjectType getObjectTypeAt(int x, int y) {
		return objects[y][x];
	}
	
	public Object[] getObjectDataAt(int x, int y) {
		return objectData[y][x];
	}
	
	public WorldObjectType[][] getTiles() {
		return tiles;
	}
	
	public WorldObjectType[][] getObjects() {
		return objects;
	}

	public int getHeight() {
		return objects.length;
	}
	
	public int getWidth() {
		return objects[0].length;
	}
	
}
