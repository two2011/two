package pl.edu.agh.two.mud.server;

import java.util.Random;

import pl.edu.agh.two.mud.common.ICreature;
import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.world.fight.Fight;
import pl.edu.agh.two.mud.server.world.model.Direction;

public class CreaturePlayerFight implements Fight {

	private Dispatcher dispatcher;
	private Random random;

	@Override
	public void startFight(ICreature creatureOne, ICreature creatureTwo) {
		IPlayer player = (IPlayer) creatureOne;
		ICreature monster = creatureTwo;

		int whoAttacksFirst = random.nextInt(2);
		switch (whoAttacksFirst) {
			case 0:
				// Player start

				dispatcher.dispatch(new SendMessageToUserCommand(player, String.format("Zaatakowa³ Ciê potwór %s",
						monster.getName()), MessageType.INFO));
				dispatcher.dispatch(new SendMessageToUserCommand(player, "Twoja tura", MessageType.INFO));

				break;
		}

	}

	@Override
	public void hit(ICreature creature) throws FatalException {

	}

	@Override
	public void switchAttackingPlayer(ICreature from, ICreature to) {

	}

	@Override
	public void runFromFight(ICreature creature, Direction direction) {

	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
