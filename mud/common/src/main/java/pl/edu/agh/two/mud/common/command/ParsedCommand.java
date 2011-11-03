package pl.edu.agh.two.mud.common.command;

import java.util.*;

/**
 * @author ksobon
 * 
 */
public class ParsedCommand implements IParsedCommand {

	private static final long serialVersionUID = -6130204709476482648L;
	private String id;
	private Map<String, String> values = new HashMap<String, String>();

	public ParsedCommand(String id, Map<String, String> values) {
		if (id == null) {
			throw new IllegalArgumentException();
		}

		this.id = id;
		if (values != null) {
			this.values = values;
		}
	}

	@Override
	public String getCommandId() {
		return id;
	}

	@Override
	public Map<String, String> getValuesMap() {
		return values;
	}

}
