package net.funkitech.util.server.messaging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MessageHandlerMethod {
	
	private static final Map<Class<?>,Class<?>> primitives = new HashMap<Class<?>,Class<?>>();
	
	static {
		primitives.put(Character.class, Character.TYPE);
		primitives.put(Boolean.class, Boolean.TYPE);
		primitives.put(Double.class, Double.TYPE);
		primitives.put(Float.class, Float.TYPE);
		primitives.put(Long.class, Long.TYPE);
		primitives.put(Integer.class, Integer.TYPE);
		primitives.put(Short.class, Short.TYPE);
		primitives.put(Byte.class, Byte.TYPE);
	}
	
	private final MessageHandler meta;
	private final Method method;
	private final Object obj;
	
	public MessageHandlerMethod(MessageHandler meta, Method method, Object obj) {
		this.meta = meta;
		this.method = method;
		this.obj = obj;
	}
	
	public MessageHandlerMethod(MessageHandler meta, Method method) {
		this.meta = meta;
		this.method = method;
		this.obj = null;
	}
	
	public MessageHandler getMeta() {
		return meta;
	}
	
	public boolean invoke(MessagingSocket socket, Object...args) {
		try {
			
			if (acceptsSocketArg()) {
				args = insertAtFirstIndex(args, socket);
			}
			
			if (argTypesMatch(args)) {
				method.invoke(obj, args);
				return true;
			}
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private boolean acceptsSocketArg() {
		if (method.getParameterTypes().length == 0) {
			return false;
		}
		
		return MessagingSocket.class.isAssignableFrom(method.getParameterTypes()[0]);
	}
	
	private boolean argTypesMatch(Object[] args) {
		
		Class<?>[] argTypes = new Class<?>[args.length];
		
		for (int i = 0; i < args.length; i++) {
			argTypes[i] = args[i].getClass();
		}
		
		Class<?>[] methodArgTypes = method.getParameterTypes();
		
		if (methodArgTypes.length != argTypes.length) {
			return false;
		}
		
		for (int i = 0; i < methodArgTypes.length; i++) {
			if (
					!methodArgTypes[i].isAssignableFrom(argTypes[i]) || 
					primitives.containsKey(argTypes[i]) ? primitives.get(argTypes[i]) == methodArgTypes[i] : false
					
				) {
				return false;
			}
		}
		
		return true;
		
	}
	
	private Object[] insertAtFirstIndex(Object[] arr, Object e) {
		Object[] arr2 = new Object[arr.length + 1];
		
		for (int i = 1; i < arr2.length; i++) {
			arr2[i] = arr[i - 1];
		}
		
		arr2[0] = e;
		
		return arr2;
	}

}
