package server.command;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public abstract class Command {
	
	private final String name;
	private final String desc;
	private final Map<String,Flag> flags = new HashMap<String,Flag>();
	private final Map<String,Argument> args = new LinkedHashMap<String,Argument>();
	protected CommandManager manager;
	
	public Command(String name, String desc) {
		this.name = name;
		this.desc = desc;
		
	}
	
	public String getName() {
		return name;
	}
	
	public void addFlag(String name, String desc) {
		flags.put(name, new Flag(name, desc));
	}
	
	public void addFlag(String name, String desc, String argName, ArgumentType argType) {
		flags.put(name, new Flag(name, desc, argName, argType));
	}
	
	public void addArg(String name, ArgumentType type, String desc) {
		args.put(name, new Argument(name, type, desc));
	}
	
	public void addArg(String name, ArgumentType type, String desc, boolean required) {
		args.put(name, new Argument(name, type, desc, required));
	}
	
	public Map<String,Argument> getArgs() {
		return args;
	}
	
	public Map<String,Flag> getFlags() {
		return flags;
	}
	
	public String getUsage() {
		String usage = "\"" + name;
		
		for (Argument arg : args.values()) {
			String ob = arg.isRequired() ? "<" : "[";
			String cb = arg.isRequired() ? ">" : "]";
			usage += " " + ob + arg.getName() + " (" + arg.getType() + ")" + cb;
		}
		
		usage += "\" " + desc;
		for (Flag flag : flags.values()) {
			
			usage += "\n\t -" + flag.getName() + "";
			
			if (flag.hasArgument()) {
				usage += "<" + flag.getArgName() + " (" + flag.getArgType() + ")>";
			}
			
			usage += " " + flag.getDesc();
			
		}
		
		return usage;
	}
	
	public abstract String run(Map<String,Object> flags, Map<String,Object> args);

}
