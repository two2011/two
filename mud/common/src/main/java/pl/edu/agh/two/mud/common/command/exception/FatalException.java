package pl.edu.agh.two.mud.common.command.exception;

import org.apache.log4j.Logger;

public class FatalException extends CommandExecutingException {

	private static final long serialVersionUID = 5182604634451657448L;

	private Logger logger;

	public FatalException(Throwable cause) {
		this(cause, null);
	}

	public FatalException(Throwable cause, Logger logger) {
		super(cause);
		this.logger = logger;
	}

	@Override
	public void handleException() {
		getCause().printStackTrace();

		if (logger != null) {
			logger.fatal("Fatal error", getCause());
		}
	}

}
