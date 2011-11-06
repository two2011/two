package pl.edu.agh.two.mud.client.command.parser;

import pl.edu.agh.two.mud.client.command.exception.InvalidCommandParametersException;
import pl.edu.agh.two.mud.client.command.exception.UnknownCommandException;
import pl.edu.agh.two.mud.common.command.IParsedCommand;

public interface ICommandParser {

	IParsedCommand parse(String command) throws UnknownCommandException,
			InvalidCommandParametersException;

}
