package pl.edu.agh.two.mud.server.world.model;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.server.world.exception.NoPlayerWithSuchNameException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    String name;
    String description;

    int x;
    int y;

    List<IPlayer> players = new ArrayList<IPlayer>();

    public Field(int x, int y, String name, String description) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.description = description;
    }


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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {

        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean addPlayer(IPlayer player) {
        return players.add(player);
    }

    public boolean removePlayer(IPlayer player) {
        return players.remove(player);
    }

    public List<IPlayer> getPlayers() {
        return players;
    }

    public IPlayer getPlayerByName(String playerName) throws NoPlayerWithSuchNameException {
        for (IPlayer player : players) {
            if (player.getName().equals(playerName))
                return player;
        }
        throw new NoPlayerWithSuchNameException(playerName);
    }


}
