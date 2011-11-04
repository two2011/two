package pl.edu.agh.two.mud.common.command.provider;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;

import pl.edu.agh.two.mud.common.command.*;

public class SpringCommandProvider implements CommandProvider {

	private Map<String, Class<? extends Command>> commandClassesById = new HashMap<String, Class<? extends Command>>();

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public Command getCommandById(String commandId) {
		Class<? extends Command> commandClass = commandClassesById.get(commandId);
		Command command = beanFactory.getBean(commandClass);
		return command;
	}

	@Override
	public List<Command> getAvailableCommands() {
		List<Command> availableCommands = new LinkedList<Command>();
		for (Class<? extends Command> commandClass : commandClassesById.values()) {
			Command command = beanFactory.getBean(commandClass);
			availableCommands.add(command);
		}
		return availableCommands;
	}

	private void buildProvider() {
		String[] commandBeanNames = beanFactory.getBeanNamesForType(Command.class);
		for (String commandBeanName : commandBeanNames) {
			BeanDefinition commandBeanDefinition = beanFactory.getBeanDefinition(commandBeanName);
			try {
				Class<? extends Command> commandClass = (Class<? extends Command>) Class.forName(commandBeanDefinition.getBeanClassName());
				String id = commandClass.getName();
				commandClassesById.put(id, commandClass);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException();
			}
		}
	}

}
