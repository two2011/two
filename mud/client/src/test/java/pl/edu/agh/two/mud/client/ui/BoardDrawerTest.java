package pl.edu.agh.two.mud.client.ui;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.two.mud.common.world.model.Board;
import pl.edu.agh.two.mud.common.world.model.SampleBoard;

public class BoardDrawerTest {
	private BoardDrawer boardDrawer;
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new SampleBoard();
		boardDrawer = new BoardDrawer();
	}
	
	@Test
	public void shouldReturnCorrectBoardMap() {
		String actual = boardDrawer.drawBoard(board);
		String expected = "###";
		assertEquals(expected, actual);
	}

}
