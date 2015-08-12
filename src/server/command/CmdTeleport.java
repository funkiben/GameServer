package server.command;

import java.util.Map;

import server.world.object.Player;

public class CmdTeleport extends CmdServer {

	public CmdTeleport() {
		super("tp", "Teleport a player to a given location");
		
		addArg("player", ArgumentType.STRING, "Player to be teleported");
		addArg("x", ArgumentType.DECIMAL, "X of the destination");
		addArg("y", ArgumentType.DECIMAL, "Y of the destination");
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		Player player = server.getPlayer((String) args.get("player"));
		
		if (player == null) {
			return "Could not find player";
		}
		
		double x = (double) args.get("x");
		double y = (double) args.get("y");
		
		boolean success = player.teleport(x, y);
		
		return success ? "Player teleported to " + player.getLocation() : "Player could not be teleported!";
	}

}
