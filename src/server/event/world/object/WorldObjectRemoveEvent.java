package server.event.world.object;

import server.world.object.WorldObject;


public class WorldObjectRemoveEvent extends WorldObjectEvent {

	public WorldObjectRemoveEvent(WorldObject object) {
		super(object);
		
	}

}
