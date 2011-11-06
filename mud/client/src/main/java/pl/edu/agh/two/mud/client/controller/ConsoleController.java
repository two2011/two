package pl.edu.agh.two.mud.client.controller;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.client.command.exception.InvalidCommandParametersException;
import pl.edu.agh.two.mud.client.command.exception.UnknownCommandException;
import pl.edu.agh.two.mud.client.command.parser.ICommandParser;
import pl.edu.agh.two.mud.client.ui.Console;
import pl.edu.agh.two.mud.client.ui.Console.ICommandLineListener;
import pl.edu.agh.two.mud.client.ui.MainWindow;
import pl.edu.agh.two.mud.common.command.IParsedCommand;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;

public class ConsoleController implements ICommandLineListener {

	private static Logger log = Logger.getLogger(ConsoleController.class);

	private Console console;
	private ICommandParser commandParser;
	private Dispatcher dispatcher;

	public ConsoleController(MainWindow window, ICommandParser commandParser,
			Dispatcher dispatcher) {
		this.console = window.getMainConsole();
		this.commandParser = commandParser;
		this.dispatcher = dispatcher;

		// TODO [ksobon] Controller should be registered as listener after user
		// connects with server
		console.addCommandLineListener(this);
	}

	@Override
	public void commandInvoked(String command) {
		handleConsoleCommand(command);
	}

	private void handleConsoleCommand(String command) {
		try {
			IParsedCommand parsedCommand = commandParser.parse(command);
			dispatcher.dispatch(parsedCommand);
		} catch (UnknownCommandException e) {
			console.appendTextToConsole(String.format(
					"Komenda \"%s\" jest nieznana.", e.getCommandName()));
		} catch (InvalidCommandParametersException e) {
			console.appendTextToConsole(String.format(
					"Komenda \"%s\" zostala niepoprawnie uzyta.",
					e.getCommandName()));
			console.appendTextToConsole("Poprawne uzycie:");
			console.appendTextToConsole(getCommand(e.getCommandDefinition()));
		} catch (Throwable t) {
			log.error("Unexpected error during command parsing", t);
		}
	}

	private String getCommand(ICommandDefinition commandDefinition) {
		return String.format("      - %s\n            %s\n",
				getNameWithAliases(commandDefinition.getNames()),
				commandDefinition.getDescription());
	}

	private String getNameWithAliases(Collection<String> names) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = names.iterator();
		builder.append(iterator.next());

		if (names.size() > 1) {
			builder.append(" [");
			while (iterator.hasNext()) {
				builder.append(String.format("%s, ", iterator.next()));
			}
			builder.delete(builder.length() - 3, builder.length() - 1);
			builder.append("]");
		}

		return builder.toString();
	}
}
