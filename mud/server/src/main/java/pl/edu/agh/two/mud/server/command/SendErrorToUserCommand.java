package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.Command;

public class SendErrorToUserCommand extends Command {

	private String errorMessage;

	public SendErrorToUserCommand(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
