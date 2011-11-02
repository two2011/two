package pl.edu.agh.two.mud.client.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import pl.edu.agh.two.mud.client.command.exception.InvalidCommandParametersException;
import pl.edu.agh.two.mud.client.command.exception.UnavailableCommandException;
import pl.edu.agh.two.mud.client.command.exception.UnknownCommandException;
import pl.edu.agh.two.mud.client.command.registry.ICommandDefinitionRegistry;
import pl.edu.agh.two.mud.common.command.IParsedCommand;
import pl.edu.agh.two.mud.common.command.ParsedCommand;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.definition.ICommandParameterDefinition;

public class CommandParser implements ICommandParser {

	private ICommandDefinitionRegistry commandDefinitionRegistry;

	private Set<Object> availableCommands = new HashSet<Object>();

	public CommandParser(ICommandDefinitionRegistry commandDefinitionRegistry) {
		this.commandDefinitionRegistry = commandDefinitionRegistry;

		for (ICommandDefinition definition : commandDefinitionRegistry
				.getCommandDefinitions()) {
			availableCommands.add(definition.getId());
		}
	}

	@Override
	public void setAvailableCommands(Set<Object> ids) {
		availableCommands = ids;
	}

	@Override
	public IParsedCommand parse(String command) throws UnknownCommandException,
			UnavailableCommandException, InvalidCommandParametersException {
		if (command == null) {
			throw new IllegalArgumentException("Cannot parse null command");
		}

		// Splitting command
		List<String> splittedCommand = new ArrayList<String>(
				Arrays.asList(command.trim().split(" ")));
		String commandName = splittedCommand.get(0);

		// Obtain command definition
		ICommandDefinition definition = commandDefinitionRegistry
				.getCommandDefinitionByName(commandName);
		if (definition == null) {
			throw new UnknownCommandException(commandName);
		}

		// Check whether this command can be used by user
		if (!availableCommands.contains(definition.getId())) {
			throw new UnavailableCommandException(commandName, definition);
		}

		List<ICommandParameterDefinition> parameterDefinitions = definition
				.getParameters();

		if (splittedCommand.size() - 1 < parameterDefinitions.size()
				|| (splittedCommand.size() - 1 > parameterDefinitions.size() && !definition
						.isTextParam())) {
			// Not enough or too many parameters parameters.
			throw new InvalidCommandParametersException(commandName,
					definition, null);
		} else if (splittedCommand.size() - 1 > parameterDefinitions.size()
				&& definition.isTextParam()) {
			// Additional parameters should be merged to last required
			// parameters

			StringBuilder lastParamater = new StringBuilder();
			for (int i = parameterDefinitions.size() - 2; i <= splittedCommand
					.size(); i++) {
				lastParamater.append(splittedCommand
						.remove(parameterDefinitions.size()));
				lastParamater.append(" ");
			}
			if (lastParamater.length() > 0) {
				lastParamater.deleteCharAt(lastParamater.length() - 1);
				splittedCommand.add(lastParamater.toString());
			}
		}

		Map<String, String> valuesMap = new HashMap<String, String>();

		// Validate parameters
		int i = 1;
		for (ICommandParameterDefinition parameterDefinition : parameterDefinitions) {
			String value = splittedCommand.get(i++);
			if (parameterDefinition.getRegExp() != null) {
				Pattern pattern = Pattern.compile(parameterDefinition
						.getRegExp());
				if (!pattern.matcher(value).find()) {
					throw new InvalidCommandParametersException(commandName,
							definition, parameterDefinition.getName());
				}
			}

			valuesMap.put(parameterDefinition.getName(), value);
		}

		return new ParsedCommand(definition.getId(), valuesMap);
	}
}
