package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;

@Alias({"uderz", "u"})
public class HitCommand extends UICommand{

	@Override
	public String getDescription() {
		return "U�ycie komendy spowoduje wykonanie ataku na osobie z ktora aktualnie walczymy";
	}
	
	
	

}
