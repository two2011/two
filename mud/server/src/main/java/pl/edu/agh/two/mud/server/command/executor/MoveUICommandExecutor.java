package pl.edu.agh.two.mud.server.command.executor;

import pl.edu.agh.two.mud.common.*;
import pl.edu.agh.two.mud.common.command.dispatcher.*;
import pl.edu.agh.two.mud.common.command.exception.*;
import pl.edu.agh.two.mud.common.command.executor.*;
import pl.edu.agh.two.mud.common.message.*;
import pl.edu.agh.two.mud.server.*;
import pl.edu.agh.two.mud.server.command.*;
import pl.edu.agh.two.mud.server.world.model.*;

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
                dispatcher.dispatch(new SendMessageToUserCommand(to.getFormattedFieldSummary(), MessageType.INFO));
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
