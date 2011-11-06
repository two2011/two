package pl.edu.agh.two.mud.client.command.provider;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.client.command.registry.CommandRegistrationException;
import pl.edu.agh.two.mud.client.command.registry.ICommandDefinitionRegistry;
import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.converter.CommandToDefinitionConverter;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.provider.SpringCommandProvider;

// TODO [ksobon] Needs to be tested ! Tests for SpringCommandProvider needed !
public class ClientSpringCommandProvider extends SpringCommandProvider {

	private CommandToDefinitionConverter converter;

	private ICommandDefinitionRegistry commandDefinitionRegistry;

	public void setConverter(CommandToDefinitionConverter converter) {
		this.converter = converter;
	}

	public void setCommandDefinitionRegistry(
			ICommandDefinitionRegistry commandDefinitionRegistry) {
		this.commandDefinitionRegistry = commandDefinitionRegistry;
	}

	@Override
	protected void registerCommand(String id,
			Class<? extends Command> commandClass) {
		super.registerCommand(id, commandClass);

		if (commandDefinitionRegistry != null && converter != null) {
			Command command = getCommandById(id);
			ICommandDefinition definition = converter
					.convertToCommandDefinition(command);
			try {
				commandDefinitionRegistry.registerCommandDefinition(definition);
			} catch (CommandRegistrationException e) {
				Logger.getLogger(ClientSpringCommandProvider.class).error(
						String.format(
								"Cannot register definition of command: %s",
								commandClass), e);
			}
		}
	}

}
