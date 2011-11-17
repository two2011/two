package pl.edu.agh.two.mud.common.command.dispatcher;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.IParsedCommand;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.command.factory.CommandFactory;
import pl.edu.agh.two.mud.common.command.provider.CommandExecutorProvider;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SynchronizedDispatcher implements Dispatcher {

	private CommandFactory commandFactory;
	private CommandExecutorProvider commandExecutorProvider;

	@Override
	public synchronized void dispatch(IParsedCommand parsedCommand) {
		dispatch(commandFactory.create(parsedCommand));
	}

	@Override
	public void dispatch(Command command) {
		CommandExecutor commandExecutor = commandExecutorProvider
				.getExecutorForCommand(command);
		try {
			commandExecutor.execute(command);
		} catch (CommandExecutingException e) {
			try {
				e.handleException();
			} catch (Throwable t) {
				System.out.println("Something goes really badly !");
				t.printStackTrace();
			}
		}
	}

	public void setCommandExecutorProvider(
			CommandExecutorProvider commandExecutorProvider) {
		this.commandExecutorProvider = commandExecutorProvider;
	}

	public void setCommandFactory(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}

}
