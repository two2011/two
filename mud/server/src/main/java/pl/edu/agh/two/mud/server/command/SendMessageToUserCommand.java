package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.message.MessageType;

public class SendMessageToUserCommand extends Command {

	private IPlayer player;

	private String message;

	private MessageType type;

	/**
	 * Constructor. Message will be send to current user.
	 * 
	 * @param message
	 *            message to send
	 * @param type
	 *            message type
	 */
	public SendMessageToUserCommand(String message, MessageType type) {
		this.message = message;
		this.type = type;
	}

	/**
	 * Constructor.
	 * 
	 * @param player
	 *            player to which message should be send
	 * @param message
	 *            message to send
	 * @param type
	 *            message type
	 */
	public SendMessageToUserCommand(IPlayer player, String message, MessageType type) {
		this.player = player;
		this.message = message;
		this.type = type;
	}

	public IPlayer getPlayer() {
		return player;
	}

	public String getMessage() {
		return message;
	}

	public MessageType getType() {
		return type;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (object instanceof SendMessageToUserCommand) {
			SendMessageToUserCommand smtc = (SendMessageToUserCommand) object;
			if (((player == null && smtc.player == null) || (player != null && player.equals(smtc.player)))
					&& message.equals(smtc.message) && type.equals(smtc.type)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (player == null ? 0 : player.hashCode());
		hash = 31 * hash + (message == null ? 0 : message.hashCode());
		hash = 31 * hash + (type == null ? 0 : type.hashCode());
		return hash;
	}
	
	@Override
	public String toString() {
		return String.format("SendMessageToUserCommand(%s, %s, %s)", player, message, type);
	}

}
