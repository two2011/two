package pl.edu.agh.two.mud.server;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.ICreature;
import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;

public class CreaturePlayerFightTest {

	private CreaturePlayerFight fight;
	private IPlayer player;
	private ICreature monster;
	private Dispatcher dispatcher;
	private Random random;

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

		verify(dispatcher).dispatch(
				new SendMessageToUserCommand(player, String.format("Zaatakowa³ Ciê potwór %s", monster.getName()),
						MessageType.INFO));
		verify(dispatcher).dispatch(new SendMessageToUserCommand(player, "Twoja tura", MessageType.INFO));
	}
}
