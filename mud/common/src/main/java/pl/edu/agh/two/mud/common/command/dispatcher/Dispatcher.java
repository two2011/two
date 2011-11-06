package pl.edu.agh.two.mud.common.command.dispatcher;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.IParsedCommand;

public interface Dispatcher {

	void dispatch(IParsedCommand parsedCommand);

	void dispatch(Command command);

}
