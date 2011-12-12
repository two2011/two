package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.message.MessageType;

public class SendMessageToUserCommand extends Command {

	private String message;

	private MessageType type;

	public SendMessageToUserCommand(String message, MessageType type) {
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public MessageType getType() {
		return type;
	}

}
