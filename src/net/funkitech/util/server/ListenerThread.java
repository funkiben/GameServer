package net.funkitech.util.server;


public abstract class ListenerThread extends Thread {
	
	private volatile boolean finished = false;
	
	public ListenerThread(String name) {
		super(name);
		setDaemon(true);
	}
	
	public void exit() {
		finished = true;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	@Override
	public void run() {
		while (!finished) {
			listen();
		}
		
		onFinish();
	};
	
	public abstract void listen();
	public abstract void onFinish();

}
