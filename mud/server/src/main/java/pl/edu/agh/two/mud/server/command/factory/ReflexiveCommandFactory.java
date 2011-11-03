package pl.edu.agh.two.mud.server.command.factory;

import java.lang.reflect.*;
import java.util.*;

import org.apache.log4j.*;
import org.springframework.beans.*;
import org.springframework.context.*;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.server.command.*;
import pl.edu.agh.two.mud.server.command.annotation.*;
import pl.edu.agh.two.mud.server.command.registry.*;

public class ReflexiveCommandFactory implements CommandFactory, ApplicationContextAware {

	private CommandClassRegistry commandClassRegistry;
	private ApplicationContext applicationContext;
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Command create(IParsedCommand parsedCommand) {
		Command command = createCommandInstance(parsedCommand.getCommandId());
		injectCommandParameters(command, parsedCommand.getValuesMap());
		return command;
	}

	private Command createCommandInstance(String commandId) {
		Class<? extends Command> commandClass = commandClassRegistry.getCommandClass(commandId);
		Command command = applicationContext.getBean(commandClass);
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
		//TODO:
	}

	private List<Field> getParametersFields(Command command) {
		Field[] allCommandFields = command.getClass().getFields();
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

	public void setCommandClassRegistry(CommandClassRegistry commandClassRegistry) {
		this.commandClassRegistry = commandClassRegistry;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}