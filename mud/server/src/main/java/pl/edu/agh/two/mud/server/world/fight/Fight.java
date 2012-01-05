package pl.edu.agh.two.mud.server.world.fight;

import pl.edu.agh.two.mud.common.ICreature;
import pl.edu.agh.two.mud.common.world.model.Direction;

public interface Fight {

	public void startFight(ICreature creatureOne, ICreature creatureTwo);

	public void hit(ICreature creature);

	public void switchAttackingCreature(ICreature from, ICreature to);

	public void runFromFight(ICreature creature, Direction direction);

}