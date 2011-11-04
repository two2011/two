package pl.edu.agh.two.mud.server.command.provider;

import java.lang.reflect.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;

import pl.edu.agh.two.mud.server.command.*;
import pl.edu.agh.two.mud.server.command.executor.*;

public class SpringCommandExecutorProvider implements CommandExecutorProvider {

	private Map<Class<? extends Command>, Class<? extends CommandExecutor>> executorClassesByCommandClassess = new HashMap<Class<? extends Command>, Class<? extends CommandExecutor>>();

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public CommandExecutor<? extends Command> getExecutorForCommand(Command command) {
		Class<? extends CommandExecutor> commandExecutorClass = executorClassesByCommandClassess.get(command.getClass());
		if (commandExecutorClass == null) {
			throw new RuntimeException(
					String.format(
							"Command executor not found. Command class: %s. Make sure you've defined beans for Command and Executor in spring application context.",
							command.getClass().getName()));
		}
		CommandExecutor commandExecutor = beanFactory.getBean(commandExecutorClass);
		return commandExecutor;
	}

	private void buildProvider() {
		String[] commandExecutorsBeanNames = beanFactory.getBeanNamesForType(CommandExecutor.class);
		for (String commandExecutorBeanName : commandExecutorsBeanNames) {
			BeanDefinition commandExecutorBeanDefinition = beanFactory.getBeanDefinition(commandExecutorBeanName);
			try {
				Class<? extends CommandExecutor> commandExecutorClass = (Class<? extends CommandExecutor>) Class
						.forName(commandExecutorBeanDefinition.getBeanClassName());
				executorClassesByCommandClassess.put(getExecutedCommandClass(commandExecutorClass), commandExecutorClass);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException();
			}
		}
	}

	private Class<? extends Command> getExecutedCommandClass(Class<?> commandExecutorClass) {
		ParameterizedType parameterizedType = (ParameterizedType) commandExecutorClass.getGenericInterfaces()[0];
		Class<? extends Command> commandClass = (Class<? extends Command>) parameterizedType.getActualTypeArguments()[0];
		return commandClass;
	}

}
