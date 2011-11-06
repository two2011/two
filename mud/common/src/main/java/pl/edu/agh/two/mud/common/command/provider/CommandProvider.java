package pl.edu.agh.two.mud.common.command.provider;

import java.util.List;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.UICommand;

public interface CommandProvider {

	Command getCommandById(String commandId);

	List<Command> getAvailableCommands();
	
	List<UICommand> getUICommands();

	boolean isCommandAvailable(String commandId);

}
