package pl.edu.agh.two.mud.server.executor;

import pl.edu.agh.two.mud.common.command.executor.*;
import pl.edu.agh.two.mud.server.command.*;
import pl.edu.agh.two.mud.server.world.model.*;

public class ExampleCommandExecutor implements CommandExecutor<ExampleCommand> {
	private Board board;
	
	@Override
	public void execute(ExampleCommand command) {
		board.getName();
		System.out.println(command.getDescription());
	}
	
	public void setBoard(Board board) {
		this.board = board;
	}

}
