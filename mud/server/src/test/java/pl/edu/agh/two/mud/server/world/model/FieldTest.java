package pl.edu.agh.two.mud.server.world.model;

import org.junit.Test;
import pl.edu.agh.two.mud.common.Player;

import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.edu.agh.two.mud.server.world.model.Direction.E;
import static pl.edu.agh.two.mud.server.world.model.Direction.N;

public class FieldTest {

    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "someDescription";
    private static final String FIRST_PLAYER_NAME = "firstPlayer";
    private static final String SECOND_PLAYER_NAME = "secondPlayer";

    @Test
    public void shouldReturnNameAndDescriptionAndValidDirections() {
        // given
        Board board = mock(Board.class);
        Field testedField = new Field(0, 0, FIELD_NAME, FIELD_DESCRIPTION);
        testedField.setBoard(board);
        when(board.getPossibleDirections(testedField)).thenReturn(Arrays.asList(N, E));
        Player somePlayer = createPlayerWithName(FIRST_PLAYER_NAME);
        testedField.addPlayer(somePlayer);

        Player anotherPlayer = createPlayerWithName(SECOND_PLAYER_NAME);

        // when
        String actual = testedField.getFormattedFieldSummary();

        // then
        assertThat(actual).startsWith("Lokacja: " + FIELD_NAME + "\n" + FIELD_DESCRIPTION);
        assertThat(actual).contains(somePlayer.getName());
        assertThat(actual).doesNotContain(anotherPlayer.getName());
        assertThat(actual).endsWith("N\tE");
    }

    private Player createPlayerWithName(String playerName) {
        Player player = new Player();
        player.setName(playerName);
        return player;
    }
}
