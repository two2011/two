package pl.edu.agh.two.mud.common.command.executor;

import pl.edu.agh.two.mud.common.command.*;

public interface CommandExecutor<C extends Command> {
    
    void execute(C command);

}
