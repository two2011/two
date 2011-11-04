package pl.edu.agh.two.mud.common.command.factory;

import pl.edu.agh.two.mud.common.command.*;

public interface CommandFactory {
	
	Command create(IParsedCommand parsedCommand);

}
