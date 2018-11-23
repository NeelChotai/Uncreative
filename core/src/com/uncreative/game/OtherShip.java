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
    private ArrayList<Buff> activeBuffs = new ArrayList<Buff>();
    private Ship inBattle = null;
    public College collegeAllegiance;
    public Inventory inventory;
    private Integer goldForPlunder;
    private Integer XPForPlunder;
    private Item[] itemsForPlunder;
    public Location location;

    public OtherShip(Integer hp, Integer baseDamage, Integer baseDefence, College college, Inventory inv, Integer gold, Integer xp, Item[] items, Location location)
    {
        this.hp = hp;
        this.maxHP = hp;
        this.baseDamage = baseDamage;
        this.baseDefence = baseDefence;
        this.collegeAllegiance = college;
        this.inventory = inv;
        this.goldForPlunder = gold;
        this.XPForPlunder = xp;
        this.itemsForPlunder = items;
        this.location = location;
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

    public void move(String dir) {
        Location prev = this.location;
        int[] loc = prev.getLocation();
        this.location.ship = null;

        switch (dir){
            case "N":   this.location = Main.map[loc[0]][loc[1] + 1];
                        break;
            case "NE":  this.location  = Main.map[loc[0] + 1][loc[1] + 1];
                        break;
            case "E":   this.location = Main.map[loc[0] + 1][loc[1]];
                        break;
            case "SE":  this.location = Main.map[loc[0] + 1][loc[1] - 1];
                        break;
            case "S":   this.location = Main.map[loc[0]][loc[1] - 1];
                        break;
            case "SW":  this.location = Main.map[loc[0] - 1][loc[1] - 1];
                        break;
            case "W":   this.location = Main.map[loc[0] - 1][loc[1]];
                        break;
            case "NW":  this.location = Main.map[loc[0] - 1][loc[1] + 1];
                        break;
            default:    throw new InvalidParameterException();
                        break;
        }

//        if ("N".equals(dir)) {
//            this.location =  Main.map[loc[0]][loc[1] + 1];
//        } else if ("NE".equals(dir)) {
//            this.location  = Main.map[loc[0] + 1][loc[1] + 1];
//        } else if ("E".equals(dir)) {
//            this.location = Main.map[loc[0] + 1][loc[1]];
//        } else if ("SE".equals(dir)) {
//            this.location = Main.map[loc[0] + 1][loc[1] - 1];
//        } else if ("S".equals(dir)) {
//            this.location = Main.map[loc[0]][loc[1] - 1];
//        } else if ("SW".equals(dir)) {
//            this.location = Main.map[loc[0] - 1][loc[1] - 1];
//        } else if ("W".equals(dir)) {
//            this.location = Main.map[loc[0] - 1][loc[1]];
//        } else if ("NW".equals(dir)) {
//            this.location = Main.map[loc[0] - 1][loc[1] + 1];
//        } else {
//            throw new InvalidParameterException();
//        }
        
        if(this.location.ship != null) {//Can't move inside another ship
            this.location = prev;
            this.location.ship = this;
        }
        this.location.ship = this;
    }



    public void attack() {
        Ship target = this.inBattle;
        int damage = this.baseDamage;
        Iterator<Buff> iter = activeBuffs.iterator();
        while(iter.hasNext()) {
            Buff buff = iter.next();
            if (buff.getString().equalsIgnoreCase("attack")) {
                damage += buff.getAmount();
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
