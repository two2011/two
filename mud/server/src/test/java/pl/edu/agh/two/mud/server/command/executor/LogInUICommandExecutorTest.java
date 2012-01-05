package pl.edu.agh.two.mud.server.command.executor;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.two.mud.common.Player;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.provider.CommandProvider;
import pl.edu.agh.two.mud.common.world.exception.NoCreatureWithSuchNameException;
import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.common.world.model.SampleBoard;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.LogInUICommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static pl.edu.agh.two.mud.common.message.MessageType.ERROR;

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
    public void shouldSuccessfullyExecuteLogInCommand()
            throws NoCreatureWithSuchNameException, FatalException,
            ClientAwareException {
        // given
        Player player = createPlayer("krzyho", "correctPassword");
        board.addPlayer(player);

        // when
        executor.execute(mockCommand());

        // then
        verify(serviceRegistry).bindPlayerToService(service, player);
    }

    @Test(expected = ClientAwareException.class)
    public void shouldNotLoginWhenPlayerIsUsed() throws FatalException, ClientAwareException {
        // given
        Player player = createPlayer("krzyho", "correctPassword");
        board.addPlayer(player);
        when(serviceRegistry.getService(player)).thenReturn(service);

        // when
        executor.execute(mockCommand());

        // then
        // should throw exception
    }

    @Test(expected = ClientAwareException.class)
    public void shouldNotLoginWithWrongPassword()
            throws NoCreatureWithSuchNameException, IOException, FatalException,
            ClientAwareException {
        // given

        Player player = createPlayer("krzyho", "wrongPassword");
        board.addPlayer(player);

        // when
        executor.execute(mockCommand());

        // then
        // should throw exception
    }

    @Test
    public void shouldNotLoginWhenNoPlayerRegistered() throws IOException,
            FatalException, ClientAwareException {
        // given
        LogInUICommand command = mockCommand();

        // when
        executor.execute(command);

        // then
        verify(dispatcher).dispatch(
                new SendMessageToUserCommand("Nie ma takiego gracza!", ERROR));
    }

    @Test(expected = FatalException.class)
    public void shouldThrowFatalExceptionOnIOException() throws FatalException, ClientAwareException, IOException {
        // given
        Player player = createPlayer("krzyho", "correctPassword");
        board.addPlayer(player);
        doThrow(new IOException()).when(service).writeObject(anyString());

        // when
        executor.execute(mockCommand());

        // then
        // should throw FatalException
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
