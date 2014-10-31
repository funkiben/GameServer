package server.command;


public class Flag {
	
	private final String name;
	private final String desc;
	private final String argName;
	private final ArgumentType argType;
	
	public Flag(String name, String desc) {
		this.name = name;
		this.desc = desc;
		argName = null;
		argType = null;
	}
	
	public Flag(String name, String desc, String argName, ArgumentType argType) {
		this.name = name;
		this.desc = desc;
		this.argName = argName;
		this.argType = argType;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public boolean hasArgument() {
		return argName != null && argType != null;
	}
	
	public String getArgName() {
		return argName;
	}
	
	public ArgumentType getArgType() {
		return argType;
	}
	

}
