package pl.edu.agh.two.mud.client.ui;

import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.common.world.model.Field;

public class BoardDrawer {

	public String drawBoard(Board board) {
		Field[][] fields = board.getFields();
		StringBuilder text = new StringBuilder();
		text.append("\n");
		for(Field[] row: fields) {
			for(Field field: row) {
				if(field == null) {
					text.append('.');
				}
				else {
					text.append('#');
				}
			}
			text.append("\n");
		}
		return text.toString();
	}

}
