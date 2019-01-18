package com.uncreative.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public interface Ship {
    public Integer getHP();
    public Integer getMaxHP();
    public Integer getBaseDamage();
    public Integer getBaseDefence();
    public ArrayList<Buff> getActiveBuffs();
    public void removeBuff(Buff buff);
    public void addBuff(Buff buff);
    public void setHP(Integer hp);
    public Boolean move(Pirates.dir direction);//Inside the game loop, location.ship.remove and newlocation.ship.add to be sorted
    public void attack();
    public void flee();
    public Boolean isInBattle();
    public College getCollegeAllegiance();
}
