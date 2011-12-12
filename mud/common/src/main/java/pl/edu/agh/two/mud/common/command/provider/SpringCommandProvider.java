package pl.edu.agh.two.mud.common.command.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.util.Commands;

public class SpringCommandProvider implements CommandProvider {

	private Map<String, Class<? extends Command>> commandClassesById = new HashMap<String, Class<? extends Command>>();

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public Command getCommandById(String commandId) {
		return getCommand(commandClassesById.get(commandId));
	}

	@Override
	public Command getCommand(Class<? extends Command> command) {
		return beanFactory.getBean(command);
	}

	@Override
	public List<Command> getCommands() {
		List<Command> availableCommands = new LinkedList<Command>();
		for (Class<? extends Command> commandClass : commandClassesById.values()) {
			availableCommands.add(getCommand(commandClass));
		}

		return availableCommands;
	}

	@Override
	public List<UICommand> getUICommands() {
		List<UICommand> uiCommands = new ArrayList<UICommand>();

		for (Command command : getCommands()) {
			if (command instanceof UICommand) {
				uiCommands.add((UICommand) command);
			}
		}

		return uiCommands;
	}

	@Override
	public List<UICommand> getUICommandsWithout(Class<? extends UICommand>... classesToExclude) {
		return getUICommandsWithout(Arrays.asList(classesToExclude));
	}

	@Override
	public List<UICommand> getUICommands(Class<? extends UICommand>... classes) {
		return getUICommands(Arrays.asList(classes));
	}

	@Override
	public List<UICommand> getUICommandsWithout(Collection<Class<? extends UICommand>> classesToExclude) {
		Set<Class<? extends UICommand>> exclude = new HashSet<Class<? extends UICommand>>(classesToExclude);

		List<UICommand> uiCommands = new ArrayList<UICommand>();
		for (UICommand uiCommand : getUICommands()) {
			if (exclude.contains(uiCommand.getClass())) {
				continue;
			}

			uiCommands.add(uiCommand);
		}

		return uiCommands;
	}

	@Override
	public List<UICommand> getUICommands(Collection<Class<? extends UICommand>> classes) {
		Set<Class<? extends UICommand>> classesToInclude = new HashSet<Class<? extends UICommand>>(classes);

		List<UICommand> uiCommands = new ArrayList<UICommand>();
		for (UICommand uiCommand : getUICommands()) {
			if (classesToInclude.contains(uiCommand.getClass())) {
				uiCommands.add(uiCommand);
			}
		}

		return uiCommands;
	}

	@Override
	public boolean isCommandAvailable(String commandId) {
		return commandClassesById.containsKey(commandId);
	}

	@Override
	public boolean isCommandAvailable(Class<? extends Command> command) {
		return isCommandAvailable(Commands.getId(command));
	}

	protected void registerCommand(String id, Class<? extends Command> commandClass) {
		commandClassesById.put(id, commandClass);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void buildProvider() {
		String[] commandBeanNames = beanFactory.getBeanNamesForType(Command.class);
		for (String commandBeanName : commandBeanNames) {
			BeanDefinition commandBeanDefinition = beanFactory.getBeanDefinition(commandBeanName);
			try {
				Class<? extends Command> commandClass = (Class<? extends Command>) Class.forName(commandBeanDefinition
						.getBeanClassName());
				String id = Commands.getId(commandClass);
				registerCommand(id, commandClass);

			} catch (ClassNotFoundException e) {
				throw new RuntimeException();
			}
		}
	}

}