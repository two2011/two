package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.common.command.annotation.*;
import pl.edu.agh.two.mud.common.command.type.*;

// Pamietacje o wrzuceniu waszych komend do applicationContext.xml jako beany, to jest przyklad i nie wrzucalem do springa.

@Alias({"example", "ex"})
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
	
	public String getPlayerLogin() {
		return playerLogin;
	}
	
	public int getMoney() {
		return money;
	}
	
	public Text getChatText() {
		return chatText;
	}

}
