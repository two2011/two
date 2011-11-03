package pl.edu.agh.two.mud.server.command.factory;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.apache.log4j.*;
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
    @Mock
    private Logger logger;

    @InjectMocks
    private ReflexiveCommandFactory reflexiveCommandFactory = new ReflexiveCommandFactory();

    @Test
    public void shouldCreateCommand() throws Exception {
        // GIVEN
        TestCommand testCommand = new TestCommand();

        IParsedCommand parsedCommand = mock(IParsedCommand.class);
        when(parsedCommand.getCommandId()).thenReturn("ZUZIA");

        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("testowy", "STRING");
        parameters.put("misiek", "54");
        parameters.put("zuzia", "text");
        when(parsedCommand.getValuesMap()).thenReturn(parameters);
        doReturn(Command.class).when(commandClassRegistry).getCommandClass(
                "ZUZIA");
        when(applicationContext.getBean(Command.class)).thenReturn(testCommand);

        // WHEN
        testCommand = (TestCommand) reflexiveCommandFactory.create(parsedCommand);

        // THEN
        assertThat(testCommand.getTestowy()).isEqualTo("STRING");
        assertThat(testCommand.getMisiek()).isEqualTo(54);
        assertThat(testCommand.getZuzia().getText()).isEqualTo("text");

    }

}
