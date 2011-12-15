package pl.edu.agh.two.mud.server.command.executor;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.common.world.model.Field;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.RefreshUICommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;

public class RefreshUICommandExecutorTest {
	private static final String SUMMARY = "Summary";

	private RefreshUICommandExecutor executor;
	private RefreshUICommand command;
	private IServiceRegistry serviceRegistry;
	private Board board;
	private Field field;
	private Service currentService;
	private Dispatcher dispatcher;

	@Before
	public void prepareTest() {
		command = mock(RefreshUICommand.class);
		serviceRegistry = mock(IServiceRegistry.class);
		board = mock(Board.class);
		field = mock(Field.class);
		currentService = mock(Service.class);
		dispatcher = mock(Dispatcher.class);
		IPlayer player = mock(IPlayer.class);

		executor = new RefreshUICommandExecutor();
		executor.setBoard(board);
		executor.setServiceRegistry(serviceRegistry);
		executor.setDispatcher(dispatcher);

		when(serviceRegistry.getCurrentService()).thenReturn(currentService);
		when(serviceRegistry.getPlayer(currentService)).thenReturn(player);
		when(board.getPlayersPosition(player)).thenReturn(field);
		when(field.getFormattedFieldSummary()).thenReturn(SUMMARY);
	}

	@Test
	public void refreshCommand() {

		try {
			executor.execute(command);
			verify(dispatcher).dispatch(
					new SendMessageToUserCommand(field.getFormattedFieldSummary(), MessageType.INFO));
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}

}
