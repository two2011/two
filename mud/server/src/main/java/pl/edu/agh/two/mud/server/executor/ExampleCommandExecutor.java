package pl.edu.agh.two.mud.server.executor;

import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.command.ExampleCommand;

public class ExampleCommandExecutor implements CommandExecutor<ExampleCommand> {

	@Override
	public void execute(ExampleCommand command) {
		System.out
				.println(String
						.format("Uzytkownik uruchomil przyklasowa komende: param1=%s, param2=%d, param3=%d, param4=%s",
								command.getParam1(), command.getParam2(),
								command.getParam3(), command.getParam4()
										.getText()));
	}

}
