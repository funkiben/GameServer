package server.command;

import java.util.Map;

public class CmdChunks extends CmdServer {

	public CmdChunks() {
		super("chunks", "Displays information about the loaded chunks");
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		
		String str = new String();
		
		str += server.getWorld().getChunks().size() + " Chunks\n";
		str += server.getWorld().getObjects().size() + " Objects\n";
		
		return str;
	}

}
