package pl.edu.agh.two.mud.client.command;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;

@Alias({ "commands", "help", "?" })
public class DisplayCommandsUICommand extends UICommand {

	@Override
	public String getDescription() {
		return "Pokazuje wszystkie dostepne komendy";
	}

}
