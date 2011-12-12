package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.LogInUICommand;
import pl.edu.agh.two.mud.server.command.SendAvailableCommands;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.util.AvailableCommands;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;
import pl.edu.agh.two.mud.server.world.model.Board;

import java.io.IOException;

public class LogInUICommandExecutor implements CommandExecutor<LogInUICommand> {

    private Board board;
    private IServiceRegistry serviceRegistry;
    private Dispatcher dispatcher;

    @Override
    public void execute(LogInUICommand command) throws FatalException {
        String login = command.getLogin();
        String password = command.getPassword();
        Service service = serviceRegistry.getCurrentService();
        try {
            IPlayer player = board.getPlayerByName(login);
            if (player.getPassword().equals(password)) {
                serviceRegistry.bindPlayerToService(service, player);
                try {
                    dispatcher.dispatch(new SendMessageToUserCommand("Witaj, " + login, MessageType.INFO));
                    service.writeObject(player);
                    board.setPlayersPosition(player, board.getStartingField());
                    board.getStartingField().addPlayer(player);
                    service.writeObject(board.getStartingField().getFormattedFieldSummary());

                    dispatcher.dispatch(new SendAvailableCommands(player, AvailableCommands.getInstance().getGameCommands()));
                } catch (IOException e) {
                    throw new FatalException(e);
                }
            } else {
                dispatcher.dispatch(new SendMessageToUserCommand("Zle haslo!", MessageType.ERROR));
            }
        } catch (NoPlayerWithSuchNameException e) {
            dispatcher.dispatch(new SendMessageToUserCommand("Nie ma takiego gracza!", MessageType.ERROR));
        }

    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setServiceRegistry(IServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

}
