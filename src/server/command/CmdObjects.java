package server.command;

import java.util.Map;

import server.world.object.WorldObjectType;

public class CmdObjects extends CmdServer {

	public CmdObjects() {
		super("objects", "Lists the amount of objects of each type");
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		String ls = new String();
		
		for (WorldObjectType type : WorldObjectType.values()) {
			ls += server.getWorld().getObjects(type).size() + " " + type + "\n";
		}
		
		return ls;
	}

}
