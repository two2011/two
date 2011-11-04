package pl.edu.agh.two.mud.common.command.provider;

import java.util.*;

import pl.edu.agh.two.mud.common.command.*;

public interface CommandProvider {
	
	Command getCommandById(String commandId);
	List<Command> getAvailableCommands();
	

}
