package pl.edu.agh.two.mud.client.command.executor;

import static org.mockito.Matchers.matches;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.client.command.DisplayCommandsUICommand;
import pl.edu.agh.two.mud.client.command.registry.ICommandDefinitionRegistry;
import pl.edu.agh.two.mud.client.ui.Console;
import pl.edu.agh.two.mud.client.ui.MainWindow;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

public class DisplayCommandsUICommandExecutorTest {

	private MainWindow mainWindow;
	private Console console;
	private ICommandDefinitionRegistry commandDefinitionRegistry;
	private DisplayCommandsUICommandExecutor executor;

	@Before
	public void prepareTest() {
		// Console
		console = mock(Console.class);

		// Main Window
		mainWindow = mock(MainWindow.class);
		when(mainWindow.getMainConsole()).thenReturn(console);

		// Command Definition Registry
		commandDefinitionRegistry = mock(ICommandDefinitionRegistry.class);

		executor = new DisplayCommandsUICommandExecutor();
		executor.setMainWindow(mainWindow);
		executor.setCommandDefinitionRegistry(commandDefinitionRegistry);
	}

	@Test
	public void noCommandsAvailable() {
		Collection<ICommandDefinition> commands = Collections.emptyList();
		when(commandDefinitionRegistry.getCommandDefinitions()).thenReturn(
				commands);

		executor.execute(new DisplayCommandsUICommand());
		verify(console, only()).appendTextToConsole(matches("^Brak komend.$"));
	}

	@Test
	public void displayOneCommand() {
		ICommandDefinition commandDefinition = mock(ICommandDefinition.class);
		when(commandDefinition.getDescription()).thenReturn("description");
		when(commandDefinition.getNames()).thenReturn(
				Arrays.asList(new String[] { "name1", "name2" }));

		when(commandDefinitionRegistry.getCommandDefinitions()).thenReturn(
				Arrays.asList(new ICommandDefinition[] { commandDefinition }));

		executor.execute(new DisplayCommandsUICommand());
		verify(console, only()).appendTextToConsole(
				startsWith("Dostepne komendy:"));
	}

	@Test
	public void displayMoreThanOneCommand() {
		ICommandDefinition commandDefinition1 = mock(ICommandDefinition.class);
		when(commandDefinition1.getDescription()).thenReturn("description");
		when(commandDefinition1.getNames()).thenReturn(
				Arrays.asList(new String[] { "b", "name1" }));

		ICommandDefinition commandDefinition2 = mock(ICommandDefinition.class);
		when(commandDefinition2.getDescription()).thenReturn("description");
		when(commandDefinition2.getNames()).thenReturn(
				Arrays.asList(new String[] { "a", "name2" }));

		when(commandDefinitionRegistry.getCommandDefinitions()).thenReturn(
				Arrays.asList(new ICommandDefinition[] { commandDefinition1,
						commandDefinition2 }));

		executor.execute(new DisplayCommandsUICommand());
		verify(console, only())
				.appendTextToConsole(
						matches("Dostepne komendy:.*\\s*- a.*\\s*.*\\s*- b.*\\s*.*\\s*$"));
	}
}
