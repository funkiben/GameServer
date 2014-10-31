package server.event.world.object;

import server.world.object.WorldObject;
import net.funkitech.util.Location;


public class WorldObjectMoveEvent extends WorldObjectEvent {
	
	private final Location delta;
	
	public WorldObjectMoveEvent(WorldObject object, Location delta) {
		super(object);
		
		this.delta = delta;
		
	}
	
	public double getDeltaX() {
		return delta.getX();
	}
	
	public double getDeltaY() {
		return delta.getY();
	}
	
	public void setDeltaX(double x) {
		delta.setX(x);
	}
	
	public void setDeltaY(double y) {
		delta.setY(y);
	}
	
	public Location getDelta() {
		return delta;
	}
	
	public double getPrevX() {
		return getWorldObject().getLocation().getX();
	}
	
	public double getPrevY() {
		return getWorldObject().getLocation().getY();
	}
	
	public Location getPrevLocation() {
		return getWorldObject().getLocation();
	}
	
	public double getNewX() {
		return getWorldObject().getLocation().getX() + delta.getX();
	}
	
	public double getNewY() {
		return getWorldObject().getLocation().getY() + delta.getY();
	}
	
	public Location getNewLocation() {
		return getWorldObject().getLocation().add(delta);
	}
	
	

}
