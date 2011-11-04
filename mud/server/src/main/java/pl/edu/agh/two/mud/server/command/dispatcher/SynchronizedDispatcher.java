package pl.edu.agh.two.mud.server.command.dispatcher;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.server.command.*;
import pl.edu.agh.two.mud.server.command.executor.*;
import pl.edu.agh.two.mud.server.command.factory.*;
import pl.edu.agh.two.mud.server.command.provider.*;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SynchronizedDispatcher implements Dispatcher {
	
	private CommandFactory commandFactory;
	private CommandExecutorProvider commandExecutorProvider;

	@Override
	public synchronized void dispatch(IParsedCommand parsedCommand) {
		Command command = commandFactory.create(parsedCommand);
		CommandExecutor commandExecutor = commandExecutorProvider.getExecutorForCommand(command);
		commandExecutor.execute(command);
	}
	
	public void setCommandExecutorProvider(CommandExecutorProvider commandExecutorProvider) {
		this.commandExecutorProvider = commandExecutorProvider;
	}
	
	public void setCommandFactory(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}
	

}
