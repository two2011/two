package pl.edu.agh.two.mud.common;

import java.io.Serializable;

public class UpdateData implements Serializable {

	private static final long serialVersionUID = 7072973000316251332L;

	private String name;

	private int strength;

	private int power;

	private int agililty;

	private int gold;

	private int experience;

	private int level;

	private int healthPoints;

	private int maxHealthPoints;

	public int getStrength() {
		return strength;
	}

	public int getPower() {
		return power;
	}

	public int getAgililty() {
		return agililty;
	}

	public int getGold() {
		return gold;
	}

	public int getExperience() {
		return experience;
	}

	public int getLevel() {
		return level;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public UpdateData(String name, int strength, int power, int agililty,
			int gold, int experience, int level, int healthPoints,
			int maxHealthPoints) {
		super();
		this.strength = strength;
		this.power = power;
		this.agililty = agililty;
		this.gold = gold;
		this.experience = experience;
		this.level = level;
		this.healthPoints = healthPoints;
		this.maxHealthPoints = maxHealthPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
