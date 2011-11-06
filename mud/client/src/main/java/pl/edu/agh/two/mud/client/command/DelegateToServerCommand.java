package pl.edu.agh.two.mud.client.command;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.IParsedCommand;

public class DelegateToServerCommand extends Command {

	private IParsedCommand parsedCommand;

	public DelegateToServerCommand(IParsedCommand parsedCommand) {
		this.parsedCommand = parsedCommand;
	}

	public IParsedCommand getParsedCommand() {
		return parsedCommand;
	}

}
