package pl.edu.agh.two.mud.server.command.executor;

import java.io.IOException;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.LogOutUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommands;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.model.Board;

public class LogOutUICommandExecutor implements CommandExecutor<LogOutUICommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;
	private Dispatcher dispatcher;

	@Override
	public void execute(LogOutUICommand command) {
		Service service = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(service);

		if (currentPlayer != null) {
			serviceRegistry.unbindPlayer(currentPlayer);
			try {
				service.writeObject("Zegnaj, " + currentPlayer.getName());
				dispatcher.dispatch(new SendAvailableCommands(currentPlayer, AvailableCommands.getInstance()
						.getUnloggedCommands()));
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

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}