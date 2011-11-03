package pl.edu.agh.two.mud.server.command.registry;

import pl.edu.agh.two.mud.server.command.*;

public interface CommandClassRegistry {
	
	Class<? extends Command> getCommandClass(String commandId);
	
	void registerCommandClass(String commandId, Class<? extends Command> commandClass);

}
