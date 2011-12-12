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
import pl.edu.agh.two.mud.server.command.TalkUICommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;

public class TalkUICommandExecutor implements CommandExecutor<TalkUICommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;
	private Dispatcher dispatcher;

	@Override
	public void execute(TalkUICommand command) throws CommandExecutingException {
		Service currentService = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(currentService);

		if (currentPlayer == null) {
			throw new ClientAwareException("Jestes niezalogowany.");
		}

		Text content = command.getContent();

		Field field = board.getPlayersPosition(currentPlayer);
		if (field != null) {
			for (IPlayer targetPlayer : field.getPlayers()) {
				if (targetPlayer != currentPlayer) {
					dispatcher.dispatch(new SendMessageToUserCommand(String.format("%s mowi: %s",
							currentPlayer.getName(), content.getText()), MessageType.INFO));
				}
			}
		} else {
			throw new ClientAwareException("Nieznany blad.");
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
