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
import pl.edu.agh.two.mud.server.command.TalkCommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;

public class TalkCommandExecutor implements CommandExecutor<TalkCommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;

	@Override
	public void execute(TalkCommand command) throws CommandExecutingException {
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
					try {
						serviceRegistry.getService(targetPlayer).writeObject(
								String.format("%s mowi: %s",
										currentPlayer.getName(),
										content.getText()));
					} catch (IOException e) {
						throw new FatalException(e,
								Logger.getLogger(getClass()));
					}
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

}
