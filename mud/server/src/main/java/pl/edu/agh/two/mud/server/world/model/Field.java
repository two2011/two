package pl.edu.agh.two.mud.server.world.model;

import pl.edu.agh.two.mud.common.Player;

import java.util.ArrayList;
import java.util.List;

public class Field {

    String name;
    String description;

    int x;
    int y;

    List<Player> players = new ArrayList<Player>();

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

    public boolean addPlayer(Player player) {
        return players.add(player);
    }

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }


}
