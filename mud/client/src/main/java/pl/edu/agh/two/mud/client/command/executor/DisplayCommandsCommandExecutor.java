package pl.edu.agh.two.mud.client.command.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import pl.edu.agh.two.mud.client.command.DisplayCommandsCommand;
import pl.edu.agh.two.mud.client.command.registry.ICommandDefinitionRegistry;
import pl.edu.agh.two.mud.client.ui.Console;
import pl.edu.agh.two.mud.client.ui.MainWindow;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;

public class DisplayCommandsCommandExecutor implements
		CommandExecutor<DisplayCommandsCommand> {

	private Console console;

	private ICommandDefinitionRegistry commandDefinitionRegistry;

	public void setMainWindow(MainWindow mainWindow) {
		console = mainWindow.getMainConsole();
	}

	public void setCommandDefinitionRegistry(
			ICommandDefinitionRegistry commandDefinitionRegistry) {
		this.commandDefinitionRegistry = commandDefinitionRegistry;
	}

	@Override
	public void execute(DisplayCommandsCommand command) {
		StringBuilder builder = new StringBuilder();

		List<ICommandDefinition> commandDefinitions = new ArrayList<ICommandDefinition>(
				commandDefinitionRegistry.getCommandDefinitions());
		Collections.sort(commandDefinitions,
				new Comparator<ICommandDefinition>() {

					@Override
					public int compare(ICommandDefinition o1,
							ICommandDefinition o2) {
						return o1.getNames().iterator().next()
								.compareTo(o2.getNames().iterator().next());
					}
				});
		if (commandDefinitions.size() > 0) {
			builder.append("Dostepne komendy:\n");
			for (ICommandDefinition commandDefinition : commandDefinitions) {
				builder.append(getCommand(commandDefinition));
			}
		} else {
			builder.append("Brak komend.");
		}

		console.appendTextToConsole(builder.toString());
	}

	private String getCommand(ICommandDefinition commandDefinition) {
		return String.format("      - %s\n            %s\n",
				getNameWithAliases(commandDefinition.getNames()),
				commandDefinition.getDescription());
	}

	private String getNameWithAliases(Collection<String> names) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = names.iterator();
		builder.append(iterator.next());

		if (names.size() > 1) {
			builder.append(" [");
			while (iterator.hasNext()) {
				builder.append(String.format("%s, ", iterator.next()));
			}
			builder.delete(builder.length() - 2, builder.length() - 1);
			builder.append("]");
		}

		return builder.toString();
	}
}
