package pl.edu.agh.two.mud.common.command.provider;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.common.command.executor.*;

public interface CommandExecutorProvider {
	
	CommandExecutor<? extends Command> getExecutorForCommand(Command command);
	
}
