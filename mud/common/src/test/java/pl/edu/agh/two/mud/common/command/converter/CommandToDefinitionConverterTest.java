package pl.edu.agh.two.mud.common.command.converter;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.type.Text;

public class CommandToDefinitionConverterTest {

	private CommandToDefinitionConverter converter;

	@Before
	public void prepareTest() {
		converter = new CommandToDefinitionConverter();
	}

	@Test
	public void noIdCommand() {
		Command command = mock(Command.class);
		when(command.getId()).thenReturn(null);
		try {
			converter.convertToCommandDefinition(command);
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void noAliasCommand() {
		Command command = new Command() {

			@Override
			public String getDescription() {
				return null;
			}
		};

		try {
			converter.convertToCommandDefinition(command);
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void emptyAliasCommand() {
		@Alias({})
		class TestCommand extends Command {
			@Override
			public String getDescription() {
				return null;
			}
		}

		try {
			converter.convertToCommandDefinition(new TestCommand());
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void commandWithoutDescription() {
		@Alias(value = { "whathever" })
		class TestCommand extends Command {
			@Override
			public String getDescription() {
				return null;
			}
		}

		try {
			converter.convertToCommandDefinition(new TestCommand());
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void commandWithoutParameters() {
		final String alias1 = "command1";
		final String alias2 = "c1";
		final String description = "description";

		@Alias({ alias1, alias2 })
		class TestCommand extends Command {
			@Override
			public String getDescription() {
				return description;
			}
		}

		TestCommand command = new TestCommand();
		ICommandDefinition definition = converter
				.convertToCommandDefinition(command);
		assertEquals(definition.getId(), command.getId());
		assertThat(definition.getNames()).containsOnly(alias1, alias2);
		assertEquals(definition.getDescription(), description);
		assertThat(definition.getParameters()).isEmpty();
		assertFalse(definition.isTextParam());
	}

	@Test
	public void commandWithTwoTextParams() {
		final String description = "description";

		@SuppressWarnings("unused")
		@Alias({ "command1" })
		class TestCommand extends Command {

			@OrderedParam(1)
			private Text param1;

			@OrderedParam(2)
			private Text param2;

			@Override
			public String getDescription() {
				return description;
			}
		}

		TestCommand command = new TestCommand();
		try {
			converter.convertToCommandDefinition(command);
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void commandWithTwoTheSameParamNo() {
		final String description = "description";

		@SuppressWarnings("unused")
		@Alias({ "command1" })
		class TestCommand extends Command {

			@OrderedParam(7)
			private String param1;

			@OrderedParam(7)
			private Integer param2;

			@Override
			public String getDescription() {
				return description;
			}
		}

		TestCommand command = new TestCommand();
		try {
			converter.convertToCommandDefinition(command);
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void commandWithStringParameter() {
		final String alias1 = "command1";
		final String description = "description";

		@SuppressWarnings("unused")
		@Alias({ alias1 })
		class TestCommand extends Command {

			@OrderedParam(1)
			private String param1;

			@Override
			public String getDescription() {
				return description;
			}
		}

		TestCommand command = new TestCommand();
		ICommandDefinition definition = converter
				.convertToCommandDefinition(command);
		assertEquals(definition.getId(), command.getId());
		assertThat(definition.getNames()).containsOnly(alias1);
		assertEquals(definition.getDescription(), description);
		assertThat(definition.getParameters()).hasSize(1);
		assertThat(definition.getParamater("param1")).isNotNull();
		assertThat(definition.getParamater("param1").getRegExp()).isEqualTo(
				"^.*$");
		assertFalse(definition.isTextParam());
	}

	@Test
	public void commandWithIntegerParameter() {
		final String alias1 = "command1";
		final String description = "description";

		@SuppressWarnings("unused")
		@Alias({ alias1 })
		class TestCommand extends Command {

			@OrderedParam(1)
			private Integer param1;

			@Override
			public String getDescription() {
				return description;
			}
		}

		TestCommand command = new TestCommand();
		ICommandDefinition definition = converter
				.convertToCommandDefinition(command);
		assertEquals(definition.getId(), command.getId());
		assertThat(definition.getNames()).containsOnly(alias1);
		assertEquals(definition.getDescription(), description);
		assertThat(definition.getParameters()).hasSize(1);
		assertThat(definition.getParamater("param1")).isNotNull();
		assertThat(definition.getParamater("param1").getRegExp()).isEqualTo(
				"^[0-9]*$");
		assertFalse(definition.isTextParam());
	}

	@Test
	public void commandWithIntParameter() {
		final String alias1 = "command1";
		final String description = "description";

		@SuppressWarnings("unused")
		@Alias({ alias1 })
		class TestCommand extends Command {

			@OrderedParam(1)
			private int param1;

			@Override
			public String getDescription() {
				return description;
			}
		}

		TestCommand command = new TestCommand();
		ICommandDefinition definition = converter
				.convertToCommandDefinition(command);
		assertEquals(definition.getId(), command.getId());
		assertThat(definition.getNames()).containsOnly(alias1);
		assertEquals(definition.getDescription(), description);
		assertThat(definition.getParameters()).hasSize(1);
		assertThat(definition.getParamater("param1")).isNotNull();
		assertThat(definition.getParamater("param1").getRegExp()).isEqualTo(
				"^[0-9]*$");
		assertFalse(definition.isTextParam());
	}

	@Test
	public void commandWithTextParameter() {
		final String alias1 = "command1";
		final String description = "description";

		@SuppressWarnings("unused")
		@Alias({ alias1 })
		class TestCommand extends Command {

			@OrderedParam(1)
			private Text param1;

			@Override
			public String getDescription() {
				return description;
			}
		}

		TestCommand command = new TestCommand();
		ICommandDefinition definition = converter
				.convertToCommandDefinition(command);
		assertEquals(definition.getId(), command.getId());
		assertThat(definition.getNames()).containsOnly(alias1);
		assertEquals(definition.getDescription(), description);
		assertThat(definition.getParameters()).hasSize(1);
		assertThat(definition.getParamater("param1")).isNotNull();
		assertThat(definition.getParamater("param1").getRegExp()).isEqualTo(
				"^.*$");
		assertTrue(definition.isTextParam());
	}

	@Test
	public void commandWithNotSupportedParameterType() {
		final String description = "description";

		@SuppressWarnings("unused")
		@Alias({ "command1" })
		class TestCommand extends Command {

			@OrderedParam(1)
			private Object param1;

			@Override
			public String getDescription() {
				return description;
			}
		}

		TestCommand command = new TestCommand();
		try {
			converter.convertToCommandDefinition(command);
			fail("Exception expected");
		} catch (IllegalArgumentException e) {
		}
	}

}
