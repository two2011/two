package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;

@Alias({"odswiez", "o"})
public class RefreshUICommand extends UICommand {

	@Override
	public String getDescription() {
		return "Komenda odswiezania - wypisuje informacje odnosnie pola, na ktorym sie znajdujesz oraz rysuje plansze gry\n"
				+ "\t odswiez[o]";
	}

}
