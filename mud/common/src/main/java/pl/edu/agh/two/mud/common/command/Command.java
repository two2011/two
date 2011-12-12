package pl.edu.agh.two.mud.common.command;

import pl.edu.agh.two.mud.common.command.util.Commands;

public abstract class Command {

	public String getId() {
		return Commands.getId(getClass());
	}

}
