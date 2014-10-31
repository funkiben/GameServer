package server.command;
import java.util.Map;

import server.world.object.Player;


public class CmdOnline extends CmdServer {

	public CmdOnline() {
		super("online", "Lists who is online");
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		String ls = new String();
		
	
		for (Player player : server.getOnlinePlayes()) {
			ls += player.getName() + "  " + player.getIPAddress() + "  " + player.getLocation() + "\n";
		}
		
		return ls;
	}

}
