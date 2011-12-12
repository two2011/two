package pl.edu.agh.two.mud.server.command.executor;

import java.io.IOException;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.LogInUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommands;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;
import pl.edu.agh.two.mud.server.world.model.Board;

public class LogInUICommandExecutor implements CommandExecutor<LogInUICommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;
	private Dispatcher dispatcher;

	@Override
	public void execute(LogInUICommand command) {
		String login = command.getLogin();
		String password = command.getPassword();
		Service service = serviceRegistry.getCurrentService();
		try {
			IPlayer player = board.getPlayerByName(login);
			if (player.getPassword().equals(password)) {
				serviceRegistry.bindPlayerToService(service, player);
				try {
					service.writeObject("Witaj, " + login);
					service.writeObject(player);
					board.setPlayersPosition(player, board.getStartingField());
					board.getStartingField().addPlayer(player);
					service.writeObject(board.getStartingField().getFormattedFieldSummary());

					dispatcher.dispatch(new SendAvailableCommands(player, AvailableCommands.getInstance()
							.getGameCommands()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					service.writeObject("Zle haslo");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (NoPlayerWithSuchNameException e) {
			try {
				service.writeObject("Nie ma takiego gracza");
			} catch (IOException ioEcException) {
				ioEcException.printStackTrace();
			}
		}

	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
