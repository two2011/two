package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.server.command.annotation.*;
import pl.edu.agh.two.mud.server.command.type.*;

@Alias({ "test", "test2" })
public class TestCommand extends Command {

    @OrderedParam(1)
    private String stringParam;

    @OrderedParam(2)
    private int intParam;

    @OrderedParam(3)
    private Text textParam;

    @Override
    public String getDescription() {
        return null;
    }

    public String getStringParam() {
        return stringParam;
    }

    public int getIntParam() {
        return intParam;
    }

    public Text getTextParam() {
        return textParam;
    }

}
