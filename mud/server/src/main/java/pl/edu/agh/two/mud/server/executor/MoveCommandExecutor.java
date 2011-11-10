package pl.edu.agh.two.mud.server.executor;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.MoveCommand;
import pl.edu.agh.two.mud.server.world.exception.NoSuchDirection;
import pl.edu.agh.two.mud.server.world.model.Board;
import pl.edu.agh.two.mud.server.world.model.Direction;
import pl.edu.agh.two.mud.server.world.model.Field;

import java.io.IOException;

public class MoveCommandExecutor implements CommandExecutor<MoveCommand> {

    private Board board;
    private IServiceRegistry serviceRegistry;

    @Override
    public void execute(MoveCommand command) {

        Service service = serviceRegistry.getCurrentService();
        Direction direction = command.getDirection();
        if (direction == null) {
            try {
                service.writeObject("Nie mozesz tam isc!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        IPlayer player = serviceRegistry.getPlayer(service);

        Field from = board.getPlayersPosition(player);
        int fromXPosition = from.getX();
        int fromYPosition = from.getY();

        Field to = null;
        switch (direction) {
            case N:
                to = board.getFields()[fromXPosition][fromYPosition - 1];
                break;
            case S:
                to = board.getFields()[fromXPosition][fromYPosition + 1];
                break;
            case W:
                to = board.getFields()[fromXPosition - 1][fromYPosition];
                break;
            case E:
                to = board.getFields()[fromXPosition + 1][fromYPosition];
                break;

        }
        from.removePlayer(player);
        to.addPlayer(player);
        board.setPlayersPosition(player, to);
    }


}
