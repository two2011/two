package pl.edu.agh.two.mud.client.command;

import java.util.Set;

import pl.edu.agh.two.mud.client.command.exception.InvalidCommandParametersException;
import pl.edu.agh.two.mud.client.command.exception.UnavailableCommandException;
import pl.edu.agh.two.mud.client.command.exception.UnknownCommandException;
import pl.edu.agh.two.mud.common.command.IParsedCommand;

public interface ICommandParser {

	void setAvailableCommands(Set<Object> ids);

	IParsedCommand parse(String command) throws UnknownCommandException,
			UnavailableCommandException, InvalidCommandParametersException;

}
