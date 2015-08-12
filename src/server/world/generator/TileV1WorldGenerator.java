package server.world.generator;

import java.util.ArrayList;
import java.util.List;

import server.world.Chunk;
import server.world.object.WorldObjectType;
import net.funkitech.util.PerlinNoiseGenerator;

public class TileV1WorldGenerator extends TileWorldGenerator {
	
	private static final double NOISE_MAX   =  Math.sqrt(2) / 2.0;
	private static final double NOISE_RANGE = NOISE_MAX * 2.0;
	
	
	private final PerlinNoiseGenerator noise;
	private final List<Biome> biomes = new ArrayList<Biome>();
	private final int noiseOffset;
	private final float noiseScale = 0.05f;
	
	public TileV1WorldGenerator() {
		super("tilesV1", 80);
		
		//addBiome(WorldObjectType.TILE_COBBLE, 0.0f, 1.0f, 1);
		addBiome(WorldObjectType.TILE_GRASS,  0.0f, 1.0f, 1);
		addBiome(WorldObjectType.TILE_WATER,  0.5f, 1.0f, 1);
		
		noiseOffset = biomes.size() * 10;
		
		noise = new PerlinNoiseGenerator(getSeed());
		
	}
	
	private void addBiome(WorldObjectType groundTile, float minNoise, float maxNoise, int...layers) {
		for (int layer : layers) {
			biomes.add(new Biome(groundTile, minNoise, maxNoise, layer));
		}
	}
	
	

	@Override
	protected void populateChunk(ChunkTilePlotter plotter) {
		int size = getTilesAlongSide();
		
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				plotter.setTile(x, y, getTileAt(plotter.getChunk(), x, y));
			}
		}
		
	}
	
	public WorldObjectType getTileAt(Chunk chunk, int x, int y) {
		
		int cx = chunk.getX() * getTilesAlongSide();
		int cy = chunk.getY() * getTilesAlongSide();
		
		WorldObjectType tile = WorldObjectType.TILE_GRASS;
		double highestNoise = 0.0f;
		Biome biome;
		int o;
		double n;
		
		for (int i = 0; i < biomes.size(); i++) {
			biome = biomes.get(i);
			o = (biome.layer * noiseOffset);
			n = noise.noise2((x + o + cx) * noiseScale, (y + o + cy) * noiseScale);
			n = (n + NOISE_MAX) / NOISE_RANGE;
			
			if (n >= highestNoise && n >= biome.minNoise && n <= biome.maxNoise) {
				highestNoise = n;
				tile = biome.tile;
			}
			
		}
		
		return tile;
		
	}
	
	class Biome {
		
		WorldObjectType tile;
		float minNoise;
		float maxNoise;
		int layer;
		
		public Biome(WorldObjectType tile, float minNoise, float maxNoise, int layer) {
			this.tile = tile;
			this.minNoise = minNoise;
			this.maxNoise = maxNoise;
			this.layer = layer;
		}
		
		
	}

}
