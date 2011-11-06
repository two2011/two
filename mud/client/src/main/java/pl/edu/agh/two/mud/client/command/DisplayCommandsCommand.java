package pl.edu.agh.two.mud.client.command;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.annotation.Alias;

@Alias({ "commands", "help", "?" })
public class DisplayCommandsCommand extends Command {

	@Override
	public String getDescription() {
		return "Pokazuje wszystkie dostepne komendy";
	}

}
