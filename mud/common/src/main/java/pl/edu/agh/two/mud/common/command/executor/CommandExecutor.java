package pl.edu.agh.two.mud.common.command.executor;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;

public interface CommandExecutor<C extends Command> {

	void execute(C command) throws CommandExecutingException;

}
