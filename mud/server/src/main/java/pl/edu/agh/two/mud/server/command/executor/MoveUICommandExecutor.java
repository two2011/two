package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.exception.FatalException;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.MoveUICommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Direction;
import pl.edu.agh.two.mud.server.world.model.Field;

import java.io.IOException;

public class MoveUICommandExecutor implements CommandExecutor<MoveUICommand> {

    private Board board;
    private IServiceRegistry serviceRegistry;
    private Dispatcher dispatcher;

    @Override
    public void execute(MoveUICommand command) throws FatalException {

        Service service = serviceRegistry.getCurrentService();
        IPlayer player = serviceRegistry.getPlayer(service);
        if (player != null) {
            Direction direction = command.getDirection();
            Field from = board.getPlayersPosition(player);

            if (!isDirectionValid(direction, from)) {
                dispatcher.dispatch(new SendMessageToUserCommand("Nie mozesz tam isc!", MessageType.INFO));
            } else {

                int fromXPosition = from.getX();
                int fromYPosition = from.getY();

                Field to = null;
                switch (direction) {
                    case N:
                        to = board.getFields()[fromYPosition - 1][fromXPosition];
                        break;
                    case S:
                        to = board.getFields()[fromYPosition + 1][fromXPosition];
                        break;
                    case W:
                        to = board.getFields()[fromYPosition][fromXPosition - 1];
                        break;
                    case E:
                        to = board.getFields()[fromYPosition][fromXPosition + 1];
                        break;

                }
                from.removePlayer(player);
                to.addPlayer(player);
                board.setPlayersPosition(player, to);

                try {
                    service.writeObject(to.getFormattedFieldSummary());
                } catch (IOException e) {
                    throw new FatalException(e);
                }
            }
        } else {
            dispatcher.dispatch(new SendMessageToUserCommand("Nie mozesz uzyc teraz tej komendy!", MessageType.INFO));
        }
    }

    private boolean isDirectionValid(Direction direction, Field from) {
        return direction != null && board.getPossibleDirections(from).contains(direction);
    }

    public void setServiceRegistry(IServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

}
