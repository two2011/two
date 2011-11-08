package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.server.command.annotation.Alias;
import pl.edu.agh.two.mud.server.command.annotation.OrderedParam;

@Alias({"rejestruj"})
public class RegisterCommand extends Command {
	
	@OrderedParam(1)
	private String login;
	
	@OrderedParam(2)
	private String email;
	
	@OrderedParam(3)
	private String race;

	@Override
	public String getDescription() {
		return "rejestruj <login> <email> [rasa]";
	}

	public String getLogin() {
		return login;
	}

	public String getEmail() {
		return email;
	}

	public String getRace() {
		return race;
	}

}
