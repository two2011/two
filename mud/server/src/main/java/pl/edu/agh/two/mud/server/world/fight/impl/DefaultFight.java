package pl.edu.agh.two.mud.server.world.fight.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.common.ICreature;
import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.common.world.model.Direction;
import pl.edu.agh.two.mud.common.world.model.Field;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.command.MoveUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommandsCommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.fight.Fight;

public class DefaultFight implements Fight {
	private static final String PLAYER = "gracza";
	private static final String MONSTER = "potwora";

	private Dispatcher dispatcher;
	private IServiceRegistry serviceRegistry;
	private Random random;
	private Board board;

	@Override
	public void startFight(ICreature creatureOne, ICreature creatureTwo) {
		sendMessage(creatureOne, String.format("Zaatkowales %s %s", getType(creatureTwo), creatureTwo.getName()));
		sendMessage(creatureTwo,
				String.format("Zostales zaatakowany przez %s %s", getType(creatureOne), creatureOne.getName()));

		int halfAgilityOne = creatureOne.getAgililty() / 2;
		int halfAgilityTwo = creatureTwo.getAgililty() / 2;

		int initiativeOne = random.nextInt(halfAgilityOne + 1) + halfAgilityOne;
		int initiativeTwo = random.nextInt(halfAgilityTwo + 1) + halfAgilityTwo;

		if (initiativeOne >= initiativeTwo) {
			switchAttackingCreature(creatureTwo, creatureOne);
		} else {
			switchAttackingCreature(creatureOne, creatureTwo);
		}

	}

	@Override
	public void hit(ICreature creatureWhoHits) {
		IPlayer playerWhoHits = castToPlayer(creatureWhoHits);

		ICreature enemyCreature = creatureWhoHits.getEnemy();
		IPlayer enemyPlayer = castToPlayer(enemyCreature);

		// Establish damage points
		int maxDmg = Math.round(creatureWhoHits.getPower() + 1 / 2);
		int damage = random.nextInt(maxDmg + 1);
		double factor = creatureWhoHits.getStrength() / (double) enemyCreature.getStrength();
		damage *= factor;

		int aOne = creatureWhoHits.getAgililty();
		int aTwo = enemyCreature.getAgililty();
		int aDiff = aTwo - aOne;
		int aRandom = random.nextInt(100);

		if (aRandom <= aDiff) {
			damage = 0;
		}

		if (damage > 0) {
			enemyCreature.subtractHealthPoints(damage);

			// Send messages about damage
			sendMessage(enemyCreature, String.format("Zadano ci %d pkt obrazen.", damage));
			sendMessage(creatureWhoHits, String.format("Zadales przeciwnikowi %d pkt obrazen.", damage));

			// Check condition of enemy after attack
			if (enemyCreature.isAlive()) {
				// If still alive
				sendMessage(enemyCreature, "Krwawisz!");
				switchAttackingCreature(creatureWhoHits, enemyCreature);
			} else {
				// If dead

				int expToAdd = enemyCreature.getLevel() * 1000;
				if (playerWhoHits != null) {
					playerWhoHits.addExperience(expToAdd);
				}

				sendMessage(creatureWhoHits, String.format("Wygrales! Zdobyles %d pkt doswiadczenia.", expToAdd));
				sendMessage(enemyCreature, "Zginales!");

				// Removing creatures HACK
				IPlayer player = null;
				if (playerWhoHits != null) {
					player = playerWhoHits;
				} else {
					player = enemyPlayer;
				}
				Field field = board.getPlayersPosition(player);
				field.removeCreature(enemyCreature);

				endFight(creatureWhoHits, enemyCreature);
			}

			// Send update data to enemy
			if (enemyPlayer != null) {
				try {
					serviceRegistry.getService(enemyPlayer).writeObject(enemyPlayer.createUpdateData());
				} catch (IOException e) {
					Logger.getLogger(getClass()).fatal("Error during communication", e);
					return;
				}
			}
		} else {
			sendMessage(creatureWhoHits, "Przeciwnik uniknal ciosu !");
			sendMessage(enemyCreature, "Udalo Ci sie uniknac ciosu !");

			switchAttackingCreature(creatureWhoHits, enemyCreature);
		}

	}

	@Override
	public void runFromFight(ICreature currentCreature, Direction direction) {
		IPlayer currentPlayer = castToPlayer(currentCreature);
		ICreature enemyCreature = currentPlayer.getEnemy();

		// Check whether run is possible
		int runChance = currentCreature.getAgililty() - enemyCreature.getAgililty() + 1;
		int randomed = random.nextInt(Math.abs(runChance) + 1);
		boolean canRun = randomed > runChance / 2;

		if (!canRun) {
			sendMessage(currentCreature, "Nie udalo Ci sie uciec");
			sendMessage(enemyCreature, "Przeciwnik probowal uciec lecz mu sie nie udalo");

			switchAttackingCreature(currentPlayer, enemyCreature);
		} else {
			sendMessage(currentCreature, "Udalo Ci sie uciec");
			sendMessage(enemyCreature, "Przeciwnikowi udalo sie uciec");

			if (currentPlayer != null) {
				dispatcher.dispatch(new MoveUICommand(direction));
			}
			endFight(currentCreature, enemyCreature);
		}
	}

	public void switchAttackingCreature(ICreature from, ICreature to) {
		sendMessage(from, "Tura przeciwnika");
		sendAvailableCommands(from, AvailableCommands.getInstance().getFightOpponentTurnCommands());

		sendMessage(to, "Twoja tura");
		sendAvailableCommands(to, AvailableCommands.getInstance().getFightYouTurnCommands());

		if (castToPlayer(to) == null) {
			hit(to);
		}
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

	public void setBoard(Board board) {
		this.board = board;
	}

	private void endFight(ICreature creature1, ICreature creature2) {
		creatureFightEnd(creature1);
		creatureFightEnd(creature2);
	}

	private void creatureFightEnd(ICreature creature) {
		creature.setEnemy(null);

		if (creature.isAlive()) {
			sendAvailableCommands(creature, AvailableCommands.getInstance().getGameCommands());

			if (creature instanceof IPlayer) {
				IPlayer player = (IPlayer) creature;
				try {
					serviceRegistry.getService(player).writeObject(player.createUpdateData());
				} catch (IOException e) {
					Logger.getLogger(getClass()).fatal("Error during communication", e);
					return;
				}
			}
		} else {
			sendAvailableCommands(creature, AvailableCommands.getInstance().getDeadPlayerCommands());
		}
	}

	private void sendAvailableCommands(ICreature creature, Collection<UICommand> availableCommands) {
		if (creature instanceof IPlayer) {
			dispatcher.dispatch(new SendAvailableCommandsCommand((IPlayer) creature, availableCommands));
		}
	}

	private IPlayer castToPlayer(ICreature creature) {
		if (creature instanceof IPlayer) {
			return (IPlayer) creature;
		}

		return null;
	}

	private void sendMessage(ICreature creature, String message) {
		if (creature instanceof IPlayer) {
			dispatcher.dispatch(new SendMessageToUserCommand((IPlayer) creature, message, MessageType.INFO));
		}
	}

	private String getType(ICreature creature) {
		return castToPlayer(creature) != null ? PLAYER : MONSTER;
	}
}