package pl.edu.agh.two.mud.server.world.model;

import pl.edu.agh.two.mud.common.Player;

import java.util.*;

public class Board {

    private String name;
    private String description;

    private int size;

    private Field[][] fields;

    private Map<Player, Field> playersOnFields = new HashMap<Player, Field>();
    private final Field STARTING_FIELD = fields[0][1];

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

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public List<Direction> getPossibleDirections(Field field) {
        List<Direction> result = new ArrayList<Direction>();
        int x = field.getX();
        int y = field.getY();
        if (y > 0 && fields[x][y - 1] != null) {
            result.add(Direction.UP);
        }
        if (y < size && fields[x][y + 1] != null) {
            result.add(Direction.DOWN);
        }
        if (x > 0 && fields[x - 1][y] != null) {
            result.add(Direction.LEFT);
        }
        if (x < size && fields[x + 1][y] != null) {
            result.add(Direction.RIGHT);
        }

        return result;
    }

    public boolean addPlayer(Player player) {
        playersOnFields.put(player, STARTING_FIELD);
        return playersOnFields.containsKey(player);
    }

    public boolean deletePlayer(Player player) {
        playersOnFields.remove(player);
        return playersOnFields.containsKey(player);
    }

    public Set<Player> getPlayers() {
        return playersOnFields.keySet();
    }

    public Player getPlayerByName(String playerName) {
        for (Player player : playersOnFields.keySet()) {
            if (player.getName().equals(playerName))
                return player;
        }
        // todo throw something
        return null;
    }

}
