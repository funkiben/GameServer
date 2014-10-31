package server.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventHandlerMethod {
	
	private final Listener listener;
	private final Method method;
	private final EventHandler annotation;
	
	public EventHandlerMethod(Listener listener, Method method, EventHandler annotation) {
		this.listener = listener;
		this.method = method;
		this.annotation = annotation;
		
	}
	
	public Method getMethod() {
		return method;
	}
	
	public EventPriority getPriority() {
		return annotation.priority();
	}
	
	public boolean ignoreCanclled() {
		return annotation.ignoreCancelled();
	}
	
	public void invoke(Event event) {
		try {
			method.invoke(listener, event);
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			
		}
	}
	
	
	
}
