package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.message.MessageType;

public class SendMessageToUserCommand extends Command {

	private String message;

	private MessageType messageType;

	public SendMessageToUserCommand(String errorMessage, MessageType messageType) {
		this.message = errorMessage;
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	public MessageType getMessageType() {
		return messageType;
	}

}
