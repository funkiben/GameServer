package server.main;

import server.event.server.ServerTickEvent;

public class ServerTickThread extends Thread {

	private final GameServer server;
	private final float tps;
	private int ticks = 0;
	
	public ServerTickThread(GameServer server, float tps) {
		super();
		
		this.server = server;
		this.tps = tps;
		
		setDaemon(true);
		
		start();
	}
	
	public int getTicks() {
		return ticks;
	}
	
	public void run() {
		while (true) {
			
			ticks++;
			
			server.getEventManager().callEvent(new ServerTickEvent(ticks));
			
			try {
				Thread.sleep((long) (1000 / tps));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
