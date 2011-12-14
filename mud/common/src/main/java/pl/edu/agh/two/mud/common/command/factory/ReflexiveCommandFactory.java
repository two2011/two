package pl.edu.agh.two.mud.common.command.factory;

import java.lang.reflect.*;
import java.util.*;

import org.apache.log4j.*;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.common.command.annotation.*;
import pl.edu.agh.two.mud.common.command.provider.*;
import pl.edu.agh.two.mud.common.command.type.*;

public class ReflexiveCommandFactory implements CommandFactory {

	private CommandProvider commandProvider;
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Command create(IParsedCommand parsedCommand) {
		Command command = commandProvider.getCommandById(parsedCommand.getCommandId());
		injectCommandParameters(command, parsedCommand.getValuesMap());
		return command;
	}

	private void injectCommandParameters(Command command, Map<String, String> parametersValuesByName) {
		List<Field> parametersFields = getParametersFields(command);
		for (Field parameterField : parametersFields) {
			String fieldName = parameterField.getName();
			assertParameterPresentInTheMap(fieldName, parametersValuesByName);
			injectFieldValue(command, parameterField, parametersValuesByName.get(fieldName));
		}

	}

	private void assertParameterPresentInTheMap(String fieldName, Map<String, String> parametersValuesByName) {
		if (!parametersValuesByName.containsKey(fieldName)) {
			logger.error(String.format("Required command parameter \"%s\" not found in the parameters map.", fieldName));
			throw new NoSuchElementException();
		}
	}

	private void injectFieldValue(Command command, Field parameterField, String parameterValue) {
		Object parsedParameter = parseParameter(parameterField.getType(), parameterValue);
		try {
			parameterField.set(command, parsedParameter);
		} catch (Exception e) {
			logger.error("Parameter type does not match command field type");
			throw new IllegalArgumentException();
		}
	}

	private Object parseParameter(Class<?> type, String parameterValue) {
		if (String.class.equals(type)) {
			return parameterValue;
		} else if (Text.class.equals(type)) {
			return new Text(parameterValue);
		} else if (Integer.class.equals(type) || int.class.equals(type)) {
			return Integer.parseInt(parameterValue);
		} else if (Double.class.equals(type) || double.class.equals(type)) {
			return Double.parseDouble(parameterValue);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private List<Field> getParametersFields(Command command) {
		Field[] allCommandFields = command.getClass().getDeclaredFields();
		List<Field> commandParametersFields = getParametersFields(allCommandFields);
		return commandParametersFields;
	}

	private List<Field> getParametersFields(Field[] commandfields) {
		List<Field> commandParametersFields = new ArrayList<Field>();
		for (Field field : commandfields) {
			if (field.isAnnotationPresent(OrderedParam.class)) {
				field.setAccessible(true);
				commandParametersFields.add(field);
			}
		}
		return commandParametersFields;
	}

	public void setCommandProvider(CommandProvider commandProvider) {
		this.commandProvider = commandProvider;
	}
	
	protected CommandProvider getCommandProvider() {
		return commandProvider;
	}
	
}
