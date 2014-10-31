package server.command;

import java.util.Map;


public class CmdHelp extends Command {

	public CmdHelp() {
		super("help", "Lists all valid commands");
		
	}

	@Override
	public String run(Map<String, Object> flags, Map<String, Object> args) {
		String ls = "Commands:\n\n";
		for (Command cmd : manager.map.values()) {
			ls += cmd.getUsage() + "\n\n";
		}
		
		
		return ls;
	}



}
