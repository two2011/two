package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.server.command.annotation.*;

@Alias({"test","test2"})
public class TestCommand extends Command {
	
	@OrderedParam(1)
	private String testowy;

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String getTestowy() {
		return testowy;
	}

}
