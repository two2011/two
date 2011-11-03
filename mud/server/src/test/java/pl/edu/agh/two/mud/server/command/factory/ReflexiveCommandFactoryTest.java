package pl.edu.agh.two.mud.server.command.factory;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;
import org.springframework.context.*;

import pl.edu.agh.two.mud.common.command.*;
import pl.edu.agh.two.mud.server.command.*;
import pl.edu.agh.two.mud.server.command.registry.*;

@RunWith(MockitoJUnitRunner.class)
public class ReflexiveCommandFactoryTest {

	@Mock
	private CommandClassRegistry commandClassRegistry;
	@Mock
	private ApplicationContext applicationContext;

	@InjectMocks
	private ReflexiveCommandFactory reflexiveCommandFactory = new ReflexiveCommandFactory();

	@Test
	public void shouldCreateCommand() throws Exception {
		// GIVEN
		IParsedCommand parsedCommand = mock(IParsedCommand.class);
		String commandId = "ZUZIA";
		when(parsedCommand.getCommandId()).thenReturn(commandId);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("stringParam", "string");
		parameters.put("intParam", "54");
		parameters.put("textParam", "text");
		when(parsedCommand.getValuesMap()).thenReturn(parameters);
		doReturn(Command.class).when(commandClassRegistry).getCommandClass(commandId);
		when(applicationContext.getBean(Command.class)).thenReturn(new TestCommand());

		// WHEN
		Command command = reflexiveCommandFactory.create(parsedCommand);

		// THEN
		assertThat(command).isInstanceOf(TestCommand.class);
		TestCommand testCommand = (TestCommand) command;
		assertThat(testCommand.getStringParam()).isEqualTo("string");
		assertThat(testCommand.getIntParam()).isEqualTo(54);
		assertThat(testCommand.getTextParam().getText()).isEqualTo("text");

	}

}
