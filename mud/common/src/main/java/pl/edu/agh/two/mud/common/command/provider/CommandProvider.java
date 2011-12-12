package pl.edu.agh.two.mud.common.command.provider;

import java.util.Collection;
import java.util.List;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.UICommand;

public interface CommandProvider {

	Command getCommandById(String commandId);

	Command getCommand(Class<? extends Command> command);

	List<Command> getCommands();

	List<UICommand> getUICommands();

	List<UICommand> getUICommandsWithout(Class<? extends UICommand>... classesToExclude);

	List<UICommand> getUICommands(Class<? extends UICommand>... classes);

	List<UICommand> getUICommandsWithout(Collection<Class<? extends UICommand>> classesToExclude);

	List<UICommand> getUICommands(Collection<Class<? extends UICommand>> classes);

	boolean isCommandAvailable(String commandId);

	boolean isCommandAvailable(Class<? extends Command> command);

}
