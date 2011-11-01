package pl.edu.agh.two.mud.client.command.exception;


public class UnknownCommandException extends CommandParsingException {

	private static final long serialVersionUID = 1347701852915169164L;

	public UnknownCommandException(String commandName) {
		super(commandName);
	}

}
