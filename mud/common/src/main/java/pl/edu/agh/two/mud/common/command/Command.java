package pl.edu.agh.two.mud.common.command;

public abstract class Command {
	
	public String getId() {
		return getClass().getName();
	}
	
	public abstract String getDescription();

}
