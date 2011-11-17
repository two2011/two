package pl.edu.agh.two.mud.common.command.exception;

public abstract class CommandExecutingException extends Exception {

	private static final long serialVersionUID = -22769972929442298L;

	public CommandExecutingException(Throwable cause) {
		super(cause);
	}

	public abstract void handleException();

}
