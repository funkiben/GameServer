package server.event.world;

import server.event.Event;
import server.world.Chunk;

public class ChunkEvent extends Event {

	private final Chunk chunk;
	
	public ChunkEvent(Chunk chunk) {
		this.chunk = chunk;
	}
	
	public Chunk getChunk() {
		return chunk;
	}

}
