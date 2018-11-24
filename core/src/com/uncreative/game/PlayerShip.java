package com.uncreative.game;

import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;

public class PlayerShip implements Ship
{
    private Integer goldAvailable;
    private Integer totalGoldEarnt;
    private Integer XP;

    public PlayerShip(Integer hp, Integer maxHP, Integer baseDamage, Integer baseDefence, Buff[] activeBuffs, Boolean inBattle)
    {

    }

    public Integer getGoldAvailable() { return this.goldAvailable; }
    public Integer getTotalGoldEarnt() { return this.totalGoldEarnt; }
    public Integer getXP() { return this.XP; }
    public void setGoldAvailable(Integer gold) { this.goldAvailable = gold; }
    public void setTotalGoldEarnt(Integer gold) { this.totalGoldEarnt = gold; }
    public void addXP(Integer xp) { this.XP += xp; }
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


