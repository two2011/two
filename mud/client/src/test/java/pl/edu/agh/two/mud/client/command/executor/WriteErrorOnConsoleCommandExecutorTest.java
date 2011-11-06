package pl.edu.agh.two.mud.client.command.executor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.client.command.WriteErrorOnConsoleCommand;
import pl.edu.agh.two.mud.client.ui.Console;

public class WriteErrorOnConsoleCommandExecutorTest {

	private static final String ERROR_MESSAGE = "error";
	private WriteErrorOnConsoleCommandExecutor executor;
	private Console console;
	private WriteErrorOnConsoleCommand command;

	@Before
	public void prepareTest() {
		console = mock(Console.class);

		command = mock(WriteErrorOnConsoleCommand.class);
		when(command.getErrorMessage()).thenReturn(ERROR_MESSAGE);

		executor = new WriteErrorOnConsoleCommandExecutor();
		executor.setConsole(console);
	}

	@Test
	public void errorOnConsole() {
		executor.execute(command);
		verify(console, only()).appendTextToConsole(ERROR_MESSAGE);
	}

}
