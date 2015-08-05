package server.world.generator;

import java.util.ArrayList;
import java.util.List;

import server.world.object.WorldObjectType;
import net.funkitech.util.PerlinNoiseGenerator;

public class TileV2WorldGenerator extends TileWorldGenerator {
	
	private static final double NOISE_MAX   =  Math.sqrt(2) / 2.0;
	private static final double NOISE_RANGE = NOISE_MAX * 2.0;
	
	
	private final PerlinNoiseGenerator noise;
	private final List<Biome> biomes = new ArrayList<Biome>();
	private final int noiseOffset;
	private final float noiseScale = 0.05f;
	
	public TileV2WorldGenerator() {
		super("tilesV2", 10);
		
		addBiome(WorldObjectType.TILE_COBBLE, 0.0f, 1.0f, 1);
		addBiome(WorldObjectType.TILE_GRASS,  0.0f, 1.0f, 2, 3);
		addBiome(WorldObjectType.TILE_WATER,  0.5f, 1.0f, 2, 3);
		
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
		
		int cx = plotter.getChunk().getX() * size;
		int cy = plotter.getChunk().getY() * size;
		
		WorldObjectType tile = WorldObjectType.TILE_GRASS;
		float highestNoise;
		Biome biome;
		int o;
		float n;
		
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				
				highestNoise = 0;
				
				for (int i = 0; i < biomes.size(); i++) {
					biome = biomes.get(i);
					o = (biome.layer * noiseOffset);
					n = noise.noise2((x + o + cx) * noiseScale, (y + o + cy) * noiseScale);
					n = (float) ((n + NOISE_MAX) / NOISE_RANGE);
					
					if (n >= highestNoise && n >= biome.minNoise && n <= biome.maxNoise) {
						highestNoise = n;
						tile = biome.tile;
					}
					
				}
				
				plotter.setTile(x, y, tile, 0);
				
			}
		}

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
