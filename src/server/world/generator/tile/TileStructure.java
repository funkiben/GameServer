package server.world.generator.tile;

import java.util.ArrayList;
import java.util.List;

public class TileStructure {
	
	private final List<ChunkTileArrangement> chunks = new ArrayList<ChunkTileArrangement>();
	private final float chance;
	
	public TileStructure(float chance) {
		this.chance = chance;
	}
	
	public float getChance() {
		return chance;
	}
	
	public void addArrangement(ChunkTileArrangement arrangement) {
		chunks.add(arrangement);
	}
	
	public List<ChunkTileArrangement> getArrangements() {
		return new ArrayList<ChunkTileArrangement>(chunks);
	}
	
}
