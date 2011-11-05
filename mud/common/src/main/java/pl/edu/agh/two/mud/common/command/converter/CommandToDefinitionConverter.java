package pl.edu.agh.two.mud.common.command.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.two.mud.common.command.Command;
import pl.edu.agh.two.mud.common.command.annotation.Alias;
import pl.edu.agh.two.mud.common.command.annotation.OrderedParam;
import pl.edu.agh.two.mud.common.command.definition.CommandDefinition;
import pl.edu.agh.two.mud.common.command.definition.CommandParameterDefinition;
import pl.edu.agh.two.mud.common.command.definition.ICommandDefinition;
import pl.edu.agh.two.mud.common.command.definition.ICommandParameterDefinition;
import pl.edu.agh.two.mud.common.command.type.Text;

public class CommandToDefinitionConverter {

	public ICommandDefinition convertToCommandDefinition(Command command) {
		Class<? extends Command> clazz = command.getClass();

		String id = command.getId();
		if (id == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}

		Alias alias = clazz.getAnnotation(Alias.class);
		if (alias == null || alias.value().length == 0) {
			throw new IllegalArgumentException(
					"There should be at least one alias");
		}
		Collection<String> names = Arrays.asList(alias.value());

		Map<OrderedParam, Field> paramAnnotationMap = new LinkedHashMap<OrderedParam, Field>();
		for (Field field : clazz.getDeclaredFields()) {
			OrderedParam orderedParam = field.getAnnotation(OrderedParam.class);
			if (orderedParam != null) {
				if (paramAnnotationMap.put(orderedParam, field) != null) {
					throw new IllegalArgumentException(
							"There are two paramaters with the same order number !");
				}
			}
		}

		// Sort ordered parameters
		List<OrderedParam> orderedParams = new ArrayList<OrderedParam>(
				paramAnnotationMap.keySet());
		Collections.sort(orderedParams, new Comparator<OrderedParam>() {
			@Override
			public int compare(OrderedParam o1, OrderedParam o2) {
				return new Integer(o1.value()).compareTo(new Integer(o2.value()));
			}
		});

		boolean textField = false;
		List<ICommandParameterDefinition> parameters = new ArrayList<ICommandParameterDefinition>();
		for (OrderedParam orderedParam : orderedParams) {
			Field field = paramAnnotationMap.get(orderedParam);
			Class<?> type = field.getType();

			parameters.add(new CommandParameterDefinition(field.getName(),
					getRegExp(type)));

			if (textField) {
				throw new IllegalArgumentException(
						"Parameter of Text type must be the last parameter of command !");
			}

			if (Text.class.equals(type)) {
				textField = true;
			}
		}

		return new CommandDefinition(id, names, command.getDescription(),
				parameters, textField);
	}

	private String getRegExp(Class<?> type) {
		String result = null;

		if (String.class.equals(type) || Text.class.equals(type)) {
			result = "^.*$";
		} else if (Integer.class.equals(type) || int.class.equals(type)) {
			result = "^[0-9]*$";
		} else {
			throw new IllegalArgumentException(String.format(
					"Type \"%s\" is not supported as parameter type.",
					type.getName()));
		}

		return result;
	}
}
