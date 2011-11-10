package pl.edu.agh.two.mud.server.executor;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.two.mud.common.Player;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.LogInCommand;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;
import pl.edu.agh.two.mud.server.world.model.Board;

import java.io.IOException;

import static org.mockito.Mockito.*;


public class LogInCommandExecutorTest {
    LogInCommandExecutor executor = new LogInCommandExecutor();
    Board board;
    ServiceRegistry serviceRegistry = mock(ServiceRegistry.class);
    Service service = mock(Service.class);

    @Before
    public void before() {
        board = new Board();
        when(serviceRegistry.getCurrentService()).thenReturn(service);
        executor.setBoard(board);
        executor.setServiceRegistry(serviceRegistry);
    }

    @Test
    public void shouldSuccessfullyExecuteLogInCommand() throws NoPlayerWithSuchNameException {
        // given
        LogInCommand command = mockCommand();

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
        LogInCommand command = mockCommand();

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
        LogInCommand command = mockCommand();

        // when
        executor.execute(command);

        // then
        verify(service).writeObject("Nie ma takiego gracza");
    }

    private LogInCommand mockCommand() {
        LogInCommand command = mock(LogInCommand.class);
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
