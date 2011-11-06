package pl.edu.agh.two.mud.common.command.definition;

import java.io.Serializable;

public interface ICommandParameterDefinition extends Serializable {

	String getName();

	String getRegExp();

}
