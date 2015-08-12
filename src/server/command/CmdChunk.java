package server.command;

import java.util.Map;

import server.world.Chunk;
import server.world.object.WorldObjectType;

public class CmdChunk extends CmdServer {

	public CmdChunk() {
		super("chunk", "Get information about chunks");
		
		addArg("x", ArgumentType.INTEGER, "X position of the chunk");
		addArg("y", ArgumentType.INTEGER, "Y position of the chunk");
		addFlag("c", "Create new chunk if there isn't one at given coordinates");
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		int x = (int) args.get("x");
		int y = (int) args.get("y");
		
		Chunk chunk = server.getWorld().getChunk(x, y, flags.containsKey("c"));
		
		if (chunk == null) {
			return "No chunk exists at given coordinates";
		}
		
		String str = new String();
		
		str += chunk.getSize() + " Kilobytes\n";
		str += chunk.getObjects().size() + " Objects\n";
		for (WorldObjectType type : WorldObjectType.values()) {
			str += chunk.getObjects(type).size() + " " + type + " Objects\n";
		}
		
		
		return str;
	}

}
