package server.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class EventManager {
	
	private final List<Listener> listeners = new ArrayList<Listener>();
	
	public void registerListener(Listener...listeners) {
		for (Listener listener : listeners) {
			this.listeners.add(listener);
		}
	}
	
	public void unregisterListener(Listener listener) {
		listeners.remove(listener);
	}
	
	public synchronized Event callEvent(Event event) {
		EnumMap<EventPriority,List<EventHandlerMethod>> handlers = getEventHandlers(event.getClass());
		
		for (EventPriority p : EventPriority.values()) {
			
			for (EventHandlerMethod m : handlers.get(p)) {
				
				if (m.ignoreCanclled() ? !event.isCancelled() : true) {
				
					m.invoke(event);
					
				}
				
			}
			
		}
		
		return event;
	}
	
	private EnumMap<EventPriority,List<EventHandlerMethod>> getEventHandlers(Class<? extends Event> e) {
		
		EnumMap<EventPriority,List<EventHandlerMethod>> map = new EnumMap<EventPriority,List<EventHandlerMethod>>(EventPriority.class);
		
		for (EventPriority p : EventPriority.values()) {
			map.put(p, new ArrayList<EventHandlerMethod>());
		}
		
		for (Listener l : listeners) {
			for (Method m : l.getClass().getMethods()) {
				
				if (m.isAnnotationPresent(EventHandler.class)) {
					
					EventHandler a = m.getAnnotation(EventHandler.class);
					
					if (m.getParameterTypes().length > 0) {
						
						if (e == null || m.getParameterTypes()[0].isAssignableFrom(e)) {
							
							map.get(a.priority()).add(new EventHandlerMethod(l, m, a));
							
						}
						
					}
					
				}
				
			}
		}
		
		
		return map;
	}

}
