package pl.edu.agh.two.mud.client.command.executor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.client.Connection;
import pl.edu.agh.two.mud.client.command.DelegateToServerCommand;
import pl.edu.agh.two.mud.client.command.WriteErrorOnConsoleCommand;
import pl.edu.agh.two.mud.common.command.IParsedCommand;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;

public class DelegateToServerCommandExecutorTest {

	private Connection connection;
	private Dispatcher dispatcher;
	private DelegateToServerCommandExecutor executor;
	private DelegateToServerCommand command;
	private IParsedCommand parsedCommand;

	@Before
	public void prepareTest() {
		connection = mock(Connection.class);
		dispatcher = mock(Dispatcher.class);

		executor = new DelegateToServerCommandExecutor();
		executor.setConnection(connection);
		executor.setDispatcher(dispatcher);

		parsedCommand = mock(IParsedCommand.class);

		command = mock(DelegateToServerCommand.class);
		when(command.getParsedCommand()).thenReturn(parsedCommand);
	}

	@Test
	public void handleIOException() throws IOException {
		doThrow(new IOException()).when(connection).send(parsedCommand);
		executor.execute(command);

		verify(dispatcher, only()).dispatch(
				any(WriteErrorOnConsoleCommand.class));
	}

	@Test
	public void sendCommandTest() throws IOException {
		executor.execute(command);
		verify(connection, only()).send(parsedCommand);
	}

}
