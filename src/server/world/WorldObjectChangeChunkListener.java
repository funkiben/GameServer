package server.world;

import server.event.EventHandler;
import server.event.EventPriority;
import server.event.Listener;
import server.event.world.object.WorldObjectMoveEvent;

public class WorldObjectChangeChunkListener implements Listener {
	
	private final World world;
	
	public WorldObjectChangeChunkListener(World world) {
		this.world = world;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onObjectMove(WorldObjectMoveEvent event) {
		
		Chunk oldChunk = world.getChunk(event.getNewLocation());
		Chunk newChunk = world.getChunk(event.getPrevLocation());
		
		if (newChunk != oldChunk) {
			oldChunk.changeObjectsChunk(event.getWorldObject(), newChunk);
		}
	}
	
}
