package pl.edu.agh.two.mud.common;

public interface IPlayer extends ICreature {

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract Integer getGold();

	public abstract void setGold(Integer gold);

	public abstract Integer getExperience();

	public abstract void setExperience(Integer experience);

	UpdateData createUpdateData();


    public abstract void addExperience(int exp);

}