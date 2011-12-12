package pl.edu.agh.two.mud.server.command.executor;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.two.mud.common.Player;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.MoveUICommand;
import pl.edu.agh.two.mud.server.command.executor.MoveUICommandExecutor;
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

    @Before
    public void before() {
        board = new SampleBoard();
        when(serviceRegistry.getCurrentService()).thenReturn(service);
        executor.setBoard(board);
        executor.setServiceRegistry(serviceRegistry);
    }

    @Test
    public void shouldNotExecuteForNotLoggedInPlayer() throws IOException {
        // given
        MoveUICommand command = prepareValidMoveNorthCommand();

        // when
        executor.execute(command);

        // then
        verify(service).writeObject("Nie mozesz uzyc teraz tej komendy!");
    }

    @Test
    public void shouldNotChangeFieldWhenInvalidDirection() throws IOException {
        // given
        MoveUICommand command = prepareInvalidMoveCommand();
        when(serviceRegistry.getPlayer(service)).thenReturn(new Player());

        // when
        executor.execute(command);

        // then
        verify(service).writeObject("Nie mozesz tam isc!");
    }

    @Test
    public void shouldNotChangeFieldWhenDirectionNotPossible() throws IOException {
        // given
        MoveUICommand command = prepareValidMoveNorthCommand();
        Player player = new Player();
        when(serviceRegistry.getPlayer(service)).thenReturn(player);
        board.addPlayer(player);

        // when
        executor.execute(command);

        // then
        verify(service).writeObject("Nie mozesz tam isc!");
    }

    @Test
    public void shouldChangeField() throws IOException {
        // given
        MoveUICommand command = prepareValidMoveEastCommand();
        Player player = new Player();
        when(serviceRegistry.getPlayer(service)).thenReturn(player);
        board.addPlayer(player);

        // when

        executor.execute(command);

        // then

        Field newPlayersPosition = board.getPlayersPosition(player);
        assertThat(newPlayersPosition).isNotEqualTo(board.getStartingField());
        verify(service).writeObject(newPlayersPosition.getFormattedFieldSummary());
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
