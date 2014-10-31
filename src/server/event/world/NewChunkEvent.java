package server.event.world;

import server.world.Chunk;

public class NewChunkEvent extends ChunkEvent {

	public NewChunkEvent(Chunk chunk) {
		super(chunk);
	}

}
