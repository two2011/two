package pl.edu.agh.two.mud.client.command.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import pl.edu.agh.two.mud.client.command.exception.InvalidCommandParametersException;
import pl.edu.agh.two.mud.client.command.exception.UnknownCommandException;
import pl.edu.agh.two.mud.client.command.registry.ICommandDefinitionRegistry;
import pl.edu.agh.two.mud.common.command.IParsedCommand;
import pl.edu.agh.two.mud.common.command.ParsedCommand;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.definition.ICommandParameterDefinition;

public class CommandParser implements ICommandParser {

	private ICommandDefinitionRegistry commandDefinitionRegistry;

	public CommandParser(ICommandDefinitionRegistry commandDefinitionRegistry) {
		this.commandDefinitionRegistry = commandDefinitionRegistry;
	}

	@Override
	public IParsedCommand parse(String command) throws UnknownCommandException, InvalidCommandParametersException {
		if (command == null) {
			throw new IllegalArgumentException("Cannot parse null command");
		}

		// Splitting command
		List<String> splittedCommand = new ArrayList<String>(Arrays.asList(command.trim().split(" ")));
		String commandName = splittedCommand.get(0);

		// Obtain command definition
		ICommandDefinition definition = commandDefinitionRegistry.getCommandDefinitionByName(commandName);
		if (definition == null) {
			throw new UnknownCommandException(commandName);
		}

		List<ICommandParameterDefinition> parameterDefinitions = definition.getParameters();

		if (splittedCommand.size() - 1 < parameterDefinitions.size()
				|| (splittedCommand.size() - 1 > parameterDefinitions.size() && !definition.isTextParam())) {
			// Not enough or too many parameters parameters.
			throw new InvalidCommandParametersException(commandName, definition, null);
		} else if (splittedCommand.size() - 1 > parameterDefinitions.size()) {
			// Additional parameters should be merged to last required
			// parameters

			StringBuilder lastParamater = new StringBuilder();
			int howMany = splittedCommand.size() - 1;
			for (int i = parameterDefinitions.size() - 1; i < howMany; i++) {
				lastParamater.append(splittedCommand.remove(parameterDefinitions.size()));
				lastParamater.append(" ");
			}
			lastParamater.deleteCharAt(lastParamater.length() - 1);
			splittedCommand.add(lastParamater.toString());
		}

		Map<String, String> valuesMap = new HashMap<String, String>();

		// Validate parameters
		int i = 1;
		for (ICommandParameterDefinition parameterDefinition : parameterDefinitions) {
			String value = splittedCommand.get(i++);
			if (parameterDefinition.getRegExp() != null) {
				Pattern pattern = Pattern.compile(parameterDefinition.getRegExp());
				if (!pattern.matcher(value).find()) {
					throw new InvalidCommandParametersException(commandName, definition, parameterDefinition.getName());
				}
			}

			valuesMap.put(parameterDefinition.getName(), value);
		}

		return new ParsedCommand(definition.getId(), valuesMap);
	}
}
