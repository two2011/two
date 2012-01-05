package pl.edu.agh.two.mud.server.command.executor;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.common.world.exception.NoCreatureWithSuchNameException;
import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.common.world.model.Field;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.AttackUICommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.world.fight.Fight;


public class AttackUICommandExecutorTest {
	private static final String ENEMY_NAME = "enemy";
	
	private final AttackUICommandExecutor executor = new AttackUICommandExecutor();
	private Board board;
	private IPlayer currentPlayer;
	private IPlayer enemy;
	private Field field;
	private AttackUICommand command;
	private Fight fight;
	private final ServiceRegistry serviceRegistry = mock(ServiceRegistry.class);
    private final Service service = mock(Service.class);
	private final Dispatcher dispatcher = mock(Dispatcher.class);
	
	@Before
    public void before() {
        board = mock(Board.class);
        currentPlayer = mock(IPlayer.class);
        enemy = mock(IPlayer.class);
        field = mock(Field.class);
        command = mock(AttackUICommand.class);
        fight = mock(Fight.class);
        
        when(serviceRegistry.getCurrentService()).thenReturn(service);
        when(serviceRegistry.getPlayer(service)).thenReturn(currentPlayer);
        when(board.getPlayersPosition(currentPlayer)).thenReturn(field);
        
        executor.setBoard(board);
        executor.setServiceRegistry(serviceRegistry);
        executor.setDispatcher(dispatcher);
        executor.setFight(fight);
    }

	@Test
	public void shouldStartFight() {
		try {
			when(command.getPlayer()).thenReturn(ENEMY_NAME);
			when(field.getCreatureByName(ENEMY_NAME)).thenReturn(enemy);
			when(enemy.isInFight()).thenReturn(false);
			
			executor.execute(command);
			verify(enemy).setEnemy(currentPlayer);
			verify(currentPlayer).setEnemy(enemy);
			verify(fight).startFight(currentPlayer, enemy);
		} catch (NoCreatureWithSuchNameException e) {
			fail("Exception unexpected");
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}
	
	@Test
	public void shouldNotStartFightIfEnemyIsNull() {
		try {
			when(command.getPlayer()).thenReturn(ENEMY_NAME);
			when(field.getCreatureByName(ENEMY_NAME)).thenReturn(null);
			
			executor.execute(command);
			verify(dispatcher).dispatch(new SendMessageToUserCommand(String.format("Przeciwnik %s nie znajduje sie na Twoim polu", ENEMY_NAME), MessageType.INFO));
		} catch (NoCreatureWithSuchNameException e) {
			fail("Exception unexpected");
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}
	
	@Test
	public void shouldNotStartFightWithHimself() {
		try {
			when(command.getPlayer()).thenReturn(ENEMY_NAME);
			when(field.getCreatureByName(ENEMY_NAME)).thenReturn(currentPlayer);
			
			executor.execute(command);
			verify(dispatcher).dispatch(new SendMessageToUserCommand("Nie mozesz atakowac sam siebie", MessageType.INFO));
		} catch (NoCreatureWithSuchNameException e) {
			fail("Exception unexpected");
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}
	
	@Test
	public void shouldNotStartFightIfEnemyIsFighting() {
		try {
			when(command.getPlayer()).thenReturn(ENEMY_NAME);
			when(field.getCreatureByName(ENEMY_NAME)).thenReturn(enemy);
			when(enemy.isInFight()).thenReturn(true);
			
			executor.execute(command);
			verify(dispatcher).dispatch(new SendMessageToUserCommand(String.format("Przeciwnik %s aktualnie walczy", ENEMY_NAME), MessageType.INFO));
		} catch (NoCreatureWithSuchNameException e) {
			fail("Exception unexpected");
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}
}
