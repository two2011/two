package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.LogOutCommand;

public class LogOutCommandExecutor implements CommandExecutor<LogOutCommand> {

	private Board board;

	private IServiceRegistry serviceRegistry;

	@Override
	public void execute(LogOutCommand command) throws CommandExecutingException {
		Service service = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(service);
		if (currentPlayer != null) {
			serviceRegistry.unbindPlayer(currentPlayer);
			board.getPlayersPosition(currentPlayer).removeCreature(currentPlayer);
			board.removePlayer(currentPlayer);
		}
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public IServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}
}
