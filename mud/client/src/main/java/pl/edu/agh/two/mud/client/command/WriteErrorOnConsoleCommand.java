package pl.edu.agh.two.mud.client.command;

import pl.edu.agh.two.mud.common.command.Command;

public class WriteErrorOnConsoleCommand extends Command {

	private String errorMessage;

	public WriteErrorOnConsoleCommand(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	};

}
