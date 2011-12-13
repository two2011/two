package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.command.type.Text;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.WhisperUICommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;

public class WhisperUICommandExecutor implements CommandExecutor<WhisperUICommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;
	private Dispatcher dispatcher;

	@Override
	public void execute(WhisperUICommand command) throws CommandExecutingException {
		Service currentService = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(currentService);

		if (currentPlayer == null) {
			throw new ClientAwareException("Jestes niezalogowany.");
		}
		Text content = command.getContent();

		try {
			Field field = board.getPlayersPosition(currentPlayer);
			if (field != null) {
				IPlayer targetPlayer = field.getPlayerByName(command.getTarget());

				dispatcher.dispatch(new SendMessageToUserCommand(targetPlayer, String.format("%s szepcze: %s",
						currentPlayer.getName(), content.getText()), MessageType.INFO));
			} else {
				throw new ClientAwareException("Nieznany blad.");
			}
		} catch (NoPlayerWithSuchNameException e) {
			throw new ClientAwareException(e, "Nie ma takiego gracza na tym polu.");
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