package pl.edu.agh.two.mud.common;

import java.io.Serializable;

public class Player extends Creature implements IPlayer, Serializable {

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
		return new UpdateData(healthPoints, maxHealthPoints);
	}

}