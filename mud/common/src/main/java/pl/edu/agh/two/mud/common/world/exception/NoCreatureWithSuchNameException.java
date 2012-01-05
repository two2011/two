package pl.edu.agh.two.mud.common.world.exception;

public class NoCreatureWithSuchNameException extends Throwable {
    public NoCreatureWithSuchNameException(String playerName) {
        super("No player with given name: " + playerName);
    }
}
