package pl.edu.agh.two.mud.server.command.executor;

import java.util.Random;

import javax.mail.MessagingException;

import pl.edu.agh.two.mud.common.Player;
import pl.edu.agh.two.mud.common.command.executor.CommandExecutor;
import pl.edu.agh.two.mud.server.command.RegisterCommand;
import pl.edu.agh.two.mud.server.mail.Mailer;
import pl.edu.agh.two.mud.server.world.model.Board;

public class RegisterCommandExecutor implements
		CommandExecutor<RegisterCommand> {

	private Board board;
	private Mailer mailer;

	@Override
	public void execute(RegisterCommand command) {
		Player player = new Player();
		try {
			String password = generatePassword();
			mailer.sendRegistrationMail(command.getEmail(), command.getLogin(),
					password);
			player.setName(command.getLogin());
			player.setPassword(password);
			board.addPlayer(player);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private String generatePassword() {
		Long password = new Random(System.nanoTime()).nextLong();
		password += 100000;
		password = Math.abs(password);
		return password.toString();
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}
}
