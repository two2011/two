package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.server.command.annotation.*;
import pl.edu.agh.two.mud.server.command.type.*;

@Alias({ "test", "test2" })
public class TestCommand extends Command {

    @OrderedParam(1)
    private String testowy;

    @OrderedParam(2)
    private int misiek;

    @OrderedParam(3)
    private Text zuzia;

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTestowy() {
        return testowy;
    }

    public int getMisiek() {
        return misiek;
    }

    public Text getZuzia() {
        return zuzia;
    }

}
