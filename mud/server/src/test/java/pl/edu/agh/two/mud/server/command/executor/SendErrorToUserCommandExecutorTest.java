package pl.edu.agh.two.mud.server.command.executor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.SendErrorToUserCommand;
import pl.edu.agh.two.mud.server.command.executor.SendErrorToUserCommandExecutor;

public class SendErrorToUserCommandExecutorTest {

	private static final String ERROR = "error";

	private SendErrorToUserCommandExecutor executor;
	private SendErrorToUserCommand command;
	private Service service;

	@Before
	public void preprateTest() {
		command = mock(SendErrorToUserCommand.class);
		when(command.getErrorMessage()).thenReturn(ERROR);

		service = mock(Service.class);

		executor = new SendErrorToUserCommandExecutor();
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
				verify(service).writeObject(command.getErrorMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
			e.printStackTrace();
		}
	}

}
