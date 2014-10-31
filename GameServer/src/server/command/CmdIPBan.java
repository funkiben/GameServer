package server.command;

import java.util.Map;

import server.world.object.Player;


public class CmdIPBan extends CmdServer {

	public CmdIPBan() {
		super("banip", "Ban a IP address from logging into the server");
		
		addArg("ip", ArgumentType.STRING, "IP to ban");
		addArg("reason", ArgumentType.STRING, "Reason for the ban, use underscores for spaces", false);
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		String ip = (String) args.get("ip");
		String reason = ((String) args.get("reason"));
		
		if (server.getBanManager().isIPBanned(ip)) {
			
			server.getBanManager().unbanIP(ip);
			
			return "IP " + ip + " unbanned";
		}
		
		if (reason == null) {
			reason = "This IP address has been banned by the server!";
		} else {
			reason = reason.replace("_", " ");
		}
		
		Player player = server.getPlayerByIP(ip);
		
		if (player != null) {
			player.kick(reason);
		}
		
		server.getBanManager().banIP(ip, reason);
		
		return "Banned IP Address " + ip + ": " + reason;
		
	}
}
