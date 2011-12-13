package pl.edu.agh.two.mud.common.command.exception;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.apache.log4j.Logger;
import org.junit.Test;

public class FatalExceptionTest {

	@Test
	public void handleExceptionFirstConstructor() {
		Throwable throwable = mock(Throwable.class);

		FatalException exception = new FatalException(throwable);
		exception.handleException();

		testGetCause(throwable);
	}

	@Test
	public void handleExceptionSecondConstructor() {
		Throwable throwable = mock(Throwable.class);
		Logger logger = mock(Logger.class);

		FatalException exception = new FatalException(throwable, logger);
		exception.handleException();

		testGetCause(throwable);
		verify(logger).fatal("Fatal error", throwable);
	}

	private void testGetCause(Throwable t) {
		verify(t).printStackTrace();
	}

}
