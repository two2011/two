package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.ServiceRegistry;
import pl.edu.agh.two.mud.server.command.HitCommand;
import pl.edu.agh.two.mud.server.world.fight.Fight;

public class HitCommandExecutor implements CommandExecutor<HitCommand> {
	private ServiceRegistry serviceRegistry;
	private Fight fight;
	
	@Override
	public void execute(HitCommand command) throws CommandExecutingException {
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
