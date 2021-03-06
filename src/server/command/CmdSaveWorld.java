package server.command;

import java.util.Map;

public class CmdSaveWorld extends CmdServer {

	public CmdSaveWorld() {
		super("saveworld", "Saves the world");
		
		addFlag("a", "Saves all chunks, not just the changed ones.");
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		server.log("Saving world...");
		int amount = server.getWorld().save(flags.containsKey("a"));
		server.log("Done. Saved " + amount + " chunks");
		return "World saved";
	}

}
