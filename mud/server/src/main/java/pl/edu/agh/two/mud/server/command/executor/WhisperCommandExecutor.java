package pl.edu.agh.two.mud.server.command.executor;

import java.io.IOException;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.command.type.Text;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.WhisperCommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;

public class WhisperCommandExecutor implements CommandExecutor<WhisperCommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;

	@Override
	public void execute(WhisperCommand command)
			throws CommandExecutingException {
		Service currentService = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(currentService);
		
		if (currentPlayer == null) {
			throw new ClientAwareException("Jestes niezalogowany.");
		}		
		Text content = command.getContent();

		try {
			Field field = board.getPlayersPosition(currentPlayer);
			if (field != null) {
				IPlayer targetPlayer = field.getPlayerByName(command
						.getTarget());

				// TODO [ksobon] There should be a command for sending message
				// to user ! Otherwise IOException need to be handled
				// appropriately !
				try {
					serviceRegistry.getService(targetPlayer)
							.writeObject(
									String.format("%s szepcze: %s",
											currentPlayer.getName(),
											content.getText()));
				} catch (IOException e) {
					throw new FatalException(e, Logger.getLogger(getClass()));
				}
			} else {
				throw new ClientAwareException("Nieznany blad.");
			}
		} catch (NoPlayerWithSuchNameException e) {
			throw new ClientAwareException(e,
					"Nie ma takiego gracza na tym polu.");
		}
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

}
