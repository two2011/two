package pl.edu.agh.two.mud.server.command.executor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import pl.edu.agh.two.mud.common.Player;
import pl.edu.agh.two.mud.common.command.dispatcher.Dispatcher;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.common.message.MessageType;
import pl.edu.agh.two.mud.common.world.exception.NoCreatureWithSuchNameException;
import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.server.command.RegisterUICommand;
import pl.edu.agh.two.mud.server.command.SendMessageToUserCommand;
import pl.edu.agh.two.mud.server.command.exception.ClientAwareException;
import pl.edu.agh.two.mud.server.mail.Mailer;

public class RegisterUICommandExecutor implements CommandExecutor<RegisterUICommand> {

	private static final String MESSAGE_ON_WRONG_ADDRESS = "Bledny adres e-mail";

	private static final String MESSAGE_ON_EXISTING_LOGIN = "Podany login juz istnieje";

	private static final String MESSAGE_ON_SUCCESS = "Konto zostalo utworzone. Dane dostepowe zostaly wyslane na adres: ";

	private static final Logger log = Logger.getLogger(RegisterUICommandExecutor.class);
	private Board board;

	private Mailer mailer;

	private Dispatcher dispatcher;

	@Override
	public void execute(RegisterUICommand command) throws ClientAwareException {
		try {
			board.getPlayerByName(command.getLogin());
			throw new ClientAwareException(MESSAGE_ON_EXISTING_LOGIN);
		} catch (NoCreatureWithSuchNameException e) {
			try {
				Player player = new Player();
				String password = generatePassword();
				mailer.sendRegistrationMail(command.getEmail(), command.getLogin(), password);
				player.setName(command.getLogin());
				player.setPassword(password);
				board.addPlayer(player);
				dispatcher.dispatch(new SendMessageToUserCommand(MESSAGE_ON_SUCCESS + command.getEmail(),
						MessageType.INFO));
			} catch (MessagingException e1) {
				throw new ClientAwareException(e1, MESSAGE_ON_WRONG_ADDRESS);
			}
		}

	}

	private String generatePassword() {
		Long password = new Random(System.nanoTime()).nextLong();
		password += 100000;
		password = Math.abs(password);

		String pass = password.toString();
		try {
			byte[] array = MessageDigest.getInstance("MD5").digest(password.toString().getBytes());
			;
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			pass = sb.toString().substring(0, 7);

		} catch (NoSuchAlgorithmException e) {
			log.fatal("No algorithm", e);
		}

		return pass;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
}
