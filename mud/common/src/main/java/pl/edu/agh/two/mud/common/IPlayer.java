package pl.edu.agh.two.mud.common;

public interface IPlayer {

	public abstract String getName();

	public abstract void setName(String name);

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

}