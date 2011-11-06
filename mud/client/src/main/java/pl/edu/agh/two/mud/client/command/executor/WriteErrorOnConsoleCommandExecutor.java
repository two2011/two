package pl.edu.agh.two.mud.client.command.executor;

import pl.edu.agh.two.mud.client.command.WriteErrorOnConsoleCommand;
import pl.edu.agh.two.mud.client.ui.Console;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;

public class WriteErrorOnConsoleCommandExecutor implements
		CommandExecutor<WriteErrorOnConsoleCommand> {

	private Console console;

	public void setConsole(Console console) {
		this.console = console;
	}

	@Override
	public void execute(WriteErrorOnConsoleCommand command) {
		console.appendTextToConsole(command.getErrorMessage());
	}

}
