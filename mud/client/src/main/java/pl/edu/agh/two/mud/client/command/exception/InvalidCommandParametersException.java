package pl.edu.agh.two.mud.client.command.exception;

import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

public class InvalidCommandParametersException extends CommandParsingException {

	private static final long serialVersionUID = 2837353669485876542L;

	private ICommandDefinition commandDefinition;

	private String parameterName;

	public InvalidCommandParametersException(String commandName,
			ICommandDefinition commandDefinition, String parameterName) {
		super(commandName);
		this.commandDefinition = commandDefinition;
		this.parameterName = parameterName;
	}

	public ICommandDefinition getCommandDefinition() {
		return commandDefinition;
	}

	public String getParameterName() {
		return parameterName;
	}
}
