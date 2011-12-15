package pl.edu.agh.two.mud.server;

import java.util.Random;

import pl.edu.agh.two.mud.common.ICreature;
import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.command.SendAvailableCommandsCommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.fight.Fight;

public class CreaturePlayerFight implements Fight {

	private Dispatcher dispatcher;
	private Random random;

	@Override
	public void startFight(ICreature creatureOne, ICreature creatureTwo) {
		IPlayer player = null;
		ICreature monster = creatureTwo;

		if (creatureOne instanceof IPlayer) {
			player = (IPlayer) creatureOne;
			monster = creatureTwo;
		} else {
			player = (IPlayer) creatureTwo;
			monster = creatureOne;
		}

		int whoAttacksFirst = random.nextInt(2);
		switch (whoAttacksFirst) {
			case 0:
				// Player start

				dispatcher.dispatch(new SendMessageToUserCommand(player, String.format("Zaatakowa� Ci� potw�r %s",
						monster.getName()), MessageType.INFO));
				dispatcher.dispatch(new SendMessageToUserCommand(player, "Twoja tura", MessageType.INFO));
				dispatcher.dispatch(new SendAvailableCommandsCommand(player, AvailableCommands.getInstance()
						.getFightYouTurnCommands()));

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
	public void runFromFight(ICreature creature, pl.edu.agh.two.mud.common.world.model.Direction direction) {

	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}