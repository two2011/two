package pl.edu.agh.two.mud.server.executor;

import java.io.IOException;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.command.type.Text;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.TalkCommand;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Field;

public class TalkCommandExecutor implements CommandExecutor<TalkCommand> {

	private Board board;
	private IServiceRegistry serviceRegistry;

	@Override
	public void execute(TalkCommand command) {
		Service currentService = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(currentService);
		Text content = command.getContent();
		if (currentPlayer == null) {
			try {
				currentService.writeObject("Jestes niezalogowany.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		Field field = board.getPlayersPosition(currentPlayer);
		if (field != null) {
			for (IPlayer targetPlayer : field.getPlayers()) {
				if (targetPlayer != currentPlayer) {
					try {
						serviceRegistry.getService(targetPlayer).writeObject(
								currentPlayer.getName() + " mowi: "
										+ content.getText());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				serviceRegistry.getService(currentPlayer).writeObject(
						"Nieznany blad");
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
