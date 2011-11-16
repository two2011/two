package pl.edu.agh.two.mud.server.executor;

import java.io.IOException;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.LogOutCommand;
import pl.edu.agh.two.mud.server.world.model.Board;

public class LogOutCommandExecutor implements CommandExecutor<LogOutCommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;

	@Override
	public void execute(LogOutCommand command) {
		Service service = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(service);

		if (currentPlayer != null) {
			serviceRegistry.unbindPlayer(currentPlayer);
			try {
				service.writeObject("Zegnaj, " + currentPlayer.getName());
				board.getPlayersPosition(currentPlayer).removePlayer(currentPlayer);
				board.removePlayer(currentPlayer);
				service.writeObject((IPlayer) null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				service.writeObject("Nie jestes zalogowany.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

}
