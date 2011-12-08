package pl.edu.agh.two.mud.server.world.fight.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.provider.CommandProvider;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.HitCommand;
import pl.edu.agh.two.mud.server.command.LogInCommand;
import pl.edu.agh.two.mud.server.command.RegisterCommand;
import pl.edu.agh.two.mud.server.command.RunCommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommands;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.world.fight.Fight;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Direction;
import pl.edu.agh.two.mud.server.world.model.Field;

public class PlayersFight implements Fight {
	private Dispatcher dispatcher;
	private IServiceRegistry serviceRegistry;
	private CommandProvider commandProvider;
	private Board board;

	@Override
	public void startFight(IPlayer playerOne, IPlayer playerTwo) {
		int whoAttacksFirst = new Random().nextInt(2);
		switch (whoAttacksFirst) {
		case 0:
			switchAttackingPlayer(playerTwo, playerOne);
			break;
		case 1:
			switchAttackingPlayer(playerOne, playerTwo);
			break;
		}

	}

	private void sendAvailableCommands(IPlayer player,
			Class<? extends UICommand>... availableComands) {
		dispatcher.dispatch(new SendAvailableCommands(player, Arrays
				.asList(availableComands)));
	}

	@Override
	public IPlayer getCurrentlyAttackingPlayer() {
		return null;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void hit(IPlayer playerWhoHits) {
		IPlayer enemy = playerWhoHits.getEnemy();
		int damage = new Random().nextInt(4) + 1;
		enemy.subtractHealthPoints(damage);
		Service playerWhoHitsService = serviceRegistry.getService(playerWhoHits);
		Service enemyService = serviceRegistry.getService(enemy);
		try {
			if (enemy.isAlive()) {
				enemyService.writeObject(enemy);
				enemyService.writeObject(String.format("Krwawisz! Zadano ci %d pkt obrazen.", damage));
				playerWhoHitsService.writeObject(String.format("Zadales przeciwnikowi %d pkt obrazen.", damage));
				switchAttackingPlayer(playerWhoHits, enemy);
				
			} else {
				playerWhoHitsService.writeObject(enemy);
				playerWhoHitsService.writeObject("Zginales!");
				
				playerWhoHitsService.writeObject("Wygrales!");
				endFight(playerWhoHits, enemy);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void endFight(IPlayer playerWhoHits, IPlayer enemy) {
		playerWhoHits.setEnemy(null);
		unlockAllCommands(playerWhoHits);
		enemy.setEnemy(null);
		if(enemy.isAlive()){
			unlockAllCommands(enemy);
		}
	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
	
	public void switchAttackingPlayer(IPlayer from, IPlayer to) {
		sendAvailableCommands(from, new Class[] {});
		sendAvailableCommands(to, HitCommand.class, RunCommand.class);
	}
	
	public void unlockAllCommands(IPlayer player){
		List<Class<? extends UICommand>> availableCommands = commandProvider.convertUICommandsToClasses(commandProvider.getUICommandsWithout(LogInCommand.class, RegisterCommand.class));
		sendAvailableCommands(player, availableCommands);
	}

	private void sendAvailableCommands(IPlayer player,
			List<Class<? extends UICommand>> availableCommands) {
		dispatcher.dispatch(new SendAvailableCommands(player, availableCommands));
		
	}

	@Override
	public void runFromFight(IPlayer currentPlayer, Direction direction) {
		Field from = board.getPlayersPosition(currentPlayer);
		
		int fromXPosition = from.getX();
        int fromYPosition = from.getY();

        Field to = null;
        switch (direction) {
            case N:
                to = board.getFields()[fromYPosition - 1][fromXPosition];
                break;
            case S:
                to = board.getFields()[fromYPosition + 1][fromXPosition];
                break;
            case W:
                to = board.getFields()[fromYPosition][fromXPosition - 1];
                break;
            case E:
                to = board.getFields()[fromYPosition][fromXPosition + 1];
                break;

        }
        
        
		IPlayer enemy = currentPlayer.getEnemy();
		boolean canRun = Math.random() > 0.5;
		if(!canRun) {
			// TODO
		}
		else {
			from.removePlayer(currentPlayer);
	        to.addPlayer(currentPlayer);
	        board.setPlayersPosition(currentPlayer, to);
	        dispatcher.dispatch(new SendMessageToUserCommand(to.getFormattedFieldSummary()));
	        currentPlayer.setEnemy(null);
	        enemy.setEnemy(null);
		}
	}
	
	public void setCommandProvider(CommandProvider commandProvider) {
		this.commandProvider = commandProvider;
	}
	
	public void setBoard(Board board) {
        this.board = board;
    }

}