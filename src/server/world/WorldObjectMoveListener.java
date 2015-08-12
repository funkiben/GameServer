package server.world;

import server.event.EventHandler;
import server.event.EventPriority;
import server.event.Listener;
import server.event.world.object.WorldObjectMoveEvent;

public class WorldObjectMoveListener implements Listener {
	
	private final World world;
	
	public WorldObjectMoveListener(World world) {
		this.world = world;
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onObjectMove(WorldObjectMoveEvent event) {
		
		Chunk oldChunk = world.getChunk(event.getPrevLocation(), true);
		Chunk newChunk = world.getChunk(event.getNewLocation(), true);
		
		if (newChunk != oldChunk) {
			oldChunk.changeObjectsChunk(event.getWorldObject(), newChunk);
		}
		
		if (event.getWorldObject().canSave()) {
			newChunk.setNeedsSave(true);
		}
	}
	
}
