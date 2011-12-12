package pl.edu.agh.two.mud.server.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.UICommand;

public class SendAvailableCommands extends Command {

	private IPlayer player;

	private Collection<Class<? extends UICommand>> uiCommandClassess;

	private Collection<UICommand> uiCommands = new ArrayList<UICommand>();

	public SendAvailableCommands(IPlayer player, Class<? extends UICommand>... uiCommandClassess) {
		this.player = player;
		this.uiCommandClassess = Arrays.asList(uiCommandClassess);
	}

	public SendAvailableCommands(Collection<Class<? extends UICommand>> uiCommandClassess, IPlayer player) {
		this.uiCommandClassess = uiCommandClassess;
		this.player = player;
	}

	public SendAvailableCommands(IPlayer player, UICommand... uiCommands) {
		this.player = player;
		this.uiCommands = Arrays.asList(uiCommands);
	}
	
	public SendAvailableCommands(IPlayer player, Collection<UICommand> uiCommands) {
		this.player = player;
		this.uiCommands = uiCommands;
	}

	public IPlayer getPlayer() {
		return player;
	}

	public Collection<Class<? extends UICommand>> getUICommandClassess() {
		return uiCommandClassess;
	}

	public Collection<UICommand> getUICommands() {
		return uiCommands;
	}
}
