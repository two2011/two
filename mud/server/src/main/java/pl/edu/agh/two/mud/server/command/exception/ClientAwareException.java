package pl.edu.agh.two.mud.server.command.exception;

import org.springframework.context.ApplicationContext;

import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.server.command.SendErrorToUserCommand;

public class ClientAwareException extends CommandExecutingException {

	private static final long serialVersionUID = 3522735834195696793L;

	private String errorMessage;

	public ClientAwareException(String errorMessage) {
		this(null, errorMessage);
	}

	public ClientAwareException(Throwable cause, String errorMessage) {
		super(cause);
		this.errorMessage = errorMessage;
	}

	@Override
	public void handleException() {
		ApplicationContext context = pl.edu.agh.two.mud.server.configuration.ApplicationContext
				.getApplicationContext();
		Dispatcher dispatcher = (Dispatcher) context.getBean(Dispatcher.class);

		dispatcher.dispatch(new SendErrorToUserCommand(errorMessage));
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
