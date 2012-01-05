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
import pl.edu.agh.two.mud.server.command.LogOutCommand;
import pl.edu.agh.two.mud.server.command.LogOutUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommandsCommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static pl.edu.agh.two.mud.common.message.MessageType.INFO;

public class LogOutUICommandExecutorTest {

    LogOutUICommandExecutor executor = new LogOutUICommandExecutor();
    Board board;
    ServiceRegistry serviceRegistry = mock(ServiceRegistry.class);
    Service service = mock(Service.class);
    Dispatcher dispatcher = mock(Dispatcher.class);
    CommandProvider commandProvider = mock(CommandProvider.class);

    @Before
    public void before() {
        board = new SampleBoard();
        when(serviceRegistry.getCurrentService()).thenReturn(service);
        executor.setServiceRegistry(serviceRegistry);
        executor.setDispatcher(dispatcher);

        AvailableCommands availableCommands = AvailableCommands.getInstance();
        availableCommands.setCommandProvider(commandProvider);
    }

    @Test
    public void shouldSuccessfullyExecuteLogOutCommand()
            throws NoCreatureWithSuchNameException, FatalException {
        // given
        LogOutUICommand command = mock(LogOutUICommand.class);

        Player player = createPlayer("krzyho", "correctPassword");
        board.addPlayer(player);
        when(serviceRegistry.getPlayer(service)).thenReturn(player);

        // when
        executor.execute(command);

        // then
        verify(dispatcher).dispatch(new SendMessageToUserCommand("Zegnaj, krzyho", INFO));
        verify(dispatcher, atLeast(1)).dispatch(any(SendAvailableCommandsCommand.class));
        verify(dispatcher, atLeast(1)).dispatch(any(LogOutCommand.class));
    }


    @Test(expected = FatalException.class)
    public void shouldThrowFatalExceptionOnIOException() throws FatalException, ClientAwareException, IOException {
        // given
        LogOutUICommand command = mock(LogOutUICommand.class);
        Player player = createPlayer("krzyho", "correctPassword");
        board.addPlayer(player);
        when(serviceRegistry.getPlayer(service)).thenReturn(player);
        doThrow(new IOException()).when(service).writeObject(anyString());

        // when
        executor.execute(command);

        // then
        // should throw FatalException
    }

    @Test
    public void shouldNotLogoutWhenNoPlayer() throws IOException,
            FatalException {
        // given
        LogOutUICommand command = mock(LogOutUICommand.class);

        // when
        executor.execute(command);

        // then
        verify(dispatcher).dispatch(
                new SendMessageToUserCommand("Nie jestes zalogowany!", INFO));
    }

    private Player createPlayer(String name, String password) {
        Player player = new Player();
        player.setName(name);
        player.setPassword(password);
        return player;
    }
}
