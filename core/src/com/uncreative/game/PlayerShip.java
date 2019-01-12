package com.uncreative.game;

import java.util.ArrayList;

public class PlayerShip implements Ship
{
    private String[] direction = { "N", "E", "S", "W", "NE", "SE", "SW", "NW"};
    private Integer goldAvailable;
    private Integer totalGoldEarnt;
    private Integer XP;
    private Integer currentHP;
    private Integer maxHP;
    private Integer baseDamage;
    private Integer baseDefence;
    private College college;
    private ArrayList<Buff> buffs = new ArrayList<Buff>();


    public PlayerShip(Integer hp, Integer maxHP, Integer baseDamage, Integer baseDefence, College college, ArrayList<Buff> activeBuffs, Boolean inBattle, Integer gold, Integer xp, Item[] items, Location location)
    {
        this.currentHP = hp;
        this.maxHP = maxHP;
        this.baseDamage = baseDamage;
        this.baseDefence = baseDefence;
        this.college = college;
        this.buffs = activeBuffs;

        this.goldAvailable = gold;
        this.XP = xp;
    }

    public Integer getGoldAvailable() { return this.goldAvailable; }
    public Integer getTotalGoldEarnt() { return this.totalGoldEarnt; }
    public Integer getXP() { return this.XP; }
    public void setGoldAvailable(Integer gold) { this.goldAvailable = gold; }
    public void setTotalGoldEarnt(Integer gold) { this.totalGoldEarnt = gold; }
    public void addXP(Integer xp) { this.XP += xp; }
    public Integer getHP() { return this.currentHP; }
    public Integer getMaxHP() { return this.maxHP; }
    public Integer getBaseDamage() { return this.baseDamage; }
    public Integer getBaseDefence() { return this.baseDefence; }
    public ArrayList<Buff> getActiveBuffs() { return this.buffs; }
    public void setHP(Integer hp) { this.currentHP = hp; }
    public void move (String direction)
    {

    }
    public void battleShip(target Ship)
    {

    }
    public void attackCollege(college College)
    {

    }
    private Boolean startMinigame()
    {
        return true; //init
    }
}
