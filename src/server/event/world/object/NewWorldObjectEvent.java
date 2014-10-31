package server.event.world.object;

import server.world.object.WorldObject;


public class NewWorldObjectEvent extends WorldObjectEvent {

	public NewWorldObjectEvent(WorldObject object) {
		super(object);
	}

}
