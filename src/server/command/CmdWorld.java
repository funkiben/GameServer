package server.command;

import java.util.Map;

public class CmdWorld extends CmdServer {

	public CmdWorld() {
		super("world", "Displays information about the world");
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		
		String str = new String();
		
		str += server.getWorld().getChunks().size() + " Chunks\n";
		str += server.getWorld().getUnsavedChunks() + " Unsaved Chunks\n";
		str += server.getWorld().getObjects().size() + " Objects\n";
		str += server.getWorld().getSize() + " Megabytes\n";
		
		return str;
	}

}
