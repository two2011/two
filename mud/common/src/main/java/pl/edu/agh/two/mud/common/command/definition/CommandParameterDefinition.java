package pl.edu.agh.two.mud.common.command.definition;

public class CommandParameterDefinition implements ICommandParameterDefinition {

	private static final long serialVersionUID = -2586432568792198478L;
	private String name;
	private String regExp;

	public CommandParameterDefinition(String name, String regExp) {
		if (name == null) {
			throw new IllegalArgumentException(
					"Paramater name must be provided");
		}

		this.name = name;
		this.regExp = regExp;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getRegExp() {
		return regExp;
	}

}
