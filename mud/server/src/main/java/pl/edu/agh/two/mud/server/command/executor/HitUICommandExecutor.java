package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.HitUICommand;
import pl.edu.agh.two.mud.server.world.fight.Fight;

public class HitUICommandExecutor implements CommandExecutor<HitUICommand> {
	private ServiceRegistry serviceRegistry;
	private Fight fight;

	@Override
	public void execute(HitUICommand command) throws CommandExecutingException {
		Service service = serviceRegistry.getCurrentService();
		IPlayer player = serviceRegistry.getPlayer(service);
		fight.hit(player);
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setFight(Fight fight) {
		this.fight = fight;
	}

}
