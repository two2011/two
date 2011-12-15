package pl.edu.agh.two.mud.server;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableList.Builder;

import pl.edu.agh.two.mud.common.ICreature;
import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.provider.CommandProvider;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.command.HitUICommand;
import pl.edu.agh.two.mud.server.command.LogInUICommand;
import pl.edu.agh.two.mud.server.command.LogOutUICommand;
import pl.edu.agh.two.mud.server.command.RefreshUICommand;
import pl.edu.agh.two.mud.server.command.RegisterUICommand;
import pl.edu.agh.two.mud.server.command.RunUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommandsCommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.TalkUICommand;
import pl.edu.agh.two.mud.server.command.WhisperUICommand;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;

public class CreaturePlayerFightTest {

	private CreaturePlayerFight fight;
	private IPlayer player;
	private ICreature monster;
	private Dispatcher dispatcher;
	private Random random;
	
	private static List<UICommand> figtOponnentTurn;
	private static List<UICommand> fightYourTurn;
	private static List<UICommand> gameCommands;
	private static List<UICommand> deadPlayerCommands;
	
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
		player = mock(IPlayer.class);
		monster = mock(ICreature.class);
		dispatcher = mock(Dispatcher.class);
		random = mock(Random.class);

		fight = new CreaturePlayerFight();
		fight.setDispatcher(dispatcher);
		fight.setRandom(random);
	}

	@Test
	public void playerMonsterPlayerOpensFight() {
		when(random.nextInt(anyInt())).thenReturn(0);

		fight.startFight(player, monster);
		playerStartsFight(player, monster);

	}

	@Test
	public void monsterPlayerPlayerOpensFight() {
		when(random.nextInt(anyInt())).thenReturn(0);

		fight.startFight(monster, player);
		playerStartsFight(player, monster);
	}

	private void playerStartsFight(IPlayer player, ICreature monster) {
		verify(dispatcher).dispatch(
				new SendMessageToUserCommand(player, String.format("Zaatakowa³ Ciê potwór %s", monster.getName()),
						MessageType.INFO));
		verify(dispatcher).dispatch(new SendMessageToUserCommand(player, "Twoja tura", MessageType.INFO));
		verify(dispatcher).dispatch(new SendAvailableCommandsCommand(player, fightYourTurn));
	}

}
