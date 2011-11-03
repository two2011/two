package pl.edu.agh.two.mud.client.command.registry;

import java.util.Collection;

import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

public interface ICommandDefinitionRegistry {

	ICommandDefinition getCommandDefinitionById(String id);

	ICommandDefinition getCommandDefinitionByName(String name);

	Collection<ICommandDefinition> getCommandDefinitions();

	void registerCommandDefinition(ICommandDefinition definition)
			throws CommandRegistrationException;

}
