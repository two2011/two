package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.RefreshUICommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;

public class RefreshUICommandExecutor implements CommandExecutor<RefreshUICommand> {

	private IServiceRegistry serviceRegistry;
	private Board board;
	private Dispatcher dispatcher;

	@Override
	public void execute(RefreshUICommand command) throws CommandExecutingException {
		Service service = serviceRegistry.getCurrentService();
		IPlayer player = serviceRegistry.getPlayer(service);

		dispatcher.dispatch(new SendMessageToUserCommand(board.getPlayersPosition(player).getFormattedFieldSummary(),
				MessageType.INFO));
	}

	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

}
