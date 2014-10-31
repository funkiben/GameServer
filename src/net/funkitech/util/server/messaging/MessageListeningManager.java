package net.funkitech.util.server.messaging;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class MessageListeningManager {
	
	private final List<MessageListener> listeners = new ArrayList<MessageListener>();
	
	public void registerListeners(MessageListener...arr) {
		for (MessageListener listener : arr) {
			listeners.add(listener);
		}
	}
	
	public void unregisterListeners(MessageListener listener) {
		listeners.remove(listener);
	}
	
	public synchronized void call(Message msg, MessagingSocket socket) {
		EnumMap<Priority,List<MessageHandlerMethod>> map = getHandlers(msg);
		
		for (Priority p : Priority.values()) {
			for (MessageHandlerMethod m : map.get(p)) {
				m.invoke(socket, msg.getArgs());
			}
		}
	}
	
	private EnumMap<Priority,List<MessageHandlerMethod>> getHandlers(Message msg) {
		
		EnumMap<Priority,List<MessageHandlerMethod>> map = new EnumMap<Priority,List<MessageHandlerMethod>>(Priority.class);
		
		for (Priority p : Priority.values()) {
			map.put(p, new ArrayList<MessageHandlerMethod>());
		}
		
		for (MessageListener l : listeners) {
			
			for (Method m : l.getClass().getDeclaredMethods()) {
				
				if (m.isAnnotationPresent(MessageHandler.class)) {
					
					MessageHandler a = m.getAnnotation(MessageHandler.class);
					
					if (contains(a.names(), msg.getName())) {
							
						MessageHandlerMethod method;
							
						if (Modifier.isStatic(m.getModifiers())) {
							method = new MessageHandlerMethod(a, m);
						} else {
							method = new MessageHandlerMethod(a, m, l);
						}
							
						map.get(a.priority()).add(method);
							
						
					}
					
				}
				
			}
			
		}
		
		return map;
	}
	
	private boolean contains(String[] strs, String str) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].equals(str)) {
				return true;
			}
		}
		
		return false;
	}
	
}
