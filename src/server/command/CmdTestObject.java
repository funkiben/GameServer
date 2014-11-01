package server.command;

import java.util.Map;

import server.world.object.WorldObject;

public class CmdTestObject extends CmdServer {

	public CmdTestObject() {
		super("testobject", "Test for an object");
		addArg("id", ArgumentType.INTEGER, "ID of the object to test for.");
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		
		int id = (Integer) args.get("id");
		
		WorldObject obj = server.getWorld().getObject(id);
		
		if (obj != null) {
			return obj.getType() + " Object found at chunk " + obj.getChunkX() + "," + obj.getChunkY() + " that matches ID (" + (!obj.isVisible() ? "invisible" : "visible") + ")";
		}
		
		return "Object not found";
	}

}
