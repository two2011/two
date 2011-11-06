package pl.edu.agh.two.mud.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.client.command.registry.ICommandDefinitionRegistry;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

public class Connection extends Thread {

	private static Logger logger = Logger.getLogger(Connection.class);

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ICommandDefinitionRegistry commandDefinitionRegistry;

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
		while (true) {
			try {
				Object object = in.readObject();
				
				// Message handling
				// TODO Should be more generic !
				if (object instanceof ICommandDefinition) {
					commandDefinitionRegistry.registerCommandDefinition((ICommandDefinition)object);
				}
				
			} catch (Exception e) {
				logger.error("Error during reading message from server", e);
				e.printStackTrace();
			}
		}
	}
	
	public void setCommandDefinitionRegistry(
			ICommandDefinitionRegistry commandDefinitionRegistry) {
		this.commandDefinitionRegistry = commandDefinitionRegistry;
	}

}
