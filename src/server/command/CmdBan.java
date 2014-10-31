package server.command;

import java.util.Map;

import server.world.object.Player;


public class CmdBan extends CmdServer {

	public CmdBan() {
		super("ban", "Bans a player by their username");
		
		addArg("player", ArgumentType.STRING, "Player to ban");
		addArg("reason", ArgumentType.STRING, "Reason for the ban, use underscores for spaces", false);
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		String playerName = (String) args.get("player");
		String reason = ((String) args.get("reason"));
		
		if (server.getBanManager().isPlayerBanned(playerName)) {
			
			server.getBanManager().unbanPlayer(playerName);
			
			return "Player " + playerName + " unbanned";
		}
		
		if (reason == null) {
			reason = "You have been banned by the server!";
		} else {
			reason = reason.replace("_", " ");
		}
		
		Player player = server.getPlayer(playerName);
		
		if (player != null) {
			player.kick(reason);
		}
		
		server.getBanManager().banPlayer(playerName, reason);
		
		String warn = new String();
		if (!server.getUserAccountDB().hasAccount(playerName)) {
			warn = "WARNING: That player does not exist!\n";
		}
		
		return warn + "Player " + playerName + " has been banned: " + reason;
	}
		
	

}
