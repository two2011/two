package pl.edu.agh.two.mud.client.command.factory;

import pl.edu.agh.two.mud.client.command.DelegateToServerCommand;
import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.IParsedCommand;
import pl.edu.agh.two.mud.common.command.factory.ReflexiveCommandFactory;

public class ClientCommandFactory extends ReflexiveCommandFactory {

	@Override
	public Command create(IParsedCommand parsedCommand) {
		// Check whether command is local (defined for client) or external
		// (obtained from server)
		if (commandProvider.isCommandAvailable(parsedCommand.getCommandId())) {
			return super.create(parsedCommand);
		}

		return new DelegateToServerCommand(parsedCommand);
	}
}
