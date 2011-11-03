package pl.edu.agh.two.mud.server.command.factory;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.server.command.*;

public interface CommandFactory {
	
	Command create(IParsedCommand parsedCommand);

}
