package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.common.command.annotation.*;
import pl.edu.agh.two.mud.common.command.type.*;

// REMEMBER TO DEFINE YOUR COMMANDS AS SPRING BEANs, THIS IS AN EXAMPLE HOW TO USE COMMANDS SO IT IS NOT DEFINED IN SPRING
public class ExampleCommand extends Command {

	@OrderedParam(1)
	private String playerLogin;
	
	@OrderedParam(2)
	private int money;
	
	@OrderedParam(3)
	private Text chatText;
	
	@Override
	public String getDescription() {
		return "TUTAJ WRZUCAC USAGE";
	}

}
