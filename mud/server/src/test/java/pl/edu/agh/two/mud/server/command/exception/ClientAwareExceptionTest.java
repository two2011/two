package pl.edu.agh.two.mud.server.command.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;

public class ClientAwareExceptionTest {

	private static final String ERROR_MESSAGE = "ErrorMessage";

	private ApplicationContext applicationContext;
	private Dispatcher dispatcher;

	@Before
	public void prepareTest() {
		applicationContext = mock(ApplicationContext.class);
		dispatcher = mock(Dispatcher.class);

		when(applicationContext.getBean(Dispatcher.class)).thenReturn(dispatcher);

		try {
			Field field = pl.edu.agh.two.mud.server.configuration.ApplicationContext.class
					.getDeclaredField("applicationContext");
			field.setAccessible(true);
			try {
				field.set(null, applicationContext);
			} finally {
				field.setAccessible(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}

	}

	@Test
	public void firstConstructorHandleException() {
		handleException(new ClientAwareException(ERROR_MESSAGE));
	}

	@Test
	public void secondConstructorHandleException() {
		handleException(new ClientAwareException(new RuntimeException(), ERROR_MESSAGE));
	}

	private void handleException(ClientAwareException exception) {
		exception.handleException();

		assertEquals(ERROR_MESSAGE, exception.getErrorMessage());
		verify(dispatcher).dispatch(new SendMessageToUserCommand(ERROR_MESSAGE, MessageType.ERROR));
	}

}
