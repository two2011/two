package pl.edu.agh.two.mud.client.command.registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;

public class CommandDefinitionRegistryTest {

	private final class Command1 {
		private static final String COMMAND_ID = "COMMAND1";
		private static final String COMMAND_NAME_1 = "command1";
		private static final String COMMAND_NAME_2 = "c1";
	}

	private final class Command2 {
		private static final String COMMAND_ID = Command1.COMMAND_ID;
		private static final String COMMAND_NAME_1 = "command2";
	}

	private final class Command3 {
		private static final String COMMAND_ID = "COMMAND3";
		private static final String COMMAND_NAME_1 = Command1.COMMAND_NAME_1;
		private static final String COMMAND_NAME_2 = "c3";
	}

	private final class Command4 {
		private static final String COMMAND_ID = "COMMAND4";
		private static final String COMMAND_NAME_1 = "c4";
	}

	private static ICommandDefinition commandDefinition1;

	private static ICommandDefinition commandDefinition2;

	private static ICommandDefinition commandDefinition3;

	private static ICommandDefinition commandDefinition4;

	private CommandDefinitionRegistry commandDefinitionRegistry;

	@BeforeClass
	public static void setUpTests() {
		commandDefinition1 = mock(ICommandDefinition.class);
		when(commandDefinition1.getId()).thenReturn(Command1.COMMAND_ID);
		when(commandDefinition1.getNames()).thenReturn(
				Arrays.asList(new String[] { Command1.COMMAND_NAME_1,
						Command1.COMMAND_NAME_2 }));

		commandDefinition2 = mock(ICommandDefinition.class);
		when(commandDefinition2.getId()).thenReturn(Command2.COMMAND_ID);
		when(commandDefinition2.getNames()).thenReturn(
				Arrays.asList(new String[] { Command2.COMMAND_NAME_1 }));

		commandDefinition3 = mock(ICommandDefinition.class);
		when(commandDefinition3.getId()).thenReturn(Command3.COMMAND_ID);
		when(commandDefinition3.getNames()).thenReturn(
				Arrays.asList(new String[] { Command3.COMMAND_NAME_1,
						Command3.COMMAND_NAME_2 }));

		commandDefinition4 = mock(ICommandDefinition.class);
		when(commandDefinition4.getId()).thenReturn(Command4.COMMAND_ID);
		when(commandDefinition4.getNames()).thenReturn(
				Arrays.asList(new String[] { Command4.COMMAND_NAME_1 }));
	}

	@Before
	public void before() {
		commandDefinitionRegistry = new CommandDefinitionRegistry();
	}

	@Test
	public void testGetCommandDefinitionById() {
		try {
			commandDefinitionRegistry
					.registerCommandDefinition(commandDefinition1);
			ICommandDefinition commandDefinition = commandDefinitionRegistry
					.getCommandDefinitionById(Command1.COMMAND_ID);
			assertEquals(commandDefinition1, commandDefinition);
		} catch (CommandRegistrationException e) {
			fail("Unexpected exception");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCommandDefinitionByName() {
		try {
			commandDefinitionRegistry
					.registerCommandDefinition(commandDefinition1);
			ICommandDefinition commandDefinition = commandDefinitionRegistry
					.getCommandDefinitionByName(Command1.COMMAND_NAME_1);
			assertEquals(commandDefinition1, commandDefinition);

			commandDefinition = commandDefinitionRegistry
					.getCommandDefinitionByName(Command1.COMMAND_NAME_2);
			assertEquals(commandDefinition1, commandDefinition);
		} catch (CommandRegistrationException e) {
			fail("Unexpected exception");
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCommandDefinitions() {
		try {
			commandDefinitionRegistry
					.registerCommandDefinition(commandDefinition1);
			commandDefinitionRegistry
					.registerCommandDefinition(commandDefinition4);

			Collection<ICommandDefinition> commandDefinitions = commandDefinitionRegistry
					.getCommandDefinitions();
			assertEquals(2, commandDefinitions.size());
			assertTrue(commandDefinitions.contains(commandDefinition1));
			assertTrue(commandDefinitions.contains(commandDefinition4));
		} catch (CommandRegistrationException e) {
			fail("Unexpected exception");
			e.printStackTrace();
		}
	}

	@Test
	public void testRegisterCommandDefinition() {
		// Test registration of null
		try {
			commandDefinitionRegistry.registerCommandDefinition(null);
		} catch (CommandRegistrationException e) {
			fail("Unexpected exception");
			e.printStackTrace();
		}

		// Test appropriate registration
		try {
			commandDefinitionRegistry
					.registerCommandDefinition(commandDefinition1);
		} catch (CommandRegistrationException e) {
			fail("Unexpected exception");
			e.printStackTrace();
		}

		// Test registration of the same ID
		try {
			commandDefinitionRegistry
					.registerCommandDefinition(commandDefinition2);
			fail("Exception expected");
		} catch (CommandRegistrationException e) {
		}

		// Test registration of the same name
		try {
			commandDefinitionRegistry
					.registerCommandDefinition(commandDefinition3);
			fail("Exception expected");
		} catch (CommandRegistrationException e) {
		}
	}
	
	@Test
	public void clearingRegistry() {
		try {
			commandDefinitionRegistry.registerCommandDefinition(commandDefinition1);
			
			commandDefinitionRegistry.clearExternalCommands();
			assertEquals(0, commandDefinitionRegistry.getCommandDefinitions().size());
			
		} catch (CommandRegistrationException e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
		
	}

}
