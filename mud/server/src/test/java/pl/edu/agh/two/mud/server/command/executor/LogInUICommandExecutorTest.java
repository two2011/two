package pl.edu.agh.two.mud.server.command.executor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.Player;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.provider.CommandProvider;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.LogInUICommand;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.SampleBoard;

public class LogInUICommandExecutorTest {
	LogInUICommandExecutor executor = new LogInUICommandExecutor();
	Board board;
	ServiceRegistry serviceRegistry = mock(ServiceRegistry.class);
	Service service = mock(Service.class);
	Dispatcher dispatcher = mock(Dispatcher.class);
	CommandProvider commandProvider = mock(CommandProvider.class);

	@Before
	public void before() {
		board = new SampleBoard();
		when(serviceRegistry.getCurrentService()).thenReturn(service);
		executor.setBoard(board);
		executor.setServiceRegistry(serviceRegistry);
		executor.setDispatcher(dispatcher);

		AvailableCommands availableCommands = AvailableCommands.getInstance();
		availableCommands.setCommandProvider(commandProvider);
	}

	@Test
	public void shouldSuccessfullyExecuteLogInCommand() throws NoPlayerWithSuchNameException {
		// given
		LogInUICommand command = mockCommand();

		Player player = createPlayer("krzyho", "correctPassword");
		board.addPlayer(player);

		// when
		executor.execute(command);

		// then
		verify(serviceRegistry).bindPlayerToService(service, player);
	}

	@Test
	public void shouldNotLoginWithWrongPassword() throws NoPlayerWithSuchNameException, IOException {
		// given
		LogInUICommand command = mockCommand();

		Player player = createPlayer("krzyho", "wrongPassword");
		board.addPlayer(player);

		// when
		executor.execute(command);

		// then
		verify(service).writeObject("Zle haslo");
	}

	@Test
	public void shouldNotLoginWhenNoPlayerRegistered() throws IOException {
		// given
		LogInUICommand command = mockCommand();

		// when
		executor.execute(command);

		// then
		verify(service).writeObject("Nie ma takiego gracza");
	}

	private LogInUICommand mockCommand() {
		LogInUICommand command = mock(LogInUICommand.class);
		when(command.getLogin()).thenReturn("krzyho");
		when(command.getPassword()).thenReturn("correctPassword");
		return command;
	}

	private Player createPlayer(String name, String password) {
		Player player = new Player();
		player.setName(name);
		player.setPassword(password);
		return player;
	}
}
