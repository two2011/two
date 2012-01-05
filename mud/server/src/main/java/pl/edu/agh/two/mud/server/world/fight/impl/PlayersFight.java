package pl.edu.agh.two.mud.server.world.fight.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.common.ICreature;
import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.common.world.model.Direction;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.MoveUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommandsCommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.fight.Fight;

public class PlayersFight implements Fight {
	private Dispatcher dispatcher;
	private IServiceRegistry serviceRegistry;
	private Random random;

	@Override
	public void startFight(ICreature creatureOne, ICreature creatureTwo) {
		IPlayer playerOne = (IPlayer) creatureOne;
		IPlayer playerTwo = (IPlayer) creatureTwo;

		dispatcher
				.dispatch(new SendMessageToUserCommand(String.format(
						"Zaatkowales gracza %s", playerOne.getName()),
						MessageType.INFO));
		dispatcher.dispatch(new SendMessageToUserCommand(playerTwo, String
				.format("Zostales zaatakowany przez gracza %s",
						playerOne.getName()), MessageType.INFO));

		int whoAttacksFirst = random.nextInt(2);
		switch (whoAttacksFirst) {
		case 0:
			switchAttackingPlayer(creatureTwo, creatureOne);
			break;
		case 1:
			switchAttackingPlayer(creatureOne, creatureTwo);
			break;
		}

	}

	@Override
	public void hit(ICreature creatureWhoHits) throws FatalException {
		IPlayer playerWhoHits = (IPlayer) creatureWhoHits;

		IPlayer enemy = playerWhoHits.getEnemy();
		int damage = random.nextInt(4) + 1;
		enemy.subtractHealthPoints(damage);
		Service enemyService = serviceRegistry.getService(enemy);

		try {
			enemyService.writeObject(enemy.createUpdateData());

			dispatcher.dispatch(new SendMessageToUserCommand(playerWhoHits,
					String.format("Zadales przeciwnikowi %d pkt obrazen.",
							damage), MessageType.INFO));
			dispatcher.dispatch(new SendMessageToUserCommand(enemy, String
					.format("Zadano ci %d pkt obrazen.", damage),
					MessageType.INFO));

			if (enemy.isAlive()) {
				dispatcher.dispatch(new SendMessageToUserCommand(enemy,
						"Krwawisz!", MessageType.INFO));

				switchAttackingPlayer(playerWhoHits, enemy);
			} else {
				int expToAdd = enemy.getLevel() * 1000;
				playerWhoHits.addExperience(expToAdd);
				dispatcher.dispatch(new SendMessageToUserCommand(playerWhoHits,
						String.format(
								"Wygrales! Zdobyles %d pkt doswiadczenia.",
								expToAdd), MessageType.INFO));
				dispatcher.dispatch(new SendMessageToUserCommand(enemy,
						"Zginales!", MessageType.INFO));

				endFight(playerWhoHits, enemy);
			}
		} catch (IOException e) {
			throw new FatalException(e, Logger.getLogger(getClass()));
		}
	}

	@Override
	public void runFromFight(ICreature currentCreature, Direction direction) {
		IPlayer currentPlayer = (IPlayer) currentCreature;
		IPlayer enemy = currentPlayer.getEnemy();
		boolean canRun = random.nextInt(2) > 0.5;
		if (!canRun) {
			dispatcher.dispatch(new SendMessageToUserCommand(
					"Nie udalo Ci sie uciec", MessageType.INFO));
			dispatcher.dispatch(new SendMessageToUserCommand(enemy,
					"Przeciwnik probowal uciec lecz mu sie nie udalo",
					MessageType.INFO));

			switchAttackingPlayer(currentPlayer, enemy);
		} else {
			dispatcher.dispatch(new SendMessageToUserCommand(
					"Udalo Ci sie uciec", MessageType.INFO));
			dispatcher.dispatch(new SendMessageToUserCommand(enemy,
					"Przeciwnikowi udalo sie uciec", MessageType.INFO));

			dispatcher.dispatch(new MoveUICommand(direction));
			endFight(currentPlayer, enemy);
		}
	}

	public void switchAttackingPlayer(ICreature from, ICreature to) {
		IPlayer playerFrom = (IPlayer) from;
		IPlayer playerTo = (IPlayer) to;

		dispatcher.dispatch(new SendMessageToUserCommand(playerFrom,
				"Tura przeciwnika", MessageType.INFO));
		dispatcher.dispatch(new SendMessageToUserCommand(playerTo,
				"Twoja tura", MessageType.INFO));

		sendAvailableCommands(playerFrom, AvailableCommands.getInstance()
				.getFightOpponentTurnCommands());
		sendAvailableCommands(playerTo, AvailableCommands.getInstance()
				.getFightYouTurnCommands());
	}

	private void endFight(IPlayer player1, IPlayer player2) {
		playersFightEnd(player1);
		playersFightEnd(player2);
	}

	private void playersFightEnd(IPlayer player) {
		player.setEnemy(null);

		if (player.isAlive()) {
			sendAvailableCommands(player, AvailableCommands.getInstance()
					.getGameCommands());
			try {
				serviceRegistry.getService(player).writeObject(player.createUpdateData());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sendAvailableCommands(player, AvailableCommands.getInstance()
					.getDeadPlayerCommands());
		}
	}

	private void sendAvailableCommands(IPlayer player,
			Collection<UICommand> availableCommands) {
		dispatcher.dispatch(new SendAvailableCommandsCommand(player,
				availableCommands));
	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setRandom(Random random) {
		this.random = random;
	}
}