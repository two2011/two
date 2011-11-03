package pl.edu.agh.two.mud.common.command;

import java.io.Serializable;
import java.util.Map;

public interface IParsedCommand extends Serializable {

	String getCommandId();

	Map<String, String> getValuesMap();

}
