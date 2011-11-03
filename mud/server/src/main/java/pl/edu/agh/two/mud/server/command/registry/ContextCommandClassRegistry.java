package pl.edu.agh.two.mud.server.command.registry;

import java.util.*;

import org.apache.log4j.*;
import org.springframework.beans.*;
import org.springframework.context.*;

import pl.edu.agh.two.mud.server.command.*;

public class ContextCommandClassRegistry implements CommandClassRegistry, ApplicationContextAware {

	private Map<String, Class<? extends Command>> commandsById = new HashMap<String, Class<? extends Command>>();
	private ApplicationContext applicationContext;
	private Logger logger = Logger.getLogger(getClass());

	public void buildRegistry() {
		Collection<Command> commands = applicationContext.getBeansOfType(Command.class).values();
		for (Command command : commands) {
			registerCommandClass(command.getId(), command.getClass());
		}
	}

	@Override
	public void registerCommandClass(String commandId, Class<? extends Command> commandClass) {
		Class<? extends Command> overwrittenCommandClass = commandsById.put(commandId, commandClass);
		logIfCommandClassOverwritten(overwrittenCommandClass);
	}


	@Override
	public Class<? extends Command> getCommandClass(String commandId) {
		return commandsById.get(commandId);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	private void logIfCommandClassOverwritten(Class<? extends Command> overwrittenCommandClass) {
		if(overwrittenCommandClass != null) {
			logger.warn(String.format("Following command class has been overwritten in registry: %s", overwrittenCommandClass.toString()));
		}
	}
}
