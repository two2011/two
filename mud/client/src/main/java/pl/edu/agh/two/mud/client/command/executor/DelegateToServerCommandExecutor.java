package pl.edu.agh.two.mud.client.command.executor;

import java.io.IOException;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.client.Connection;
import pl.edu.agh.two.mud.client.command.DelegateToServerCommand;
import pl.edu.agh.two.mud.client.command.WriteErrorOnConsoleCommand;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;

public class DelegateToServerCommandExecutor implements
		CommandExecutor<DelegateToServerCommand> {

	private static Logger log = Logger
			.getLogger(DelegateToServerCommandExecutor.class);

	private Connection connection;

	private Dispatcher dispatcher;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void execute(DelegateToServerCommand command) {
		try {
			connection.send(command.getParsedCommand());
		} catch (IOException e) {
			log.error("Error during delegating command to server", e);
			dispatcher
					.dispatch(new WriteErrorOnConsoleCommand(
							"Error occured during sending command to server. Please see the log files"));
		}
	}
}
