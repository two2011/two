package pl.edu.agh.two.mud.server.command.executor;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.two.mud.common.Player;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.LogOutUICommand;
import pl.edu.agh.two.mud.server.command.MoveUICommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;
import pl.edu.agh.two.mud.server.world.model.SampleBoard;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MoveUICommandExecutorTest {
    private final MoveUICommandExecutor executor = new MoveUICommandExecutor();
    private Board board;
    private final ServiceRegistry serviceRegistry = mock(ServiceRegistry.class);
    private final Service service = mock(Service.class);
    private final Dispatcher dispatcher = mock(Dispatcher.class);

    @Before
    public void before() {
        board = new SampleBoard();
        when(serviceRegistry.getCurrentService()).thenReturn(service);
        executor.setBoard(board);
        executor.setServiceRegistry(serviceRegistry);
        executor.setDispatcher(dispatcher);
    }

    @Test
    public void shouldNotExecuteForNotLoggedInPlayer() throws IOException, FatalException {
        // given
        MoveUICommand command = prepareValidMoveNorthCommand();

        // when
        executor.execute(command);

        // then
        verify(dispatcher).dispatch(new SendMessageToUserCommand("Nie mozesz uzyc teraz tej komendy!", MessageType.INFO));
    }

    @Test
    public void shouldNotChangeFieldWhenInvalidDirection() throws IOException, FatalException {
        // given
        MoveUICommand command = prepareInvalidMoveCommand();
        when(serviceRegistry.getPlayer(service)).thenReturn(new Player());

        // when
        executor.execute(command);

        // then
        verify(dispatcher).dispatch(new SendMessageToUserCommand("Nie mozesz tam isc!", MessageType.INFO));
    }

    @Test
    public void shouldNotChangeFieldWhenDirectionNotPossible() throws IOException, FatalException {
        // given
        MoveUICommand command = prepareValidMoveNorthCommand();
        Player player = mock(Player.class);
        when(serviceRegistry.getPlayer(service)).thenReturn(player);
        board.addPlayer(player);

        // when
        executor.execute(command);

        // then
        verify(dispatcher).dispatch(new SendMessageToUserCommand("Nie mozesz tam isc!", MessageType.INFO));
    }

    @Test
    public void shouldChangeField() throws IOException, FatalException {
        // given
        MoveUICommand command = prepareValidMoveEastCommand();
        Player player = mock(Player.class);
        when(serviceRegistry.getPlayer(service)).thenReturn(player);
        board.addPlayer(player);

        // when

        executor.execute(command);

        // then

        Field newPlayersPosition = board.getPlayersPosition(player);
        assertThat(newPlayersPosition).isNotEqualTo(board.getStartingField());
        verify(dispatcher).dispatch(new SendMessageToUserCommand(newPlayersPosition.getFormattedFieldSummary(), MessageType.INFO));
    }

    private MoveUICommand prepareValidMoveEastCommand() {
        return new MoveUICommand().withDirection("e");
    }

    private MoveUICommand prepareValidMoveNorthCommand() {
        return new MoveUICommand().withDirection("n");
    }

    private MoveUICommand prepareInvalidMoveCommand() {
        return new MoveUICommand().withDirection("c");
    }
}
