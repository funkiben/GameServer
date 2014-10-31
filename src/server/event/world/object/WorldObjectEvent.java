package server.event.world.object;

import server.event.Event;
import server.world.object.WorldObject;


public class WorldObjectEvent extends Event {
	
	private final WorldObject object;
	
	public WorldObjectEvent(WorldObject object) {
		this.object = object;
	}
	
	public WorldObject getWorldObject() {
		return object;
	}

}
