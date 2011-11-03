package pl.edu.agh.two.mud.server.mail;

import javax.mail.MessagingException;

/**
 * @author kuras
 *
 */
public interface IMailer {

	public void sendRegistrationMail(String target, String login,
			String password) throws MessagingException;

	public void sendMail(String target, String subject, String message)
			throws MessagingException;

}
