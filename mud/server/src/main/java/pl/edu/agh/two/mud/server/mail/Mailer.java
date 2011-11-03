package pl.edu.agh.two.mud.server.mail;

import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer implements IMailer {

	private static final String HOST_NAME = "smtp.gmail.com";

	private static final int HOST_PORT = 465;

	private static final String USER_NAME = "agh.mud@gmail.com";

	private static final String PASSWORD = "aghmud2011";

	private static final String SUBJECT_REGISTRATION = "Konto utworzone";

	private static final String CONTENT_REGISTRATION = "Witaj, {0}!\nTwoj login to:\t{1}.\nTwoje haslo to:\t{2}";

	@Override
	public void sendRegistrationMail(String target, String login,
			String password) throws MessagingException {
		String subject = SUBJECT_REGISTRATION;
		String message = MessageFormat.format(CONTENT_REGISTRATION, target,
				login, password);
		sendMail(target, subject, message);
	}

	@Override
	public void sendMail(String target, String subject, String message)
			throws MessagingException {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", HOST_NAME);
		props.put("mail.smtps.auth", Boolean.TRUE.toString());

		Session mailSession = Session.getDefaultInstance(props);
		mailSession.setDebug(true);
		Transport transport = mailSession.getTransport();

		MimeMessage mimeMessage = new MimeMessage(mailSession);
		mimeMessage.setSubject(subject);
		mimeMessage.setContent(message, "text/plain");

		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(
				target));

		transport.connect(HOST_NAME, HOST_PORT, USER_NAME, PASSWORD);

		transport.sendMessage(mimeMessage,
				mimeMessage.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

}
