package pl.edu.agh.two.mud.server.command.executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.RefreshUICommand;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;

public class RefreshUICommandExecutorTest {
	private static final String SUMMARY = "Summary";

	private RefreshUICommandExecutor executor;
	private RefreshUICommand command;
	private IServiceRegistry serviceRegistry;
	private Board board;
	private Field field;
	private Service currentService;

	@Before
	public void prepareTest() {
		command = mock(RefreshUICommand.class);
		serviceRegistry = mock(IServiceRegistry.class);
		board = mock(Board.class);
		field = mock(Field.class);
		currentService = mock(Service.class);
		IPlayer player = mock(IPlayer.class);

		executor = new RefreshUICommandExecutor();
		executor.setBoard(board);
		executor.setServiceRegistry(serviceRegistry);

		when(serviceRegistry.getCurrentService()).thenReturn(currentService);
		when(serviceRegistry.getPlayer(currentService)).thenReturn(player);
		when(board.getPlayersPosition(player)).thenReturn(field);
		when(field.getFormattedFieldSummary()).thenReturn(SUMMARY);
	}

	@Test
	public void sendingIOError() {
		Throwable throwable = new IOException();
		try {
			doThrow(throwable).when(currentService).writeObject(any());
		} catch (IOException e) {
		}

		try {
			executor.execute(command);
			fail("Exception expected");
		} catch (CommandExecutingException e) {
			assertEquals(e.getCause(), throwable);
		}
	}

	@Test
	public void refreshCommand() {

		try {
			executor.execute(command);

			try {
				verify(currentService).writeObject(SUMMARY);
			} catch (IOException e) {
			}
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}

}
