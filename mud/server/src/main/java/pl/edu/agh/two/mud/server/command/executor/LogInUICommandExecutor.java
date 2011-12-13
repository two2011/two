package pl.edu.agh.two.mud.server.command.executor;

import static pl.edu.agh.two.mud.common.message.MessageType.ERROR;
import static pl.edu.agh.two.mud.common.message.MessageType.INFO;

import java.io.IOException;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.LogInUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommandsCommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;
import pl.edu.agh.two.mud.server.world.model.Board;

public class LogInUICommandExecutor implements CommandExecutor<LogInUICommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;
	private Dispatcher dispatcher;

	@Override
	public void execute(LogInUICommand command) throws FatalException,
			ClientAwareException {
		String login = command.getLogin();
		String password = command.getPassword();
		Service service = serviceRegistry.getCurrentService();
		try {
			IPlayer player = board.getPlayerByName(login);

			if (serviceRegistry.getService(player) != null) {
				throw new ClientAwareException(
						"Nie mozna zalogowac sie na ta postac. Ktos inny nia gra");
			}

			if (player.getPassword().equals(password)) {
				serviceRegistry.bindPlayerToService(service, player);
				try {
					dispatcher.dispatch(new SendMessageToUserCommand("Witaj, "
							+ login, INFO));
					service.writeObject(player);
					board.setPlayersPosition(player, board.getStartingField());
					board.getStartingField().addPlayer(player);

					dispatcher.dispatch(new SendMessageToUserCommand(board
							.getStartingField().getFormattedFieldSummary(),
							INFO));

					dispatcher.dispatch(new SendAvailableCommandsCommand(
							player, AvailableCommands.getInstance()
									.getGameCommands()));
				} catch (IOException e) {
					throw new FatalException(e);
				}
			} else {
				throw new ClientAwareException("Zle haslo!");
			}
		} catch (NoPlayerWithSuchNameException e) {
			dispatcher.dispatch(new SendMessageToUserCommand(
					"Nie ma takiego gracza!", ERROR));
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
