package pl.edu.agh.two.mud.common.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

public class AvailableCommandsMessage implements Serializable {

	private static final long serialVersionUID = -8018790375639623938L;
	private Collection<ICommandDefinition> commandDefinitions;

	public AvailableCommandsMessage(Collection<ICommandDefinition> commandDefinitions) {
		this.commandDefinitions = new ArrayList<ICommandDefinition>(commandDefinitions);
	}

	public Collection<ICommandDefinition> getCommandDefinitions() {
		return commandDefinitions;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof AvailableCommandsMessage) {
			AvailableCommandsMessage acm = (AvailableCommandsMessage) obj;

			Set<ICommandDefinition> cd1 = new HashSet<ICommandDefinition>(commandDefinitions);
			Set<ICommandDefinition> cd2 = new HashSet<ICommandDefinition>(acm.commandDefinitions);
			if (cd1.equals(cd2)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return commandDefinitions.hashCode();
	}

}
