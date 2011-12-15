package pl.edu.agh.two.mud.server.world.fight.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.provider.CommandProvider;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.HitUICommand;
import pl.edu.agh.two.mud.server.command.LogInUICommand;
import pl.edu.agh.two.mud.server.command.LogOutUICommand;
import pl.edu.agh.two.mud.server.command.MoveUICommand;
import pl.edu.agh.two.mud.server.command.RefreshUICommand;
import pl.edu.agh.two.mud.server.command.RegisterUICommand;
import pl.edu.agh.two.mud.server.command.RunUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommandsCommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.TalkUICommand;
import pl.edu.agh.two.mud.server.command.WhisperUICommand;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.model.Direction;

import com.google.common.collect.ImmutableList.Builder;

public class PlayersFightTest {

	private IServiceRegistry serviceRegistry;
	private Dispatcher dispatcher;
	private Random random;
	private IPlayer player1;
	private IPlayer player2;
	private static List<UICommand> figtOponnentTurn;
	private static List<UICommand> fightYourTurn;
	private static List<UICommand> gameCommands;
	private static List<UICommand> deadPlayerCommands;

	private PlayersFight fight;

	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void prepare() {
		figtOponnentTurn = new Builder<UICommand>().add(mock(UICommand.class)).add(mock(UICommand.class)).build();
		fightYourTurn = new Builder<UICommand>().add(mock(UICommand.class)).add(mock(UICommand.class))
				.add(mock(UICommand.class)).build();
		gameCommands = new Builder<UICommand>().add(mock(UICommand.class)).build();
		deadPlayerCommands = new Builder<UICommand>().add(mock(UICommand.class)).add(mock(UICommand.class))
				.add(mock(UICommand.class)).add(mock(UICommand.class)).build();

		// AvailableCommands mocking
		CommandProvider provider = mock(CommandProvider.class);

		when(provider.getUICommands(RefreshUICommand.class, TalkUICommand.class, WhisperUICommand.class)).thenReturn(
				figtOponnentTurn);
		when(
				provider.getUICommands(HitUICommand.class, RunUICommand.class, RefreshUICommand.class,
						TalkUICommand.class, WhisperUICommand.class)).thenReturn(fightYourTurn);
		when(
				provider.getUICommandsWithout(RegisterUICommand.class, LogInUICommand.class, HitUICommand.class,
						RunUICommand.class)).thenReturn(gameCommands);
		when(
				provider.getUICommands(RefreshUICommand.class, TalkUICommand.class, WhisperUICommand.class,
						LogOutUICommand.class)).thenReturn(deadPlayerCommands);

		AvailableCommands commands = AvailableCommands.getInstance();
		commands.setCommandProvider(provider);
	}

	@Before
	public void prepareTest() {
		serviceRegistry = mock(IServiceRegistry.class);
		dispatcher = mock(Dispatcher.class);
		random = mock(Random.class);
		player1 = mock(IPlayer.class);
		player2 = mock(IPlayer.class);

		fight = new PlayersFight();
		fight.setServiceRegistry(serviceRegistry);
		fight.setDispatcher(dispatcher);
		fight.setRandom(random);

		when(player1.getEnemy()).thenReturn(player2);
		when(player2.getEnemy()).thenReturn(player1);
		when(player1.isAlive()).thenReturn(true);
		when(player2.isAlive()).thenReturn(true);
	}

	@Test
	public void attackerFirstTurn() {
		when(random.nextInt(anyInt())).thenReturn(0);

		fightStarted(player1, player2);
		switchPlayers(player2, player1);
	}

	@Test
	public void attackedFirstTurn() {
		when(random.nextInt(anyInt())).thenReturn(1);

		fightStarted(player1, player2);
		switchPlayers(player1, player2);
	}

	@Test
	public void endTurnPlayerSwitching() {
		fight.switchAttackingPlayer(player1, player2);

		verify(dispatcher).dispatch(new SendMessageToUserCommand(player1, "Tura przeciwnika", MessageType.INFO));
		verify(dispatcher).dispatch(new SendMessageToUserCommand(player2, "Twoja tura", MessageType.INFO));

		switchPlayers(player1, player2);
	}

	@Test
	public void hitSendingError() {
		Service service = prepareService(player2);

		IOException throwable = new IOException();
		try {
			doThrow(throwable).when(service).writeObject(any());
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}

		try {
			fight.hit(player1);
			fail("Exception expected");
		} catch (FatalException e) {
			assertEquals(throwable, e.getCause());
		}
	}

	@Test
	public void hitToHurt() {
		hit(player1, 1, true);
		verify(dispatcher).dispatch(new SendMessageToUserCommand(player2, "Krwawisz!", MessageType.INFO));
		switchPlayers(player1, player2);
	}

	@Test
	public void hitToKill() {
		hit(player1, 1, false);
		verify(dispatcher).dispatch(new SendMessageToUserCommand(player1, "Wygrales!", MessageType.INFO));
		verify(dispatcher).dispatch(new SendMessageToUserCommand(player2, "Zginales!", MessageType.INFO));
		endFight(player1, player2);
	}

	@Test
	public void didNotManageToRun() {
		when(random.nextInt(anyInt())).thenReturn(0);

		fight.runFromFight(player1, Direction.N);

		verify(dispatcher).dispatch(new SendMessageToUserCommand("Nie udalo Ci sie uciec", MessageType.INFO));
		verify(dispatcher).dispatch(
				new SendMessageToUserCommand(player2, "Przeciwnik probowal uciec lecz mu sie nie udalo",
						MessageType.INFO));
		switchPlayers(player1, player2);
	}

	@Test
	public void manageToRun() {
		when(random.nextInt(anyInt())).thenReturn(1);

		Direction direction = Direction.N;
		fight.runFromFight(player1, direction);

		verify(dispatcher).dispatch(new SendMessageToUserCommand("Udalo Ci sie uciec", MessageType.INFO));
		verify(dispatcher).dispatch(
				new SendMessageToUserCommand(player2, "Przeciwnikowi udalo sie uciec", MessageType.INFO));
		verify(dispatcher).dispatch(new MoveUICommand(direction));
		endFight(player1, player2);
	}

	private void fightStarted(IPlayer atacker, IPlayer atacked) {
		fight.startFight(player1, player2);

		verify(dispatcher).dispatch(
				new SendMessageToUserCommand(String.format("Zaatkowales gracza %s", atacked.getName()),
						MessageType.INFO));
		verify(dispatcher).dispatch(
				new SendMessageToUserCommand(atacked, String.format("Zostales zaatakowany przez gracza %s",
						atacker.getName()), MessageType.INFO));
	}

	private void switchPlayers(IPlayer player1, IPlayer player2) {
		verify(dispatcher).dispatch(new SendAvailableCommandsCommand(player1, figtOponnentTurn));
		verify(dispatcher).dispatch(new SendAvailableCommandsCommand(player2, fightYourTurn));
	}

	private Service prepareService(IPlayer player) {
		Service service = mock(Service.class);
		when(serviceRegistry.getService(player)).thenReturn(service);

		return service;
	}

	private void hit(IPlayer atacker, int damage, boolean alive) {
		when(random.nextInt(anyInt())).thenReturn(damage - 1);

		IPlayer enemy = atacker.getEnemy();
		prepareService(enemy);
		when(enemy.isAlive()).thenReturn(alive);

		try {
			fight.hit(atacker);
		} catch (FatalException e) {
			e.printStackTrace();
			fail("Unexception expected");
		}

		verify(enemy).subtractHealthPoints(damage);

		verify(dispatcher).dispatch(
				new SendMessageToUserCommand(atacker, String.format("Zadales przeciwnikowi %d pkt obrazen.", damage),
						MessageType.INFO));
		verify(dispatcher).dispatch(
				new SendMessageToUserCommand(enemy, String.format("Zadano ci %d pkt obrazen.", damage),
						MessageType.INFO));
	}

	private void endFight(IPlayer player1, IPlayer player2) {
		playersFightEnd(player1);
		playersFightEnd(player2);
	}

	private void playersFightEnd(IPlayer player) {
		verify(player).setEnemy(null);

		if (player.isAlive()) {
			verify(dispatcher).dispatch(new SendAvailableCommandsCommand(player, gameCommands));
		} else {
			verify(dispatcher).dispatch(new SendAvailableCommandsCommand(player, deadPlayerCommands));
		}
	}

}
