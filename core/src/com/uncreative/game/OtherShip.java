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

    public void move(Main.dir direction) {
        if(this.inBattle != null) { return; }
        Location prev = this.location;
        int[] oldloc = prev.getLocation();
        this.location.ship = null;
        int[] newloc = new int[2];
        switch (direction){
            case N:
                newloc[0] = oldloc[0];
                newloc[1] = oldloc[1] + 1;
                break;
            case NE:
                newloc[0] = oldloc[0] + 1;
                newloc[1] = oldloc[1] + 1;
                break;
            case E:
                newloc[0] = oldloc[0] + 1;
                newloc[1] = oldloc[1];
                break;
            case SE:
                newloc[0] = oldloc[0] + 1;
                newloc[1] = oldloc[1] - 1;
                break;
            case S:
                newloc[0] = oldloc[0];
                newloc[1] = oldloc[1] - 1;
                break;
            case SW:
                newloc[0] = oldloc[0] - 1;
                newloc[1] = oldloc[1] - 1;
                break;
            case W:
                newloc[0] = oldloc[0] - 1;
                newloc[1] = oldloc[1];
                break;
            case NW:
                newloc[0] = oldloc[0] - 1;
                newloc[1] = oldloc[1] + 1;
                break;
            default:    throw new InvalidParameterException();
                break;
        }
        //Can't move out of bounds or inside another ship
        if(this.location.ship != null || newloc[0] < 0 || newloc[0] > Main.size - 1 || newloc[1] < 0 || newloc[1] > Main.size - 1)
        {
            this.location = prev;
        } else {
            this.location = Main.map[newloc[0]][newloc[1]];
        }
        this.location.ship = this;

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
