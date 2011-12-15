package pl.edu.agh.two.mud.common;

import java.io.*;

public class Player implements IPlayer, Serializable {

    private static final int NEXT_LEVEL_STEP = 1000;

    private static final long serialVersionUID = 6035858257763542932L;

    private String name;

    private String password;

    private int strength = 1;

    private int power = 1;

    private int agililty = 1;

    private int gold = 0;

    private int experience = 0;

    private int level = 1;

    private int healthPoints = 10;

    private int maxHealthPoints = 10;

    private IPlayer enemy;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
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

    @Override
    public Integer getHealthPoints() {
        return healthPoints;
    }

    @Override
    public boolean isInFight() {
        return getEnemy() != null;
    }

    @Override
    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;

    }

    @Override
    public IPlayer getEnemy() {
        return enemy;
    }

    @Override
    public void setEnemy(IPlayer enemy) {
        this.enemy = enemy;
    }

    @Override
    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    @Override
    public void subtractHealthPoints(Integer damage) {
        healthPoints -= damage;
    }

    @Override
    public boolean isAlive() {
        return healthPoints > 0;
    }

    @Override
    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    @Override
    public UpdateData createUpdateData() {
        return new UpdateData(strength, power, agililty, gold, experience, level, healthPoints,
                maxHealthPoints);
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