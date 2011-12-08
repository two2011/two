package pl.edu.agh.two.mud.common;

public interface IPlayer {

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract Integer getStrength();

	public abstract void setStrength(Integer strength);

	public abstract Integer getPower();

	public abstract void setPower(Integer power);

	public abstract Integer getAgililty();

	public abstract void setAgililty(Integer agililty);

	public abstract Integer getGold();

	public abstract void setGold(Integer gold);

	public abstract Integer getExperience();

	public abstract void setExperience(Integer experience);

	public abstract Integer getLevel();

	public abstract void setLevel(Integer level);

	public abstract Integer getHealthPoints();

	public abstract void setHealthPoints(Integer healthPoints);
	
	public abstract void substractHealthPoints(Integer damage);

	public boolean isInFight();

	public IPlayer getEnemy();
	
	public void setEnemy(IPlayer enemy);

	int getMaxHealthPoints();

	void setMaxHealthPoints(int maxHealthPoints);
	
	public boolean isAlive();

}