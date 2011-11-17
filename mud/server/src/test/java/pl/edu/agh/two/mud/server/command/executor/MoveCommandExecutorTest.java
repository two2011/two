package pl.edu.agh.two.mud.server.command.executor;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.two.mud.common.Player;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.MoveCommand;
import pl.edu.agh.two.mud.server.command.executor.MoveCommandExecutor;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;
import pl.edu.agh.two.mud.server.world.model.SampleBoard;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MoveCommandExecutorTest {
    private final MoveCommandExecutor executor = new MoveCommandExecutor();
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
        MoveCommand command = prepareValidMoveNorthCommand();

        // when
        executor.execute(command);

        // then
        verify(service).writeObject("Nie mozesz uzyc teraz tej komendy!");
    }

    @Test
    public void shouldNotChangeFieldWhenInvalidDirection() throws IOException {
        // given
        MoveCommand command = prepareInvalidMoveCommand();
        when(serviceRegistry.getPlayer(service)).thenReturn(new Player());

        // when
        executor.execute(command);

        // then
        verify(service).writeObject("Nie mozesz tam isc!");
    }

    @Test
    public void shouldNotChangeFieldWhenDirectionNotPossible() throws IOException {
        // given
        MoveCommand command = prepareValidMoveNorthCommand();
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
        MoveCommand command = prepareValidMoveEastCommand();
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

    private MoveCommand prepareValidMoveEastCommand() {
        return new MoveCommand().withDirection("e");
    }

    private MoveCommand prepareValidMoveNorthCommand() {
        return new MoveCommand().withDirection("n");
    }

    private MoveCommand prepareInvalidMoveCommand() {
        return new MoveCommand().withDirection("c");
    }
}
