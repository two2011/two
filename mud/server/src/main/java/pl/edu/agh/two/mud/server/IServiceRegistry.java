package pl.edu.agh.two.mud.server;

import pl.edu.agh.two.mud.common.IPlayer;

public interface IServiceRegistry {

	public Service getService(IPlayer player);

	public IPlayer getPlayer(Service service);

	public void bindPlayerToService(Service service, IPlayer player);

}
