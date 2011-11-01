package pl.edu.agh.two.mud.client.command.exception;

import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

public class UnavailableCommandException extends CommandParsingException {

	private static final long serialVersionUID = 6674674465944388126L;

	private ICommandDefinition commandDefinition;

	public UnavailableCommandException(String commandName,
			ICommandDefinition commandDefinition) {
		super(commandName);
		this.commandDefinition = commandDefinition;
	}

	public ICommandDefinition getCommandDefinition() {
		return commandDefinition;
	}
}
