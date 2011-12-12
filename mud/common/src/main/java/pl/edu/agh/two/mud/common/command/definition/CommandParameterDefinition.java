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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof CommandParameterDefinition) {
			CommandParameterDefinition cpd = (CommandParameterDefinition) obj;
			
			if (name.equals(cpd.name) && regExp.equals(cpd.regExp)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (name == null ? 0 : name.hashCode());
		hash = 31 * hash + (regExp == null ? 0 : regExp.hashCode());
		return hash;
	}

}
