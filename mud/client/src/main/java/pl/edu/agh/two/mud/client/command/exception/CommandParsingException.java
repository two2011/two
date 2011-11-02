package pl.edu.agh.two.mud.client.command.exception;

public class CommandParsingException extends Exception {

	private static final long serialVersionUID = 2991101730907636075L;
	private String commandName;

	public CommandParsingException(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandName() {
		return commandName;
	}

}
