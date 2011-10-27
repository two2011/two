package pl.edu.agh.two.mud.client.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;
import pl.edu.agh.two.mud.common.IPlayer;

public class HeroPanel extends JPanel {

	private static final long serialVersionUID = -8842308968094233877L;

	private static final Color LABEL_TEXT_COLOR = Color.WHITE;

	private static final Color VALUE_TEXT_COLOR = Color.YELLOW;

	private JLabel feet;

	private JLabel body;

	private JLabel head;

	private JLabel weapon;

	private JLabel agility;

	private JLabel power;

	private JLabel strength;

	private JLabel experience;

	private JLabel gold;

	private JLabel name;

	private JLabel level;

	/**
	 * Create the panel.
	 */
	public HeroPanel() {
		setBorder(new LineBorder(Color.WHITE));
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setLayout(new MigLayout("", "[100px][100px][100px][100px]", "[][][]"));

		createGeneralInfo();
		createStatisticsPanel();
		createEquipmentPanel();
		createOthersPanel();

	}

	private void createGeneralInfo() {
		JLabel playerNameLabel = new JLabel("Imie postaci");
		playerNameLabel.setForeground(LABEL_TEXT_COLOR);
		add(playerNameLabel, "cell 0 0,alignx left,aligny center");

		name = new JLabel("nameValue");
		name.setForeground(VALUE_TEXT_COLOR);
		add(name, "cell 1 0,alignx left,aligny center");

		JLabel levelLabel = new JLabel("Poziom");
		levelLabel.setForeground(LABEL_TEXT_COLOR);
		add(levelLabel, "cell 2 0");

		level = new JLabel("0");
		level.setForeground(VALUE_TEXT_COLOR);
		add(level, "cell 3 0");
	}

	private void createStatisticsPanel() {
		JPanel statisticsPanel = new JPanel();
		statisticsPanel.setForeground(Color.WHITE);
		statisticsPanel.setBackground(Color.BLACK);
		statisticsPanel.setBorder(new LineBorder(new Color(255, 255, 255)));
		add(statisticsPanel, "cell 0 1 2 1,grow");
		statisticsPanel.setLayout(new GridLayout(0, 2, 4, 4));

		JLabel strengthLabel = new JLabel("Sila");
		strengthLabel.setForeground(LABEL_TEXT_COLOR);
		statisticsPanel.add(strengthLabel);

		strength = new JLabel("0");
		strength.setForeground(VALUE_TEXT_COLOR);
		statisticsPanel.add(strength);

		JLabel powerLabel = new JLabel("Moc");
		powerLabel.setForeground(LABEL_TEXT_COLOR);
		statisticsPanel.add(powerLabel);

		power = new JLabel("0");
		power.setForeground(VALUE_TEXT_COLOR);
		statisticsPanel.add(power);

		JLabel agilityLabel = new JLabel("Zwinnosc");
		agilityLabel.setForeground(LABEL_TEXT_COLOR);
		statisticsPanel.add(agilityLabel);

		agility = new JLabel("0");
		agility.setForeground(VALUE_TEXT_COLOR);
		statisticsPanel.add(agility);
	}

	private void createEquipmentPanel() {
		JPanel equipmentPanel = new JPanel();
		equipmentPanel.setBorder(new LineBorder(Color.WHITE));
		equipmentPanel.setBackground(Color.BLACK);
		equipmentPanel.setForeground(Color.WHITE);
		add(equipmentPanel, "cell 2 1 2 1,grow");
		equipmentPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel weaponLabel = new JLabel("Bron");
		weaponLabel.setForeground(LABEL_TEXT_COLOR);
		equipmentPanel.add(weaponLabel);

		weapon = new JLabel("brak");
		weapon.setForeground(VALUE_TEXT_COLOR);
		equipmentPanel.add(weapon);

		JLabel headLabel = new JLabel("Ubranie - glowa");
		headLabel.setForeground(LABEL_TEXT_COLOR);
		equipmentPanel.add(headLabel);

		head = new JLabel("brak");
		head.setForeground(VALUE_TEXT_COLOR);
		equipmentPanel.add(head);

		JLabel bodyLabel = new JLabel("Ubranie - tulow");
		bodyLabel.setForeground(LABEL_TEXT_COLOR);
		equipmentPanel.add(bodyLabel);

		body = new JLabel("brak");
		body.setForeground(VALUE_TEXT_COLOR);
		equipmentPanel.add(body);

		JLabel feetLabel = new JLabel("Ubranie - nogi");
		feetLabel.setForeground(LABEL_TEXT_COLOR);
		equipmentPanel.add(feetLabel);

		feet = new JLabel("brak");
		feet.setForeground(VALUE_TEXT_COLOR);
		equipmentPanel.add(feet);
	}

	private void createOthersPanel() {
		JPanel othersPanel = new JPanel();
		othersPanel.setBorder(new LineBorder(Color.WHITE));
		othersPanel.setBackground(Color.BLACK);
		othersPanel.setForeground(Color.WHITE);
		add(othersPanel, "cell 0 2 4 1,grow");
		othersPanel.setLayout(new GridLayout(0, 4, 0, 0));

		JLabel expirienceLabel = new JLabel("Doswiadczenie");
		othersPanel.add(expirienceLabel);
		expirienceLabel.setForeground(LABEL_TEXT_COLOR);

		experience = new JLabel("0");
		othersPanel.add(experience);
		experience.setForeground(VALUE_TEXT_COLOR);

		JLabel goldLabel = new JLabel("Zloto");
		othersPanel.add(goldLabel);
		goldLabel.setForeground(LABEL_TEXT_COLOR);

		gold = new JLabel("0");
		othersPanel.add(gold);
		gold.setForeground(VALUE_TEXT_COLOR);
	}

	public void updateHero(IPlayer player) {
		// updating name
		name.setText(player.getName());

		// updating level
		level.setText(player.getLevel().toString());

		// updating basic stats
		strength.setText(player.getStrength().toString());
		power.setText(player.getPower().toString());
		agility.setText(player.getAgililty().toString());

		// updating exp
		experience.setText(player.getExperience().toString());

		// updating gold
		gold.setText(player.getGold().toString());
	}

}
