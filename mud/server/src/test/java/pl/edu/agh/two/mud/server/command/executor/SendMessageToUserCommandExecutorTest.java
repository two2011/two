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

import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.common.message.TextMessage;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;

public class SendMessageToUserCommandExecutorTest {

	private static final String MESSAGE = "message";
	private static final MessageType TYPE = MessageType.INFO;

	private SendMessageToUserCommandExecutor executor;
	private SendMessageToUserCommand command;
	private Service service;

	@Before
	public void preprateTest() {
		command = mock(SendMessageToUserCommand.class);
		when(command.getMessage()).thenReturn(MESSAGE);
		when(command.getMessageType()).thenReturn(TYPE);

		service = mock(Service.class);

		executor = new SendMessageToUserCommandExecutor();
		executor.setService(service);
	}

	@Test
	public void errorDuringSendingMessage() {
		IOException throwable = new IOException();
		try {
			doThrow(throwable).when(service).writeObject(anyString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			executor.execute(command);
			fail("Exception expected");
		} catch (CommandExecutingException e) {
			assertEquals(e.getCause(), throwable);
		}
	}

	@Test
	public void sendingMessage() {
		try {
			executor.execute(command);
			try {
				verify(service).writeObject(new TextMessage(command.getMessage(), command.getMessageType()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
			e.printStackTrace();
		}
	}

}
