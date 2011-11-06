package pl.edu.agh.two.mud.client.command.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.client.command.exception.CommandParsingException;
import pl.edu.agh.two.mud.client.command.exception.InvalidCommandParametersException;
import pl.edu.agh.two.mud.client.command.exception.UnknownCommandException;
import pl.edu.agh.two.mud.client.command.registry.ICommandDefinitionRegistry;
import pl.edu.agh.two.mud.common.command.IParsedCommand;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.definition.ICommandParameterDefinition;

public class CommandParserTest {

	private final class Command1 {
		private static final String COMMAND_ID = "COMMAND1";
		private static final String COMMAND_NAME_1 = "command1";
		private static final String COMMAND_NAME_2 = "c1";
		private static final String COMMAND_PARAM_1_NAME = "p1";
		private static final String COMMAND_PARAM_1_REGEXP = "^.*$";
		private static final String COMMAND_PARAM_2_NAME = "p2";
		private static final String COMMAND_PARAM_2_REGEXP = "^[0-9]$";
		private static final String COMMAND_PARAM_3_NAME = "p3";
	}

	private ICommandDefinition commandDefinition1;

	private CommandParser commandParser;

	@Before
	public void setUp() {
		commandDefinition1 = mock(ICommandDefinition.class);
		when(commandDefinition1.getId()).thenReturn(Command1.COMMAND_ID);
		when(commandDefinition1.getNames()).thenReturn(
				Arrays.asList(new String[] { Command1.COMMAND_NAME_1,
						Command1.COMMAND_NAME_2 }));

		ICommandParameterDefinition parameterDefinition1 = mock(ICommandParameterDefinition.class);
		when(parameterDefinition1.getName()).thenReturn(
				Command1.COMMAND_PARAM_1_NAME);
		when(parameterDefinition1.getRegExp()).thenReturn(
				Command1.COMMAND_PARAM_1_REGEXP);

		ICommandParameterDefinition parameterDefinition2 = mock(ICommandParameterDefinition.class);
		when(parameterDefinition2.getName()).thenReturn(
				Command1.COMMAND_PARAM_2_NAME);
		when(parameterDefinition2.getRegExp()).thenReturn(
				Command1.COMMAND_PARAM_2_REGEXP);

		ICommandParameterDefinition parameterDefinition3 = mock(ICommandParameterDefinition.class);
		when(parameterDefinition3.getName()).thenReturn(
				Command1.COMMAND_PARAM_3_NAME);
		when(parameterDefinition3.getRegExp()).thenReturn(null);

		List<ICommandParameterDefinition> parameterDefinitions = new ArrayList<ICommandParameterDefinition>(
				1);
		parameterDefinitions.add(parameterDefinition1);
		parameterDefinitions.add(parameterDefinition2);
		parameterDefinitions.add(parameterDefinition3);

		when(commandDefinition1.getParameters()).thenReturn(
				parameterDefinitions);

		ICommandDefinitionRegistry commandDefinitionRegistry = mock(ICommandDefinitionRegistry.class);
		when(commandDefinitionRegistry.getCommandDefinitions()).thenReturn(
				Arrays.asList(new ICommandDefinition[] { commandDefinition1 }));

		when(
				commandDefinitionRegistry
						.getCommandDefinitionByName(Command1.COMMAND_NAME_1))
				.thenReturn(commandDefinition1);
		when(
				commandDefinitionRegistry
						.getCommandDefinitionByName(Command1.COMMAND_NAME_2))
				.thenReturn(commandDefinition1);

		commandParser = new CommandParser(commandDefinitionRegistry);
	}

	@Test
	public void testParseNull() {
		// Test null input
		try {
			commandParser.parse(null);
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		} catch (CommandParsingException e) {
			e.printStackTrace();
			fail("Exception unexpected");
		}
	}

	@Test
	public void testParseUndefined() {
		// Test: undefined
		String[] commands = new String[] { "command", "command p1",
				"command p1 p2 p3" };
		for (String command : commands) {
			try {
				commandParser.parse(command);
				fail("Exception expected");
			} catch (UnknownCommandException e) {
				assertEquals(getCommandName(command), e.getCommandName());
			} catch (Exception e) {
				e.printStackTrace();
				fail("Other exception expected");
			}
		}
	}

	@Test
	public void testParseToFewParameters() {
		// Test: defined, to few parameters
		String[] commands = new String[] { "command1", "command1 p1",
				"command1 p2", "c1", "c1 p1", "c1 p1 p2" };
		for (String command : commands) {
			try {
				commandParser.parse(command);
				fail("Exception expected");
			} catch (InvalidCommandParametersException e) {
				assertEquals(getCommandName(command), e.getCommandName());
				assertEquals(commandDefinition1, e.getCommandDefinition());
				assertNull(e.getParameterName());
			} catch (Exception e) {
				e.printStackTrace();
				fail("Other exception expected");
			}
		}
	}

	@Test
	public void testParseToManyParameters() {
		// Test: defined, to many parameters - not text
		when(commandDefinition1.isTextParam()).thenReturn(false);
		String[] commands = new String[] { "command1 p1 p2 p3 p4",
				"c1 p1 p2 p3 p4", "command1 1 2 3 4 5 6 7 8 9" };
		for (String command : commands) {
			try {
				commandParser.parse(command);
				fail("Exception expected");
			} catch (InvalidCommandParametersException e) {
				assertEquals(getCommandName(command), e.getCommandName());
				assertEquals(commandDefinition1, e.getCommandDefinition());
				assertNull(e.getParameterName());
			} catch (Exception e) {
				e.printStackTrace();
				fail("Other exception expected");
			}
		}
	}

	@Test
	public void testParseInvalidParameter() {
		// Test input: defined, correct number of
		// parameters, invalid parameter
		String[] commands = new String[] { "command1 1 a 2", "c1 1 a 2" };
		for (String command : commands) {
			try {
				commandParser.parse(command);
				fail("Exception expected");
			} catch (InvalidCommandParametersException e) {
				assertEquals(getCommandName(command), e.getCommandName());
				assertEquals(commandDefinition1, e.getCommandDefinition());
				assertEquals(Command1.COMMAND_PARAM_2_NAME,
						e.getParameterName());
			} catch (Exception e) {
				e.printStackTrace();
				fail("Other exception expected");
			}
		}
	}

	@Test
	public void testParseMergedParametersIvalid() {
		// Test input: defined, correct (additional merged) number of
		// parameters, invalid parameter
		when(commandDefinition1.isTextParam()).thenReturn(true);
		String[] commands = new String[] { "command1 1 a 2 3 4 5",
				"c1 1 a 2 3 4 5", "command1 1 a 2", "c1 1 a 2" };
		for (String command : commands) {
			try {
				commandParser.parse(command);
				fail("Exception expected");
			} catch (InvalidCommandParametersException e) {
				assertEquals(getCommandName(command), e.getCommandName());
				assertEquals(commandDefinition1, e.getCommandDefinition());
				assertEquals(Command1.COMMAND_PARAM_2_NAME,
						e.getParameterName());
			} catch (Exception e) {
				e.printStackTrace();
				fail("Other exception expected");
			}
		}
	}

	@Test
	public void testParseValid() {
		// Test input: defined, correct number of
		// parameters, valid parameter
		String[] commands = new String[] { "command1 1 2 3", "c1 1 2 3" };
		for (String command : commands) {
			try {
				IParsedCommand parsedCommand = commandParser.parse(command);
				assertEquals(Command1.COMMAND_ID, parsedCommand.getCommandId());
				assertEquals(
						"1",
						parsedCommand.getValuesMap().get(
								Command1.COMMAND_PARAM_1_NAME));
				assertEquals(
						"2",
						parsedCommand.getValuesMap().get(
								Command1.COMMAND_PARAM_2_NAME));
				assertEquals(
						"3",
						parsedCommand.getValuesMap().get(
								Command1.COMMAND_PARAM_3_NAME));
			} catch (Exception e) {
				e.printStackTrace();
				fail("Exception unexpected");
			}
		}
	}

	@Test
	public void testParseMergedValid() {
		// Test input: defined, correct (additional merged) number of
		// parameters, valid parameter
		when(commandDefinition1.isTextParam()).thenReturn(true);
		String[] commands = new String[] { "command1 1 2 3 4 5", "c1 1 2 3 4 5" };
		for (String command : commands) {
			try {
				IParsedCommand parsedCommand = commandParser.parse(command);
				assertEquals(Command1.COMMAND_ID, parsedCommand.getCommandId());
				assertEquals(
						"1",
						parsedCommand.getValuesMap().get(
								Command1.COMMAND_PARAM_1_NAME));
				assertEquals(
						"2",
						parsedCommand.getValuesMap().get(
								Command1.COMMAND_PARAM_2_NAME));
				assertEquals(
						"3 4 5",
						parsedCommand.getValuesMap().get(
								Command1.COMMAND_PARAM_3_NAME));
			} catch (Exception e) {
				e.printStackTrace();
				fail("Exception unexpected");
			}
		}
	}

	private String getCommandName(String command) {
		return command.split(" ")[0];
	}

}
