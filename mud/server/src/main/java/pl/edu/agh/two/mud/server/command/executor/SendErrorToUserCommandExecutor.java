package pl.edu.agh.two.mud.server.command.executor;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.SendErrorToUserCommand;

public class SendErrorToUserCommandExecutor implements
		CommandExecutor<SendErrorToUserCommand> {

	private static Logger logger = Logger
			.getLogger(SendErrorToUserCommandExecutor.class);

	private Service service;

	@Override
	public void execute(SendErrorToUserCommand command)
			throws CommandExecutingException {

		try {
			service.writeObject(command.getErrorMessage());
		} catch (Throwable t) {
			throw new FatalException(t, logger);
		}

	}

	public void setService(Thread thread) {
		if (thread instanceof Service) {
			service = (Service) thread;
		}
	}
}
