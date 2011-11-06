package pl.edu.agh.two.mud.common.command.provider;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import pl.edu.agh.two.mud.common.command.Command;

public class SpringCommandProvider implements CommandProvider {

	private Map<String, Class<? extends Command>> commandClassesById = new HashMap<String, Class<? extends Command>>();

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public Command getCommandById(String commandId) {
		Class<? extends Command> commandClass = commandClassesById
				.get(commandId);
		Command command = beanFactory.getBean(commandClass);
		return command;
	}

	@Override
	public List<Command> getAvailableCommands() {
		List<Command> availableCommands = new LinkedList<Command>();
		for (Class<? extends Command> commandClass : commandClassesById
				.values()) {
			Command command = beanFactory.getBean(commandClass);
			availableCommands.add(command);
		}
		return availableCommands;
	}

	protected void registerCommand(String id,
			Class<? extends Command> commandClass) {
		commandClassesById.put(id, commandClass);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void buildProvider() {
		String[] commandBeanNames = beanFactory
				.getBeanNamesForType(Command.class);
		for (String commandBeanName : commandBeanNames) {
			BeanDefinition commandBeanDefinition = beanFactory
					.getBeanDefinition(commandBeanName);
			try {
				Class<? extends Command> commandClass = (Class<? extends Command>) Class
						.forName(commandBeanDefinition.getBeanClassName());
				String id = commandClass.getName();
				registerCommand(id, commandClass);

			} catch (ClassNotFoundException e) {
				throw new RuntimeException();
			}
		}
	}

}
