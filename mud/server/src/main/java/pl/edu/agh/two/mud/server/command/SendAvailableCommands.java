package pl.edu.agh.two.mud.server.command;

import java.util.Collection;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.UICommand;

public class SendAvailableCommands extends Command {

	private IPlayer player;

	private Collection<Class<? extends UICommand>> uiCommands;

	public SendAvailableCommands(IPlayer player,
			Collection<Class<? extends UICommand>> uiCommands) {
		this.player = player;
		this.uiCommands = uiCommands;
	}

	public IPlayer getPlayer() {
		return player;
	}

	public Collection<Class<? extends UICommand>> getUiCommands() {
		return uiCommands;
	}

}
