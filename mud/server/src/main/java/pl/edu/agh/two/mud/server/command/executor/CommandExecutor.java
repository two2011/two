package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.server.command.*;

public interface CommandExecutor<ConcreteCommand extends Command> {
    
    void execute(ConcreteCommand command);

}
