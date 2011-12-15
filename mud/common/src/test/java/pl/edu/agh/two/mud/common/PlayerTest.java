package pl.edu.agh.two.mud.common;

import static org.fest.assertions.Assertions.*;
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
    
    @Test
    public void shouldCreateUpdateData() throws Exception {
        // GIVEN
        player.setAgililty(5);
        player.setExperience(500);
        player.setGold(100);
        player.setHealthPoints(100);
        player.setLevel(5);
        player.setMaxHealthPoints(1000);
        player.setPower(5);
        player.setStrength(5);

        // WHEN
        UpdateData updateData = player.createUpdateData();

        // THEN
        assertThat(updateData.getHealthPoints()).isEqualTo(100);
        assertThat(updateData.getMaxHealthPoints()).isEqualTo(1000);
        assertThat(updateData.getAgililty()).isEqualTo(5);
        assertThat(updateData.getStrength()).isEqualTo(5);
        assertThat(updateData.getPower()).isEqualTo(5);
        assertThat(updateData.getGold()).isEqualTo(100);
        assertThat(updateData.getExperience()).isEqualTo(500);
        assertThat(updateData.getLevel()).isEqualTo(5);
        
        

    }

}
