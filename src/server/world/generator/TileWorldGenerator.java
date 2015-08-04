package server.world.generator;

import java.util.Arrays;

import net.funkitech.util.Location;
import server.world.Chunk;
import server.world.object.WorldObject;
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

		ChunkTilePlotter plotter = new ChunkTilePlotter(chunk);
		
		populateChunk(plotter);
		
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
	
	
	final class ChunkTilePlotter {
		
		private final WorldObject[][] tiles = new WorldObject[tilesAlongSide][tilesAlongSide];
		private final WorldObject[][] objects = new WorldObject[tilesAlongSide][tilesAlongSide];
		private final Chunk chunk;
		
		public ChunkTilePlotter(Chunk chunk) {
			this.chunk = chunk;
		}
		
		public Chunk getChunk() {
			return chunk;
		}
		
		public void setTile(int x, int y, WorldObjectType type, Object...customData) {
			Object[] arr = Arrays.copyOf(new Object[] {tileSize, tileSize}, customData.length + 2);
			for (int i = 0; i < customData.length; i++) {
				arr[i + 2] = customData[i];
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
	}

}
