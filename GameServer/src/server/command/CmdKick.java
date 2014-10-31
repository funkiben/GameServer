package server.command;

import java.util.Map;

import server.world.object.Player;


public class CmdKick extends CmdServer {

	public CmdKick() {
		super("kick", "Kick a player from the server.");
		
		addArg("player", ArgumentType.STRING, "Player to kick");
		addArg("reason", ArgumentType.STRING, "Reason for the kick, use underscores for spaces");
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		String playerName = (String) args.get("player");
		String reason = ((String) args.get("reason")).replace("_", " ");
		
		Player player = server.getPlayer(playerName);
		
		if (player == null) {
			return "Could not find player " + player;
		}
		
		player.kick(reason);
		
		
		return "Player kicked";
	}

}
