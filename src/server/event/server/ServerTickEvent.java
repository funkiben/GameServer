package server.event.server;

import server.event.Event;

public class ServerTickEvent extends Event {
	
	private final int ticks;
	
	public ServerTickEvent(int ticks) {
		this.ticks = ticks;
	}
	
	public int getTicks() {
		return ticks;
	}
	
}
