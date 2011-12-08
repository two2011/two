package pl.edu.agh.two.mud.server.command.executor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.converter.UICommandToDefinitionConverter;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.command.provider.CommandProvider;
import pl.edu.agh.two.mud.common.message.AvailableCommandsMessage;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.SendAvailableCommands;

public class SendAvailableCommandExecutor implements CommandExecutor<SendAvailableCommands> {

	private IServiceRegistry serviceRegistry;

	private UICommandToDefinitionConverter converter;

	private CommandProvider commandProvider;

	@Override
	public void execute(SendAvailableCommands command) throws CommandExecutingException {

		Service service = serviceRegistry.getService(command.getPlayer());
		Collection<ICommandDefinition> commandDefinitions = new ArrayList<ICommandDefinition>();

		for (Class<? extends UICommand> uiCommandClass : command.getUiCommands()) {
			commandDefinitions.add(converter.convertToCommandDefinition((UICommand) commandProvider
					.getCommandById(uiCommandClass.getName())));
		}

		try {
			service.writeObject(new AvailableCommandsMessage(commandDefinitions));
		} catch (IOException e) {
			throw new FatalException(e);
		}

	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setConverter(UICommandToDefinitionConverter converter) {
		this.converter = converter;
	}

	public void setCommandProvider(CommandProvider commandProvider) {
		this.commandProvider = commandProvider;
	}

}
