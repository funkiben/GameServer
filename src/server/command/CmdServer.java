package server.command;

import server.main.GameServer;



public abstract class CmdServer extends Command {
	
	protected final GameServer server;
	
	public CmdServer(String name, String desc) {
		super(name, desc);
		
		this.server = GameServer.inst;
		
	}

	
}
