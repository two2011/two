package pl.edu.agh.two.mud.common.message;

import java.io.Serializable;
import java.util.Collection;

import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

public class AvailableCommandsMessage implements Serializable {

	private static final long serialVersionUID = -8018790375639623938L;
	private Collection<ICommandDefinition> commandDefinitions;

	public AvailableCommandsMessage(
			Collection<ICommandDefinition> commandDefinitions) {
		this.commandDefinitions = commandDefinitions;
	}

	public Collection<ICommandDefinition> getCommandDefinitions() {
		return commandDefinitions;
	}

}