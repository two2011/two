package pl.edu.agh.two.mud.common;

import java.io.Serializable;

public class Player extends Creature implements IPlayer, Serializable {

	private static final int NEXT_LEVEL_STEP = 1000;

	private static final long serialVersionUID = 6035858257763542932L;

	private String password;

	private int experience = 0;

	private int gold = 0;

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
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
	public UpdateData createUpdateData() {
		return new UpdateData(getName(), strength, power, agililty, gold,
				experience, level, healthPoints, maxHealthPoints);
	}

	@Override
	public void addExperience(int exp) {
		int oldExp = experience;
		this.experience += exp;
		if ((experience / NEXT_LEVEL_STEP) > (oldExp / NEXT_LEVEL_STEP)) {
			advance();
		}
	}

	private void advance() {
		this.level += 1;
		this.maxHealthPoints += 10;
		this.strength += 1;
		this.agililty += 1;
		this.power += 1;
		this.healthPoints = this.maxHealthPoints;
	}

}