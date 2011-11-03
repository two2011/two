package pl.edu.agh.two.mud.common.command.definition;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ICommandDefinition {

	String getId();

	Collection<String> getNames();

	String getDescription();

	List<ICommandParameterDefinition> getParameters();

	Map<String, ICommandParameterDefinition> getParamtersMap();

	ICommandParameterDefinition getParamater(String parameterName);

	boolean isTextParam();

}
