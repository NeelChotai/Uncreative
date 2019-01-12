package com.uncreative.game;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

public class OtherShip implements Ship {
    private String[] dir = {"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
    private Integer hp;
    private Integer maxHP;
    private Integer baseDamage;
    private Integer baseDefence;
    private ArrayList<Buff> activeBuffs;
    private Ship inBattle = null;
    public College collegeAllegiance;
    private Integer goldForPlunder;
    private Integer XPForPlunder;
    private Item[] itemsForPlunder;
    public Location location;

    public OtherShip(Integer hp, Integer baseDamage, Integer baseDefence, College college, Integer gold, Integer xp, Item[] items, Location location)
    {
        this.hp = hp;
        this.maxHP = hp;
        this.baseDamage = baseDamage;
        this.baseDefence = baseDefence;
        this.collegeAllegiance = college;
        this.goldForPlunder = gold;
        this.XPForPlunder = xp;
        this.itemsForPlunder = items;
        this.location = location;
        this.activeBuffs = new ArrayList<Buff>();
        this.activeBuffs.addAll(college.getBuffs());
    }

    public Integer getHP() {
        return this.hp;
    }

    public Integer getMaxHP() {
        return this.maxHP;
    }

    public Integer getBaseDamage() {
        return this.baseDamage;
    }

    public Integer getBaseDefence() {
        return this.baseDefence;
    }

    public ArrayList<Buff> getActiveBuffs() {
        return activeBuffs;
    }

    public void removeBuff(Buff buff) {
        this.activeBuffs.remove(buff);
    }

    public void addBuff(Buff buff) {
        this.activeBuffs.add(buff);
    }

    public College getCollegeAllegiance() {
        return this.collegeAllegiance;
    }

    public void setHP(Integer hp) {
        if(hp > this.maxHP) {
            this.hp = this.maxHP;
        } else {
            this.hp = hp;
        }
    }

    public Boolean isInBattle(){
        return this.inBattle != null;
    }

    public void setInBattle(Ship target) {
        this.inBattle = target;
    }

    public void move(Pirates.dir direction) {
        if(this.inBattle != null) { System.out.println("Error: OtherShip tried to move in battle."); return; }//Can't move in battle

        int[] location = this.location.getLocation();
        switch (direction){
            case N:
                location[0] = location[0];
                location[1] = location[1] + 1;
                break;
            case E:
                location[0] = location[0] + 1;
                location[1] = location[1];
                break;
            case S:
                location[0] = location[0];
                location[1] = location[1] - 1;
                break;
            case W:
                location[0] = location[0] - 1;
                location[1] = location[1];
                break;
            default:    throw new InvalidParameterException();
        }
        //Can't move out of bounds or inside another ship
        if(location[0] < 0 || location[0] > Pirates.size - 1 || location[1] < 0 || location[1] > Pirates.size - 1)
        {
            return;
        }

        Location newlocation = Pirates.map[location[0]][location[1]];
        if(newlocation.ship != null){
            if(newlocation.ship instanceof PlayerShip) {
                ((PlayerShip) newlocation.ship).battleShip(this);
            } else if(newlocation.ship.getCollegeAllegiance() != this.collegeAllegiance){
                this.inBattle = newlocation.ship;
                ((OtherShip) newlocation.ship).inBattle = this;
                attack();
            }
        } else {
            newlocation.ship = this;
            this.location.ship = null;
            this.location = newlocation;
        }
    }

    public void attack() {
        Ship target = this.inBattle;
        int damage = this.baseDamage;

        Iterator<Buff> iter = activeBuffs.iterator();//Check for damage buffs
        while(iter.hasNext()) {
            Buff buff = iter.next();
            if (buff.getString().equalsIgnoreCase("attack")) {
                damage += buff.getAmount();
            }
        }

        iter = target.getActiveBuffs().iterator();//Check target for defense buffs
        while(iter.hasNext()){
            Buff buff = iter.next();
            if(buff.getString().equalsIgnoreCase("defense")) {
                damage -= buff.getAmount();
            }
        }

        damage -= target.getBaseDefence();
        if(damage < 0) { damage = 0; }
        target.setHP(target.getHP() - damage);
    }
    public void flee() {
        this.inBattle = null;
    }
}
