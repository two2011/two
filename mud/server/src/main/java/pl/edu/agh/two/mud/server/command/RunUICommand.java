package pl.edu.agh.two.mud.server.command;

import static pl.edu.agh.two.mud.common.world.model.Direction.E;
import static pl.edu.agh.two.mud.common.world.model.Direction.N;
import static pl.edu.agh.two.mud.common.world.model.Direction.S;
import static pl.edu.agh.two.mud.common.world.model.Direction.W;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;
import pl.edu.agh.two.mud.common.world.model.Direction;

@Alias({"wycofaj", "w"})
public class RunUICommand extends UICommand {

	@OrderedParam(1)
	private String direction;
	
	@Override
	public String getDescription() {
		return "Wycofaj sie - proba ucieczki z pola walki\n"
				+ "\t wycofaj[w] <n|s|e|w>";
	}
	
	public Direction getDirection() {
        if (direction.equals("n")) {
            return N;
        } else if (direction.equals("s")) {
            return S;
        } else if (direction.equals("e")) {
            return E;
        } else if (direction.equals("w")) {
            return W;
        }
        return null;
    }

}
