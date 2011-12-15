package pl.edu.agh.two.mud.server.world.fight;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.world.model.Direction;

public interface Fight {
	
	public void startFight(IPlayer playerOne, IPlayer playetTwo);
	public void hit(IPlayer player) throws FatalException;
	public void switchAttackingPlayer(IPlayer from, IPlayer to);
	
	public void runFromFight(IPlayer player, Direction direction);

}