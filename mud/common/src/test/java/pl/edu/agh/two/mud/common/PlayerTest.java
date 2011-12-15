package pl.edu.agh.two.mud.common;

import static org.fest.assertions.Assertions.*;

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
        assertThat(player.getExperience()).isEqualTo(before + exp);
    }

    @Test
    public void shouldAddLevelWhenExperienceMoreThan1000() throws Exception {
        // GIVEN
        int exp = 1100;
        Integer before = player.getLevel();
        Integer agilityBefore = player.getAgililty();
        int maxHealthPointsBefore = player.getMaxHealthPoints();
        Integer strengthBefore = player.getStrength();
        Integer powerBefore = player.getPower();
        player.setExperience(0);

        // WHEN
        player.addExperience(exp);

        // THEN
        assertThat(player.getLevel()).isEqualTo(before + 1);
        assertThat(player.getAgililty()).isEqualTo(agilityBefore + 1);
        assertThat(player.getMaxHealthPoints()).isEqualTo(
                maxHealthPointsBefore + 10);
        assertThat(player.getHealthPoints()).isEqualTo(
                player.getMaxHealthPoints());
        assertThat(player.getPower()).isEqualTo(powerBefore + 1);
        assertThat(player.getStrength()).isEqualTo(strengthBefore + 1);

    }

}
