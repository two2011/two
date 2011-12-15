package pl.edu.agh.two.mud.client.ui;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.common.world.model.Field;

public class BoardDrawerTest {
	private BoardDrawer boardDrawer;
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = mock(Board.class);
		boardDrawer = new BoardDrawer();
		when(board.getFields()).thenReturn(sampleFields());
	}
	
	private Field[][] sampleFields() {
		Field[][] fields = new Field[][]{
				{null, new Field(0, 1, "", ""), new Field(0, 2, "", "")},
				{new Field(1, 0, "", ""), new Field(1, 1, "", ""), new Field(1, 2, "", "")},
				{new Field(2, 0, "", ""), null, null}};
		return fields;
	}

	@Test
	public void shouldReturnCorrectBoardMap() {
		String actual = boardDrawer.drawBoard(board);
		String expected = "\n.##\n###\n#..\n";
		assertEquals(expected, actual);
	}

}
