package pl.edu.agh.two.mud.common.command;

import java.io.Serializable;
import java.util.Map;

public interface IParsedCommand extends Serializable {

	Object getCommandId();

	Map<String, String> getValuesMap();

}
