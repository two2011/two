package pl.edu.agh.two.mud.server.command;

import static pl.edu.agh.two.mud.server.world.model.Direction.E;
import static pl.edu.agh.two.mud.server.world.model.Direction.N;
import static pl.edu.agh.two.mud.server.world.model.Direction.S;
import static pl.edu.agh.two.mud.server.world.model.Direction.W;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;
import pl.edu.agh.two.mud.server.world.model.Direction;

@Alias({ "idz", "i" })
public class MoveUICommand extends UICommand {

	@OrderedParam(1)
	private String direction;

	@Override
	public String getDescription() {
		return "Komenda przejscia na inne pole. Jej uzycie spowoduje przejscie na inne pole.\n" + "\t idz[i] <n|s|e|w>";
	}

	public MoveUICommand() {
	}

	public MoveUICommand(Direction direction) {
		this.direction = direction.toString();
	}

	public Direction getDirection() {
		String uDirection = direction.toUpperCase();

		if (uDirection.equals("N")) {
			return N;
		} else if (uDirection.equals("S")) {
			return S;
		} else if (uDirection.equals("E")) {
			return E;
		} else if (uDirection.equals("W")) {
			return W;
		}
		return null;
	}

	public MoveUICommand withDirection(String direction) {
		this.direction = direction;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof MoveUICommand) {
			MoveUICommand muic = (MoveUICommand) obj;
			if (direction.equals(muic.direction)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return direction.hashCode();
	}
}
