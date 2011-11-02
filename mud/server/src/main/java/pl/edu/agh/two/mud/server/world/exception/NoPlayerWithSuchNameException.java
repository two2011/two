package pl.edu.agh.two.mud.server.world.exception;

public class NoPlayerWithSuchNameException extends Throwable {
    public NoPlayerWithSuchNameException(String playerName) {
        super("No player with given name: " + playerName);
    }
}
