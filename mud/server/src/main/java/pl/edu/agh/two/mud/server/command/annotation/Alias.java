package pl.edu.agh.two.mud.server.command.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Alias {
	String[] value();
}
