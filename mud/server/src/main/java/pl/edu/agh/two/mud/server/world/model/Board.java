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
            result.add(Direction.N);
        }
        if (y < size - 1 && fields[x][y + 1] != null) {
            result.add(Direction.S);
        }
        if (x > 0 && fields[x - 1][y] != null) {
            result.add(Direction.W);
        }
        if (x < size - 1 && fields[x + 1][y] != null) {
            result.add(Direction.E);
        }

        return result;
    }

    public boolean addPlayer(IPlayer player) {
        playersOnFields.put(player, startingField);
        return playersOnFields.containsKey(player);
    }

    public boolean removePlayer(IPlayer player) {
        playersOnFields.put(player, null);
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

    public Field getPlayersPosition(IPlayer player) {
        return playersOnFields.get(player);
    }

    public void setPlayersPosition(IPlayer player, Field field) {
        playersOnFields.put(player, field);
    }
}
