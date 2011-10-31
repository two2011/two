package pl.edu.agh.two.mud.common;

public class Player implements IPlayer {

	private String name;

	private int strength;

	private int power;

	private int agililty;

	private int gold;

	private int experience;

	private int level;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Integer getStrength() {
		return strength;
	}

	@Override
	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	@Override
	public Integer getPower() {
		return power;
	}

	@Override
	public void setPower(Integer power) {
		this.power = power;
	}

	@Override
	public Integer getAgililty() {
		return agililty;
	}

	@Override
	public void setAgililty(Integer agililty) {
		this.agililty = agililty;
	}

	@Override
	public Integer getGold() {
		return gold;
	}

	@Override
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	@Override
	public Integer getExperience() {
		return experience;
	}

	@Override
	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	@Override
	public Integer getLevel() {
		return level;
	}

	@Override
	public void setLevel(Integer level) {
		this.level = level;
	}



}
