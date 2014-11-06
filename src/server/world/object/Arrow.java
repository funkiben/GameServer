package server.world.object;

import server.main.GameServer;
import net.funkitech.util.Location;

public class Arrow extends WorldObject {
	
	private static final long serialVersionUID = 1L;
	
	private static final int LIFE_SPAN = 20;
	private static final float SPEED = 30F;
	
	
	private final Location velocity;
	private int ticksAlive = 0;
	
	public Arrow(Location location, Location direction) {
		super(location, WorldObjectType.ARROW, direction.normalize().multiply(SPEED));
		
		this.velocity = (Location) getCustomData()[0];
		
		setSave(false);
		
		enableServerTickListener();
		
	}
	
	public Location getVelocity() {
		return velocity;
	}
	
	@Override
	public void onServerTick(int ticks) {
		ticksAlive++;
		
		if (ticksAlive > LIFE_SPAN) {
			GameServer.inst.getWorld().removeObject(this);
			return;
		}
		
		move(velocity);
	}
	
	

}
