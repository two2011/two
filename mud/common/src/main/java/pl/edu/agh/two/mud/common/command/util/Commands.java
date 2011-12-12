package pl.edu.agh.two.mud.common.command.util;

import java.util.Collection;

import pl.edu.agh.two.mud.common.command.Command;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

public class Commands {

	public static Collection<Class<? extends Command>> toClasses(Collection<Command> commands) {

		return Collections2.transform(commands, new Function<Command, Class<? extends Command>>() {
			@Override
			public Class<? extends Command> apply(Command input) {
				return input.getClass();
			}
		});
	}

	public static String getId(Class<? extends Command> commandClass) {
		return commandClass.getName();
	}

}
