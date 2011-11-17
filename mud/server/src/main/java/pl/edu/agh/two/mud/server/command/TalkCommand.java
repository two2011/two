package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;
import pl.edu.agh.two.mud.common.command.type.Text;

@Alias({ "mow", "m" })
public class TalkCommand extends UICommand {

	@OrderedParam(1)
	private Text content;

	@Override
	public String getDescription() {
		return "Komenda mowienia - przesyla wiadomosc do wszystkich graczy bedacego na tym samym polu\n"
				+ "\t mow[m] <tresc>";
	}

	public Text getContent() {
		return content;
	}

}
