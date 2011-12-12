package pl.edu.agh.two.mud.common.message;

import java.io.Serializable;

public class TextMessage implements Serializable {

	private static final long serialVersionUID = 8334034506898700578L;
	private String content;
	private MessageType type;

	public TextMessage(String content, MessageType type) {
		super();
		this.content = content;
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public MessageType getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof TextMessage) {
			TextMessage message = (TextMessage) obj;
			if (content.equals(message.content) && type.equals(message.type)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return String.format("%s|%s", content, type.toString()).hashCode();
	}

}
