package pl.edu.agh.two.mud.server.command.dispatcher;

import pl.edu.agh.two.mud.common.command.*;

public interface Dispatcher {
	
	void dispatch(IParsedCommand parsedCommand);

}
