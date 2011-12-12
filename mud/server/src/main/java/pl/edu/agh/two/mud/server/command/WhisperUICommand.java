package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;
import pl.edu.agh.two.mud.common.command.type.Text;

@Alias({ "szepcz", "sz" })
public class WhisperUICommand extends UICommand {

	@OrderedParam(1)
	private String target;

	@OrderedParam(2)
	private Text content;

	@Override
	public String getDescription() {
		return "Komenda szeptania - przesya wiadomosc do wybranego gracza bedacego na tym samym polu\n"
				+ "\t szepcz[sz] <user> <tresc>";
	}

	public String getTarget() {
		return target;
	}

	public Text getContent() {
		return content;
	}

}
