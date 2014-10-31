package server.command;


public class Argument {
	
	private final String name;
	private final ArgumentType type;
	private final String desc;
	private final boolean required;
	
	public Argument(String name, ArgumentType type, String desc) {
		this.name = name;
		this.type = type;
		this.desc = desc;
		required = true;
	}
	
	public Argument(String name, ArgumentType type, String desc, boolean required) {
		this.name = name;
		this.type = type;
		this.desc = desc;
		this.required = required;
	}
	
	public String getName() {
		return name;
	}
	
	public ArgumentType getType() {
		return type;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	
	
}
