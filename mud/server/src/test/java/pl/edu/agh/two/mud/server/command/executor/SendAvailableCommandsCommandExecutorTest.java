package pl.edu.agh.two.mud.server.command.executor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.IPlayer;
import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.converter.UICommandToDefinitionConverter;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.exception.CommandExecutingException;
import pl.edu.agh.two.mud.common.command.provider.CommandProvider;
import pl.edu.agh.two.mud.common.message.AvailableCommandsMessage;
import pl.edu.agh.two.mud.server.IServiceRegistry;
import pl.edu.agh.two.mud.server.Service;
import pl.edu.agh.two.mud.server.command.SendAvailableCommandsCommand;

import com.google.common.collect.ImmutableList;

public class SendAvailableCommandsCommandExecutorTest {

	private SendAvailableCommandsCommandExecutor executor;
	private SendAvailableCommandsCommand command;
	private IServiceRegistry serviceRegistry;
	private UICommandToDefinitionConverter converter;
	private CommandProvider commandProvider;
	private Service service;

	private UICommand uiCommand;
	private ICommandDefinition uiCommandCommandDefinition;

	@Before
	public void prepareTest() {
		command = mock(SendAvailableCommandsCommand.class);
		serviceRegistry = mock(IServiceRegistry.class);
		converter = mock(UICommandToDefinitionConverter.class);
		commandProvider = mock(CommandProvider.class);
		service = mock(Service.class);
		uiCommand = mock(UICommand.class);
		uiCommandCommandDefinition = mock(ICommandDefinition.class);

		executor = new SendAvailableCommandsCommandExecutor();
		executor.setCommandProvider(commandProvider);
		executor.setConverter(converter);
		executor.setServiceRegistry(serviceRegistry);

		when(serviceRegistry.getService(any(IPlayer.class))).thenReturn(service);
		when(commandProvider.getCommand(uiCommand.getClass())).thenReturn(uiCommand);
		when(converter.convertToCommandDefinition(uiCommand)).thenReturn(uiCommandCommandDefinition);
	}

	@Test
	public void sendingError() {
		IOException throwable = new IOException();
		try {
			doThrow(throwable).when(service).writeObject(anyString());
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
	public void sendSpecifiedAsClasses() {
		when(command.getUICommandClassess()).thenReturn(
				new ImmutableList.Builder<Class<? extends UICommand>>().add(uiCommand.getClass()).build());

		try {
			executor.execute(command);

			try {
				verify(service).writeObject(
						new AvailableCommandsMessage(new ImmutableList.Builder<ICommandDefinition>().add(
								uiCommandCommandDefinition).build()));
			} catch (IOException e) {
				fail("Exception unexpected");
			}
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}
	
	@Test
	public void sendSpecifiedAsCommands() {
		when(command.getUICommandClassess()).thenReturn(null);
		when(command.getUICommands()).thenReturn(
				new ImmutableList.Builder<UICommand>().add(uiCommand).build());

		try {
			executor.execute(command);

			try {
				verify(service).writeObject(
						new AvailableCommandsMessage(new ImmutableList.Builder<ICommandDefinition>().add(
								uiCommandCommandDefinition).build()));
			} catch (IOException e) {
				fail("Exception unexpected");
			}
		} catch (CommandExecutingException e) {
			fail("Exception unexpected");
		}
	}

}
