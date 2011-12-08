package pl.edu.agh.two.mud.server.command.executor;

import java.io.IOException;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.RefreshUICommand;
import pl.edu.agh.two.mud.server.world.model.Board;

public class RefreshUICommandExecutor implements
		CommandExecutor<RefreshUICommand> {

	private IServiceRegistry serviceRegistry;

	private Board board;

	@Override
	public void execute(RefreshUICommand command)
			throws CommandExecutingException {

		Service service = serviceRegistry.getCurrentService();
		IPlayer player = serviceRegistry.getPlayer(service);

		try {
			service.writeObject(board.getPlayersPosition(player)
					.getFormattedFieldSummary());
		} catch (IOException e) {
			throw new FatalException(e);
		}

	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}
