package com.uncreative.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import javax.print.attribute.standard.MediaSize;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

public class OtherShip implements Ship {
    private Integer hp;
    private Integer maxHP;
    private Integer baseDamage;
    private Integer baseDefence;
    private ArrayList<Buff> activeBuffs;
    public Ship inBattle = null;
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

    public Integer getGold() { return this.goldForPlunder; }

    public Integer getXP() { return this.XPForPlunder; }

    public Item[] getItems() { return this.itemsForPlunder; }

    public void setHP(Integer hp) {
        if(hp > this.maxHP) {
            this.hp = this.maxHP;
        } else  if(hp <= 0){
            this.hp = hp;
            this.die();
        } else {
            this.hp = hp;
        }
    }

    public Boolean isInBattle(){
        return this.inBattle != null;
    }

    public void setInBattle(Ship target) {
        this.inBattle = target;
        if(target instanceof PlayerShip) {
            ((PlayerShip)this.inBattle).inBattle = this;
        }  else if(target instanceof OtherShip) {
            ((OtherShip)this.inBattle).inBattle = this;
        }
    }

    public Boolean move(Pirates.dir direction) {
        if(this.inBattle != null) { System.out.println("Error: OtherShip tried to move in battle."); return false; }//Can't move in battle
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
        if(location[0] < 0 || location[0] > Pirates.map.length - 1 || location[1] < 0 || location[1] > Pirates.map[0].length - 1)
        {
            return false;
        }

        Location newlocation = Pirates.map[location[0]][location[1]];
        if(newlocation.ship != null){
            if(newlocation.ship instanceof PlayerShip) {
                if(this.getCollegeAllegiance().getHostile()) {
                    ((PlayerShip) newlocation.ship).battleShip(this);
                    return false;
                } else {
                    return false;//Friendly ship, don't move.
                }
            } else if(newlocation.ship.getCollegeAllegiance() != this.collegeAllegiance){
                if(((OtherShip) newlocation.ship).isInBattle()) {
                    return false;// The other ship is busy
                }
                this.inBattle = newlocation.ship;
                ((OtherShip) newlocation.ship).inBattle = this;
                attack();
            }
        } else if(newlocation.building != null) {
            return false;
        } else {
            for(Obstacle obstacle : newlocation.obstacles) {
                this.setHP(this.getHP() - obstacle.getDamage());
            }
            newlocation.ship = this;
            this.location.ship = null;
            this.location = newlocation;
        }
        return true;
    }

    public void attack() {
        Ship target = this.inBattle;
        int damage = this.baseDamage;

        Iterator<Buff> iter = activeBuffs.iterator();//Check for damage buffs
        while(iter.hasNext()) {
            Buff buff = iter.next();
            if (buff.getStat().equalsIgnoreCase("attack")) {
                damage += buff.getAmount();
            }
        }

        iter = target.getActiveBuffs().iterator();//Check target for defense buffs
        while(iter.hasNext()){
            Buff buff = iter.next();
            if(buff.getStat().equalsIgnoreCase("defense")) {
                damage -= buff.getAmount();
            }
        }

        damage -= target.getBaseDefence();
        if(damage < 0) { damage = 0; }
        target.setHP(target.getHP() - damage);
    }
    public void flee() {
        if(this.inBattle instanceof PlayerShip) {
            ((PlayerShip)this.inBattle).inBattle = null;
        } else if(this.inBattle instanceof OtherShip) {
            ((OtherShip) this.inBattle).inBattle = null;
        }
        this.inBattle = null;
    }

    public void die() {
        this.location.ship = null;
        this.collegeAllegiance.ships.remove(this);
        if(this.isInBattle()) {
            if (this.inBattle instanceof PlayerShip) {
                PlayerShip player = (PlayerShip) this.inBattle;
                for (Item item : this.itemsForPlunder) {
                    player.inventory.addItem(item);
                }
                player.addXP(this.XPForPlunder);
                player.setGoldAvailable(player.getGoldAvailable() + this.goldForPlunder);
                player.inBattle = null;
            } else if(this.inBattle instanceof OtherShip) {
                OtherShip ship = (OtherShip) this.inBattle;
                ship.inBattle = null;
            }
            this.inBattle = null;
        }
    }

    public Location getLocation() {
        return this.location;
    }
}
