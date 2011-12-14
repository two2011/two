package pl.edu.agh.two.mud.common.command.definition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandDefinition implements ICommandDefinition {

	private static final long serialVersionUID = -4083137119523819979L;
	private String id;
	private Collection<String> names;
	private String description;
	private Map<String, ICommandParameterDefinition> parametersMap = new LinkedHashMap<String, ICommandParameterDefinition>();
	private Boolean textParam;

	public CommandDefinition(String id, Collection<String> names, String description,
			List<ICommandParameterDefinition> parameters, boolean textParam) {

		if (id == null) {
			throw new IllegalArgumentException("Parameter id must be provided");
		}
		if (names == null || names.size() == 0) {
			throw new IllegalArgumentException("There must be at least one name for command");
		}
		if (description == null) {
			throw new IllegalArgumentException("Parameter description must be provided");
		}
		if ((parameters == null || parameters.size() == 0) && textParam) {
			throw new IllegalArgumentException("Cannot handle textParamater if there are no paramaters for command");
		}

		this.id = id;
		this.names = names;
		this.description = description;

		for (ICommandParameterDefinition parameter : parameters) {
			parametersMap.put(parameter.getName(), parameter);
		}

		this.textParam = textParam;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Collection<String> getNames() {
		return names;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<ICommandParameterDefinition> getParameters() {
		return Collections.unmodifiableList(new ArrayList<ICommandParameterDefinition>(parametersMap.values()));
	}

	@Override
	public Map<String, ICommandParameterDefinition> getParamtersMap() {
		return Collections.unmodifiableMap(parametersMap);
	}

	@Override
	public ICommandParameterDefinition getParamater(String parameterName) {
		return parametersMap.get(parameterName);
	}

	@Override
	public boolean isTextParam() {
		return textParam;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof CommandDefinition) {
			CommandDefinition cd = (CommandDefinition) obj;

			if (id.equals(cd.id) && names.equals(cd.names) && description.equals(cd.description)
					&& parametersMap.equals(cd.parametersMap) && textParam.equals(cd.textParam)) {
				return true;
			}

		}

		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (id == null ? 0 : id.hashCode());
		hash = 31 * hash + (names == null ? 0 : names.hashCode());
		hash = 31 * hash + (description == null ? 0 : description.hashCode());
		hash = 31 * hash + (parametersMap == null ? 0 : parametersMap.hashCode());
		hash = 31 * hash + (textParam == null ? 0 : textParam.hashCode());
		return hash;
	}

}
