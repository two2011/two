package pl.edu.agh.two.mud.server.world.fight;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.server.world.model.Direction;

public interface Fight {
	
	public void startFight(IPlayer playerOne, IPlayer playetTwo);
	public IPlayer getCurrentlyAttackingPlayer();
	public void hit(IPlayer player);
	public void switchAttackingPlayer(IPlayer from, IPlayer to);
	
	public void runFromFight(IPlayer player, Direction direction);

}