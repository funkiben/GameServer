package server.command;

import java.util.Map;


public class CmdSaveWorld extends CmdServer {

	public CmdSaveWorld() {
		super("saveworld", "Saves the world");
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		server.getWorld().save();
		return "World saved";
	}

}
