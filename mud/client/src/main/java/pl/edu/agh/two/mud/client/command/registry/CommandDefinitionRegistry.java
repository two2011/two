package pl.edu.agh.two.mud.client.command.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

/**
 * @author ksobon
 * 
 */
public class CommandDefinitionRegistry implements ICommandDefinitionRegistry,
		ApplicationContextAware {

	private Map<String, ICommandDefinition> mapById = new HashMap<String, ICommandDefinition>();
	private Map<String, ICommandDefinition> mapByName = new HashMap<String, ICommandDefinition>();

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {

		for (ICommandDefinition definition : context.getBeansOfType(
				ICommandDefinition.class).values()) {
			try {
				registerCommandDefinition(definition);
			} catch (CommandRegistrationException e) {
				throw new RuntimeException(
						"Error with registration of built-in command definition",
						e);
			}
		}

	}

	@Override
	public ICommandDefinition getCommandDefinitionById(String id) {
		return mapById.get(id);
	}

	@Override
	public ICommandDefinition getCommandDefinitionByName(String name) {
		return mapByName.get(name);
	}

	@Override
	public Collection<ICommandDefinition> getCommandDefinitions() {
		return Collections.unmodifiableCollection(mapById.values());
	}

	@Override
	public void registerCommandDefinition(ICommandDefinition definition)
			throws CommandRegistrationException {

		if (definition == null) {
			return;
		}

		if (mapById.containsKey(definition.getId())) {
			throw new CommandRegistrationException(String.format(
					"Definition with id '%s' is already registered",
					definition.getId()));
		}

		for (String name : definition.getNames()) {
			if (mapByName.containsKey(name)) {
				throw new CommandRegistrationException(
						String.format(
								"Definition with name '%s' is already registered",
								name));
			}
		}

		mapById.put(definition.getId(), definition);
		for (String name : definition.getNames()) {
			mapByName.put(name, definition);
		}
	}

	@Override
	public void clearRegistry() {
		mapById.clear();
		mapByName.clear();
	}
}
