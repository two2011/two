package pl.edu.agh.two.mud.server.command.provider;

import java.util.*;

import pl.edu.agh.two.mud.server.command.*;

public interface CommandProvider {
	
	Command getCommandById(String commandId);
	List<Command> getAvailableCommands();
	

}
