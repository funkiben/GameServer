package server.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
	
	public final Map<String,Command> map = new HashMap<String,Command>();

	
	public CommandManager() {
		register(new CmdHelp());
		
	}
	
	public void register(Command...commands) {
		for (Command command : commands) {
			map.put(command.getName(), command);
			command.manager = this;
		}
	}
	
	public String parseCommand(String str) {
		
		String[] words = str.split(" ");
		
		Command command = null;
		
		for (Command cmd : map.values()) {
			
			if (words[0].equalsIgnoreCase(cmd.getName())) {
				command = cmd;
			}
			
		}
		
		if (command == null) {
			return "Invalid command. Type \"help\" for a list of commands.";
		}
		
		words = Arrays.copyOfRange(words, 1, words.length);
		
		Map<String,Object> flags = new HashMap<String,Object>();
		
		outer:
		for (String w : words) {
			
			if (w.startsWith("-")) {
				
				for (Flag flag : command.getFlags().values()) {
					String name = "-" + flag.getName();
					
					if (w.startsWith(name)) {
						
						Object value = null;
						
						if (flag.hasArgument()) {
							w = w.replace(name, "");
							
							if (!w.equals("") && !w.isEmpty()) {
								value = flag.getArgType().parse(w);
							}
							
							if (value == null) {
								return "Invalid flag usage: Flag \"-" + flag.getName() + "\" requires argument " + flag.getArgType() + "";
							}
						}
						
						flags.put(flag.getName(), value);
						continue outer;
						
					}
					
				}
				
				return "Invalid flag: " + w;
			}
			
		}
		
		
		List<String> argsStr = new ArrayList<String>();
		
		for (String w : words) {
			
			if (!w.startsWith("-")) {
				
				argsStr.add(w);
				
			}
			
		}
		
		
		String msg = new String();
		
		Map<String,Object> args = new HashMap<String,Object>();
		
		for (Argument arg : command.getArgs().values()) {
			
			if (argsStr.size() == 0) {
				args.put(arg.getName(), null);
			} else {
				args.put(arg.getName(), arg.getType().parse(argsStr.get(0)));
				
				argsStr.remove(0);
			}
			
			if (args.get(arg.getName()) == null && arg.isRequired()) {
				msg += "Argument missing or invalid: " + arg.getName() + " (" + arg.getType() + ") " + arg.getDesc() + "\n";
			}
			
		}
		
		if (!msg.isEmpty()) {
			return msg;
		}
		
		try {
			return(command.run(flags, args));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "Error while executing command \"" + command.getName() + "\"";
		
		
	}
	

}
