package pl.edu.agh.two.mud.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.client.command.registry.CommandRegistrationException;
import pl.edu.agh.two.mud.client.command.registry.ICommandDefinitionRegistry;
import pl.edu.agh.two.mud.client.ui.Console;
import pl.edu.agh.two.mud.client.ui.MainWindow;
import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.converter.UICommandToDefinitionConverter;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.provider.CommandProvider;
import pl.edu.agh.two.mud.common.message.AvailableCommandsMessage;
import pl.edu.agh.two.mud.common.message.TextMessage;

public class Connection extends Thread {

	private static Logger logger = Logger.getLogger(Connection.class);

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ICommandDefinitionRegistry commandDefinitionRegistry;
	private CommandProvider commandProvider;
	private UICommandToDefinitionConverter converter;
	private MainWindow mainWindow;

	public void connect(String host, int port) throws IOException {
		socket = new Socket(host, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}

	public Object read() throws ClassNotFoundException, IOException {
		return in.readObject();
	}

	public void send(Object objectToSend) throws IOException {
		out.writeObject(objectToSend);
	}

	@Override
	public void run() {
		try {
			while (true) {
				Object object;
				object = in.readObject();

				// Message handling
				if (object instanceof AvailableCommandsMessage) {
					AvailableCommandsMessage command = (AvailableCommandsMessage) object;

					// Clear registry
					commandDefinitionRegistry.clearExternalCommands();

					// Register internal commands

					for (UICommand uiCommand : commandProvider.getUICommands()) {
						try {
							commandDefinitionRegistry.registerCommandDefinition(converter
									.convertToCommandDefinition(uiCommand));
						} catch (CommandRegistrationException e) {
							logger.error("Cannot register internal command", e);
						}
					}

					// Register external commands
					for (ICommandDefinition commandDefinition : command.getCommandDefinitions()) {
						try {
							commandDefinitionRegistry.registerCommandDefinition(commandDefinition);
						} catch (CommandRegistrationException e) {
							logger.error("Cannot register  external command", e);
						}
					}

				} else if (object instanceof IPlayer) {
					mainWindow.getPlayerPanel().updateHero((IPlayer) object);
				} else if (object instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) object;
					Console console = mainWindow.getMainConsole();
					switch (textMessage.getType()) {
						case ERROR:
							console.appendTextToConsole(String.format("[ERROR] %s", textMessage.getContent()));
							break;
						case INFO:
							console.appendTextToConsole(textMessage.getContent());
							break;
					}
				} else if (object instanceof String) {
					mainWindow.getMainConsole().appendTextToConsole((String) object);
				} else if (object == null) {
					mainWindow.getPlayerPanel().updateHero(null);
				}

			}
		} catch (IOException e) {
			logger.fatal("Fatal exception while reading from socket", e);
		} catch (ClassNotFoundException e) {
			logger.fatal("Cannot deserialize class obtained from socket", e);
		}
	}

	public void setCommandDefinitionRegistry(ICommandDefinitionRegistry commandDefinitionRegistry) {
		this.commandDefinitionRegistry = commandDefinitionRegistry;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void setCommandProvider(CommandProvider commandProvider) {
		this.commandProvider = commandProvider;
	}

	public void setConverter(UICommandToDefinitionConverter converter) {
		this.converter = converter;
	}

}
