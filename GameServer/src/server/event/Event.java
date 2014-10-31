package server.event;

public abstract class Event {
	private boolean cancelled;
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean b) {
		this.cancelled = b;
	}
	
	public String getName() {
		return getClass().getSimpleName();
	}
	
}