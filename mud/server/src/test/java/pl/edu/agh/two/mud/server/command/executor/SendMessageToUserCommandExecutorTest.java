package pl.edu.agh.two.mud.server.command.executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.common.message.TextMessage;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;

public class SendMessageToUserCommandExecutorTest {

	private static final String MESSAGE = "message";
	private static final MessageType TYPE = MessageType.INFO;

	private SendMessageToUserCommandExecutor executor;
	private SendMessageToUserCommand command;
	private IServiceRegistry serviceRegistry;

	@Before
	public void preprateTest() {
		command = mock(SendMessageToUserCommand.class);
		when(command.getMessage()).thenReturn(MESSAGE);
		when(command.getType()).thenReturn(TYPE);

		serviceRegistry = mock(IServiceRegistry.class);

		executor = new SendMessageToUserCommandExecutor();
		executor.setServiceRegistry(serviceRegistry);
	}

	@Test
	public void errorDuringSendingMessage() {
		Service currentService = mock(Service.class);

		when(command.getPlayer()).thenReturn(null);
		when(serviceRegistry.getCurrentService()).thenReturn(currentService);

		IOException throwable = new IOException();
		try {
			doThrow(throwable).when(currentService).writeObject(anyString());
		} catch (IOException e) {
		}

		try {
			executor.execute(command);
			fail("Exception expected");
		} catch (CommandExecutingException e) {
			assertEquals(e.getCause(), throwable);
		}
	}

	@Test
	public void sendingMessageToCurrentUser() {
		Service currentService = mock(Service.class);

		when(command.getPlayer()).thenReturn(null);
		when(serviceRegistry.getCurrentService()).thenReturn(currentService);

		try {
			executor.execute(command);
			try {
				verify(currentService).writeObject(new TextMessage(command.getMessage(), command.getType()));
			} catch (IOException e) {
			}
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
			e.printStackTrace();
		}
	}

	@Test
	public void sendingMessageToSomeUser() {
		Service service = mock(Service.class);
		IPlayer somePlayer = mock(IPlayer.class);

		when(command.getPlayer()).thenReturn(somePlayer);
		when(serviceRegistry.getService(somePlayer)).thenReturn(service);

		try {
			executor.execute(command);
			try {
				verify(service).writeObject(new TextMessage(command.getMessage(), command.getType()));
			} catch (IOException e) {
			}
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
			e.printStackTrace();
		}
	}

}
