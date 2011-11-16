package pl.edu.agh.two.mud.server.executor;

import java.io.IOException;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.command.type.Text;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.WhisperCommand;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;

public class WhisperCommandExecutor implements CommandExecutor<WhisperCommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;

	@Override
	public void execute(WhisperCommand command) {
		Service currentService = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(currentService);
		Text content = command.getContent();
		try {
			Field field = board.getPlayersPosition(currentPlayer);
			if (field != null) {
				IPlayer targetPlayer = field.getPlayerByName(command
						.getTarget());
				try {
					serviceRegistry.getService(targetPlayer).writeObject(
							currentPlayer.getName() + " szepcze: "
									+ content.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					serviceRegistry.getService(currentPlayer).writeObject(
							"Nieznany blad");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (NoPlayerWithSuchNameException e) {
			try {
				currentService.writeObject("Nie ma takiego gracza na tym polu");
			} catch (IOException e1) {
				e1.printStackTrace();
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
