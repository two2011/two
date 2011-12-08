package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.RunCommand;
import pl.edu.agh.two.mud.server.world.fight.Fight;

public class RunCommandExecutor implements CommandExecutor<RunCommand> {
	private IServiceRegistry serviceRegistry;
	private Fight fight;

	@Override
	public void execute(RunCommand command) throws CommandExecutingException {
		Service currentService = serviceRegistry.getCurrentService();
		IPlayer currentPlayer = serviceRegistry.getPlayer(currentService);
		fight.runFromFight(currentPlayer);
	}
	
	public void setServiceRegistry(IServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setFight(Fight fight) {
		this.fight = fight;
	}

}
