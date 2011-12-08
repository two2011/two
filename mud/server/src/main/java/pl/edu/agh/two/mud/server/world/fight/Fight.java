package pl.edu.agh.two.mud.server.world.fight;

import pl.edu.agh.two.mud.common.IPlayer;

public interface Fight {
	
	public void startFight(IPlayer playerOne, IPlayer playetTwo);
	public IPlayer getCurrentlyAttackingPlayer();
	
	public void runFromFight(IPlayer player);

}
