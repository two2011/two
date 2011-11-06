package pl.edu.agh.two.mud.common.command;

import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;
import pl.edu.agh.two.mud.common.command.type.Text;

@Alias({ "test", "test2" })
public class SampleCommand extends UICommand {

	@OrderedParam(1)
	private String stringParam;

	@OrderedParam(2)
	private int intParam;

	@OrderedParam(3)
	private Integer IntegerParam;

	@OrderedParam(4)
	private double doubleParam;

	@OrderedParam(5)
	private Double DoubleParam;

	@OrderedParam(6)
	private Text textParam;

	@Override
	public String getDescription() {
		return null;
	}

	public String getStringParam() {
		return stringParam;
	}

	public int getIntParam() {
		return intParam;
	}

	public Text getTextParam() {
		return textParam;
	}

	public double getdoubleParam() {
		return doubleParam;
	}

	public Integer getIntegerParam() {
		return IntegerParam;
	}

	public Double getDoubleParam() {
		return DoubleParam;
	}

}
