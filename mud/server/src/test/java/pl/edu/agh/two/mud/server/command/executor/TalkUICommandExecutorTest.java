package pl.edu.agh.two.mud.server.command.executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.type.Text;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.TalkUICommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;

public class TalkUICommandExecutorTest {
	private static final String MESSAGE = "Some message";

	private TalkUICommandExecutor executor;
	private Board board;
	private IServiceRegistry serviceRegistry;
	private IPlayer currentPlayer;
	private Service currentService;
	private TalkUICommand command;

	@Before
	public void prepareTest() {
		board = mock(Board.class);
		currentService = mock(Service.class);
		currentPlayer = mock(IPlayer.class);

		serviceRegistry = mock(IServiceRegistry.class);
		when(serviceRegistry.getCurrentService()).thenReturn(currentService);
		when(serviceRegistry.getPlayer(currentService)).thenReturn(
				currentPlayer);

		executor = new TalkUICommandExecutor();
		executor.setBoard(board);
		executor.setServiceRegistry(serviceRegistry);

		command = mock(TalkUICommand.class);
		when(command.getContent()).thenReturn(new Text(MESSAGE));
	}

	@Test
	public void userDoesNotExist() {
		when(serviceRegistry.getPlayer(any(Service.class))).thenReturn(null);
		try {
			executor.execute(command);
			fail("Exception expected");
		} catch (ClientAwareException e) {
			assertEquals("Jestes niezalogowany.", e.getErrorMessage());

		} catch (CommandExecutingException e) {
			fail("Other exception expected");
		}
	}

	@Test
	public void noFieldForUser() {
		when(board.getPlayersPosition(currentPlayer)).thenReturn(null);

		try {
			executor.execute(command);
			fail("Exception expected");
		} catch (ClientAwareException e) {
			assertEquals("Nieznany blad.", e.getErrorMessage());
		} catch (CommandExecutingException e) {
			fail("Other exception expected");
		}
	}

	@Test
	public void sendingError() {
		Field field = mock(Field.class);
		IPlayer targetPlayer = mock(IPlayer.class);
		Service targetService = mock(Service.class);

		List<IPlayer> players = new ArrayList<IPlayer>();
		players.add(currentPlayer);
		players.add(targetPlayer);

		when(board.getPlayersPosition(currentPlayer)).thenReturn(field);
		when(field.getPlayers()).thenReturn(players);
		when(serviceRegistry.getService(targetPlayer))
				.thenReturn(targetService);
		IOException exception = new IOException();
		try {
			doThrow(exception).when(targetService).writeObject(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			executor.execute(command);
			fail("Exception expected");
		} catch (FatalException e) {
			assertEquals(exception, e.getCause());
		} catch (CommandExecutingException e) {
			fail("Other exception expected");
		}
	}

	@Test
	public void talkNoUser() {
		Field field = mock(Field.class);

		List<IPlayer> players = new ArrayList<IPlayer>();

		when(board.getPlayersPosition(currentPlayer)).thenReturn(field);
		when(field.getPlayers()).thenReturn(players);

		try {
			executor.execute(command);
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}

	@Test
	public void talkOneUser() {		
		talkNUsers(1);		
	}
	
	@Test
	public void talkManyUser() {		
		talkNUsers(100);		
	}

	private void talkNUsers(int howMany) {
		List<IPlayer> players = new ArrayList<IPlayer>();
		List<Service> services = new ArrayList<Service>();
		
		Field field = mock(Field.class);
		when(board.getPlayersPosition(currentPlayer)).thenReturn(field);

		players.add(currentPlayer);
		for (int i = 0; i < howMany; i++) {
			IPlayer targetPlayer = mock(IPlayer.class);
			Service targetService = mock(Service.class);
			when(serviceRegistry.getService(targetPlayer)).thenReturn(
					targetService);

			players.add(targetPlayer);
			services.add(targetService);
		}

		when(field.getPlayers()).thenReturn(players);
		
		try {
			executor.execute(command);
			for (Service service : services) {
				try {
					verify(service).writeObject(
							matches(String.format("^.* mowi: %s$", MESSAGE)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}
}
