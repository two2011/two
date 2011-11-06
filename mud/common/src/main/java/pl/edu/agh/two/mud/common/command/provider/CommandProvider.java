package pl.edu.agh.two.mud.common.command.provider;

import java.util.List;

import pl.edu.agh.two.mud.common.command.Command;

public interface CommandProvider {

	Command getCommandById(String commandId);

	List<Command> getAvailableCommands();

	boolean isCommandAvailable(String commandId);

}
