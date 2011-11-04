package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.server.command.*;

public interface CommandExecutor<C extends Command> {
    
    void execute(C command);

}
