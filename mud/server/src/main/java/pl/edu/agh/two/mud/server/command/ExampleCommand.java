package pl.edu.agh.two.mud.server.command;

import pl.edu.agh.two.mud.common.command.UICommand;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;
import pl.edu.agh.two.mud.common.command.type.Text;

@Alias({ "example", "ex" })
public class ExampleCommand extends UICommand {

	@OrderedParam(1)
	private String param1;

	@OrderedParam(2)
	private int param2;

	@OrderedParam(3)
	private Integer param3;

	@OrderedParam(4)
	private Text param4;

	@Override
	public String getDescription() {
		return "Przykladowa komenda. Jej uzycie spowoduje wypisanie wszystkich parameterow na konsoli serwera.\n" +
				"\t example[ex] <param1> <param2> <param3> <param4>\n" +
				"\t\tparam1 - String\n" +
				"\t\tparam2 - int\n" +
				"\t\tparam3 - Integer\n" +
				"\t\tparam4 - Text (dowolny ciag znakow wraz ze spacjami)";
	}

	public String getParam1() {
		return param1;
	}

	public int getParam2() {
		return param2;
	}

	public Integer getParam3() {
		return param3;
	}

	public Text getParam4() {
		return param4;
	}

}
