package server.world.generator;

import java.util.Arrays;

import net.funkitech.util.Location;
import server.world.Chunk;
import server.world.object.WorldObject;
import server.world.object.WorldObjectType;

public class ChunkTilePlotter {
	
	private final int tileSize;
	private final int tilesAlongSide;
	private final Chunk chunk;
	private final TileWorldGenerator generator;
	final WorldObject[][] tiles;
	final WorldObject[][] objects;
	
	public ChunkTilePlotter(TileWorldGenerator generator, Chunk chunk) {
		this.generator = generator;
		this.tileSize = generator.getTileSize();
		this.tilesAlongSide = generator.getTilesAlongSide();
		this.chunk = chunk;
		tiles = new WorldObject[generator.getTilesAlongSide()][tilesAlongSide];
		objects = new WorldObject[tilesAlongSide][tilesAlongSide];
	}
	
	public Chunk getChunk() {
		return chunk;
	}
	
	public TileWorldGenerator getGenerator() {
		return generator;
	}
	
	public void setTile(int x, int y, WorldObjectType type, Object...customData) {
		Object[] preCustomData = new Object[] { tileSize, tileSize, 0};
		
		Object[] arr = Arrays.copyOf(preCustomData, customData.length + preCustomData.length);
		
		for (int i = 0; i < customData.length; i++) {
			arr[i + preCustomData.length] = customData[i];
		}
		
		tiles[y][x] = new WorldObject(new Location(x * tileSize, y * tileSize).add(chunk.getLocation()), type, arr);
	}
	
	public void removeTile(int x, int y) {
		tiles[y][x] = null;
	}
	
	public WorldObject getTile(int x, int y) {
		return tiles[y][x];
	}
	
	public WorldObject[][] getTiles() {
		return tiles;
	}
	
	public void setObject(int x, int y, WorldObjectType type, Object...customData) {
		objects[y][x] = new WorldObject(new Location(x * tileSize, y * tileSize).add(chunk.getLocation()), type, customData);
	}
	
	public void removeObjects(int x, int y) {
		objects[y][x] = null;
	}
	
	public WorldObject getObject(int x, int y) {
		return objects[y][x];
	}
	
	public WorldObject[][] getObjects() {
		return objects;
	}
	
	public void rawAddObject(double x, double y, WorldObjectType type, Object...customData) {
		chunk.addObject(new WorldObject(new Location(x, y).add(chunk.getLocation()), type, customData));
	}
	
	public void setImageIDs() {
		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles.length; x++) {
				getTile(x, y).getCustomData()[2] = TileImageDB.getImgID(x, y, this);
			}
		}
	}
	
}
