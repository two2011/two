package pl.edu.agh.two.mud.common.command.factory;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.IParsedCommand;

public interface CommandFactory {

	Command create(IParsedCommand parsedCommand);

}
