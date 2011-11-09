package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;

@Alias({ "login", "log", "l" })
public class LogInCommand extends UICommand {

	@OrderedParam(1)
	private String name;

	@OrderedParam(2)
	private String password;

	@Override
	public String getDescription() {
		return "Komenda logowania. Jej uzycie spowoduje zalogowanie sie na wybrana postac.\n"
				+ "\t login[ex] <login> <password>";
	}

	public String getLogin() {
		return name;
	}

	public String getPassword() {
		return password;
	}

}
