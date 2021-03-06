package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;

@Alias({ "atak", "a" })
public class AttackUICommand extends UICommand {

	@OrderedParam(1)
	private String player;

	@Override
	public String getDescription() {
		return "Komenda atakowania - atakuje wybrana postac o ile nie jest ona w stanie walki\n"
				+ "\t atak[a] <user>";
	}

	public String getPlayer() {
		return player;
	}

}
