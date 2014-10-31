package net.funkitech.util.server.messaging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageHandler {
	
	public String[] names();
	public Priority priority() default Priority.NORMAL;
	
}
