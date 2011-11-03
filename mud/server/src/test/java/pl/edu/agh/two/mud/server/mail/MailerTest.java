package pl.edu.agh.two.mud.server.mail;

import javax.mail.MessagingException;

import org.junit.Ignore;
import org.junit.Test;

public class MailerTest {

	private static final String PASSWORD = "classified";
	private static final String LOGIN = "krzyho";
	private static final String TARGET_MAIL = "krzyho@gmail.com";

	@Test
	@Ignore
	public void shouldSendRegistrationMail() throws MessagingException {
		IMailer mailer = new Mailer();

		mailer.sendRegistrationMail(TARGET_MAIL, LOGIN, PASSWORD);

	}

}
