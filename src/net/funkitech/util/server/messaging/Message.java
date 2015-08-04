package net.funkitech.util.server.messaging;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = -1583352683773004499L;
	
	private final String name;
	private final Object[] args;
	
	public Message(String name, Object...args) {
		this.name = name;
		this.args = args;
	}
	
	public String getName() {
		return name;
	}
	
	public Object[] getArgs() {
		return args;
	}
}
