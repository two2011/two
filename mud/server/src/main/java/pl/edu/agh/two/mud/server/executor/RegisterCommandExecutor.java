package pl.edu.agh.two.mud.server.executor;

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
		Player player=new Player();
		player.setName(command.getLogin());
		//todo fix password
		player.setPassword(command.getLogin());
		try {
			//todo use password generator
			mailer.sendRegistrationMail(command.getEmail(), command.getLogin(), command.getLogin());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.addPlayer(player);
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setMailer(Mailer mailer) {
		this.mailer = mailer;
	}
}
