package pl.edu.agh.two.mud.common.command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ksobon
 * 
 */
public class ParsedCommand implements IParsedCommand {

	private static final long serialVersionUID = -6130204709476482648L;
	private Object id;
	private Map<String, String> values = new HashMap<String, String>();

	public ParsedCommand(Object id, Map<String, String> values) {
		if (id == null) {
			throw new IllegalArgumentException();
		}

		this.id = id;
		if (values != null) {
			this.values = values;
		}
	}

	@Override
	public Object getCommandId() {
		return id;
	}

	@Override
	public Map<String, String> getValuesMap() {
		return values;
	}

}
