package pl.edu.agh.two.mud.server.command.executor;

import java.io.IOException;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.message.TextMessage;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;

public class SendMessageToUserCommandExecutor implements CommandExecutor<SendMessageToUserCommand> {

	private static Logger logger = Logger.getLogger(SendMessageToUserCommandExecutor.class);

	private IServiceRegistry serviceRegistry;

	@Override
	public void execute(SendMessageToUserCommand command) throws CommandExecutingException {

		Service service = null;

		if (command.getPlayer() != null) {
			service = serviceRegistry.getService(command.getPlayer());
		} else {
			service = serviceRegistry.getCurrentService();
		}

		try {
			service.writeObject(new TextMessage(command.getMessage(), command.getType()));
		} catch (IOException e) {
			throw new FatalException(e, logger);
		}

	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
}
