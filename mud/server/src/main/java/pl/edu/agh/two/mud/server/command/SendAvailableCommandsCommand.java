package pl.edu.agh.two.mud.server.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.UICommand;

public class SendAvailableCommandsCommand extends Command {

	private IPlayer player;

	private Collection<Class<? extends UICommand>> uiCommandClassess;

	private Collection<UICommand> uiCommands = new ArrayList<UICommand>();

	public SendAvailableCommandsCommand(IPlayer player, Class<? extends UICommand>... uiCommandClassess) {
		this.player = player;
		this.uiCommandClassess = Arrays.asList(uiCommandClassess);
	}

	public SendAvailableCommandsCommand(Collection<Class<? extends UICommand>> uiCommandClassess, IPlayer player) {
		this.uiCommandClassess = uiCommandClassess;
		this.player = player;
	}

	public SendAvailableCommandsCommand(IPlayer player, UICommand... uiCommands) {
		this.player = player;
		this.uiCommands = Arrays.asList(uiCommands);
	}

	public SendAvailableCommandsCommand(IPlayer player, Collection<UICommand> uiCommands) {
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

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof SendAvailableCommandsCommand) {
			SendAvailableCommandsCommand sacc = (SendAvailableCommandsCommand) obj;

			if ((player == null && sacc.player == null) || (player != null && player.equals(sacc.player))) {
				if (uiCommandClassess != null && sacc.uiCommandClassess != null) {
					return new HashSet<Class<? extends UICommand>>(uiCommandClassess)
							.equals(new HashSet<Class<? extends UICommand>>(sacc.uiCommandClassess));
				} else if (uiCommands != null && sacc.uiCommands != null) {
					return new HashSet<UICommand>(uiCommands).equals(new HashSet<UICommand>(sacc.uiCommands));
				}
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (player == null ? 0 : player.hashCode());
		hash = 31 * hash + (uiCommandClassess == null ? 0 : uiCommandClassess.hashCode());
		hash = 31 * hash + (uiCommands == null ? 0 : uiCommands.hashCode());
		return hash;
	}
}
