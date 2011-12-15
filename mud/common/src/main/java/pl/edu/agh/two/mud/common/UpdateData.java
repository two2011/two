package pl.edu.agh.two.mud.common;

import java.io.*;

public class UpdateData implements Serializable{
	
	private int health;
	
	private int maxHealth;

	public UpdateData(int health, int maxHealth) {
		this.health = health;
		this.maxHealth = maxHealth;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	

}
