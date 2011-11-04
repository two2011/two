package pl.edu.agh.two.mud.server.command.provider;

import pl.edu.agh.two.mud.server.command.*;
import pl.edu.agh.two.mud.server.command.executor.*;

public interface CommandExecutorProvider {
	
	CommandExecutor<? extends Command> getExecutorForCommand(Command command);
	
}
