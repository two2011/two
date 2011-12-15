package pl.edu.agh.two.mud.common;

import static org.fest.assertions.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

public class PlayerTest {
    
    private IPlayer player = new Player();

    @Test
    public void shouldAddExperience() throws Exception {
        // GIVEN
        int before = player.getExperience();
        int exp = 500;

        // WHEN
        player.addExperience(exp);
        
        // THEN
        assertThat(player.getExperience()).isEqualTo(before+exp);
    }
    
    
   
}
