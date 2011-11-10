package pl.edu.agh.two.mud.server.world.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.edu.agh.two.mud.server.world.model.Direction.W;
import static pl.edu.agh.two.mud.server.world.model.Direction.E;
import static pl.edu.agh.two.mud.server.world.model.Direction.N;

public class BoardTest {

    @Test
    public void shouldReturnPossibleDirections() {
        // given
        Board board = new Board();
        board.setSize(2);
        Field[][] fields = createNewFields();
        board.setFields(fields);

        // when
        List<Direction> expectedDirections = new ArrayList<Direction>();
        expectedDirections.add(E);
        expectedDirections.add(N);

        List<Direction> expectedDirections2 = new ArrayList<Direction>();
        expectedDirections2.add(N);
        expectedDirections2.add(W);

        // then
        List<Direction> actualDirections = board.getPossibleDirections(fields[0][1]);
        assertThat(actualDirections).containsOnly(expectedDirections.toArray());

        List<Direction> actualDirections2 = board.getPossibleDirections(fields[1][1]);
        assertThat(actualDirections2).containsOnly(expectedDirections2.toArray());
    }

    private Field[][] createNewFields() {
        Field[][] result = new Field[2][2];
        result[0][0] = new Field(0, 0, "someName", "someDescription");
        result[0][1] = new Field(0, 1, "someName", "someDescription");
        result[1][0] = new Field(1, 0, "someName", "someDescription");
        result[1][1] = new Field(1, 1, "someName", "someDescription");
        return result;
    }
}
