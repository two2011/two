package pl.edu.agh.two.mud.server.world.model;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;

import java.util.*;

public class Board {

    private String name;
    private String description;

    private int size;

    private Field[][] fields;

    private Map<IPlayer, Field> playersOnFields = new HashMap<IPlayer, Field>();
    private Field startingField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public Field getStartingField() {
        return startingField;
    }

    public void setStartingField(Field startingField) {
        this.startingField = startingField;
    }

    public List<Direction> getPossibleDirections(Field field) {
        List<Direction> result = new ArrayList<Direction>();
        int x = field.getX();
        int y = field.getY();
        if (y > 0 && fields[x][y - 1] != null) {
            result.add(Direction.UP);
        }
        if (y < size - 1 && fields[x][y + 1] != null) {
            result.add(Direction.DOWN);
        }
        if (x > 0 && fields[x - 1][y] != null) {
            result.add(Direction.LEFT);
        }
        if (x < size - 1 && fields[x + 1][y] != null) {
            result.add(Direction.RIGHT);
        }

        return result;
    }

    // todo move to CommandExecutor
//    public Field changeField(Command command) {
//        Player player = command.getPlayer;
//        Direction direction = command.direction;
//        Field from = player.getField();
//        int fromXPosition = from.getX();
//        int fromYPosition = from.getY();
//
//        Field to = null;
//        switch (direction) {
//            case UP:
//                to = board.getFields()[fromXPosition][fromYPosition - 1];
//                break;
//            case DOWN:
//                to = board.getFields()[fromXPosition][fromYPosition + 1];
//                break;
//            case LEFT:
//                to = board.getFields()[fromXPosition - 1][fromYPosition];
//                break;
//            case RIGHT:
//                to = board.getFields()[fromXPosition + 1][fromYPosition];
//                break;
//        }
//        from.removePlayer(player);
//        to.addPlayer(player);
//        player.setField(to);
//        return to;
//    }

    public boolean addPlayer(IPlayer player) {
        playersOnFields.put(player, startingField);
        return playersOnFields.containsKey(player);
    }

    public boolean removePlayer(IPlayer player) {
        playersOnFields.remove(player);
        return playersOnFields.containsKey(player);
    }

    public Set<IPlayer> getPlayers() {
        return playersOnFields.keySet();
    }

    public IPlayer getPlayerByName(String playerName) throws NoPlayerWithSuchNameException {
        for (IPlayer player : playersOnFields.keySet()) {
            if (player.getName().equals(playerName))
                return player;
        }
        throw new NoPlayerWithSuchNameException(playerName);
    }

}
