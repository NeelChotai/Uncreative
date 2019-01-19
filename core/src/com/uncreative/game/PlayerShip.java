package com.uncreative.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import javax.print.attribute.standard.MediaSize;
import java.lang.reflect.Array;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayerShip implements Ship
{
    private String[] direction = { "N", "E", "S", "W", "NE", "SE", "SW", "NW"};
    private Integer goldAvailable;
    private Integer totalGoldEarned;
    private Integer XP;
    private Integer currentHP;
    private Integer maxHP;
    private Integer baseDamage;
    private Integer baseDefence;
    private ArrayList<College> colleges;
    private College initialCollege;
    public Ship inBattle;
    private ArrayList<Buff> buffs;
    public Inventory inventory;
    public Location location;


    public PlayerShip(Integer hp, Integer maxHP, Integer baseDamage, Integer baseDefence, College college, Integer gold, Integer xp, Item[] items, Location location)
    {
        this.currentHP = hp;
        this.maxHP = maxHP;
        this.baseDamage = baseDamage;
        this.baseDefence = baseDefence;
        this.colleges = new ArrayList<College>();
        this.colleges.add(college);
        this.initialCollege = college;
        this.goldAvailable = gold;
        this.totalGoldEarned = gold;
        this.XP = xp;
        this.buffs = new ArrayList<Buff>();
        this.buffs.addAll(college.getBuffs());
        this.inventory = new Inventory(16);
        for(Item item : items) { this.inventory.addItem(item); }
        this.location = location;
    }

    public Integer getGoldAvailable() { return this.goldAvailable; }

    public Integer getTotalGoldEarned() { return this.totalGoldEarned; }

    public Integer getXP() { return this.XP; }

    public void setGoldAvailable(Integer gold) {
        if(this.goldAvailable < gold){
            this.totalGoldEarned += (gold - goldAvailable);
        }
        this.goldAvailable = gold;
    }

    public void addXP(Integer xp) { this.XP += xp; }

    public Integer getHP() { return this.currentHP; }

    public Integer getMaxHP() { return this.maxHP; }

    public Integer getBaseDamage() { return this.baseDamage; }

    public Integer getBaseDefence() { return this.baseDefence; }

    public ArrayList<Buff> getActiveBuffs() { return this.buffs; }

    public void addBuff(Buff buff) {
        this.buffs.add(buff);
        if(buff.getStat().equals("maxHP")) {
            this.maxHP += buff.getAmount();
            this.currentHP += buff.getAmount();
        }
    }

    public void removeBuff(Buff buff){
        this.buffs.remove(buff);
        if(buff.getStat() == "maxHP") {
            this.maxHP -= buff.getAmount();
            setHP(this.currentHP);//Fixes the potential problem of HP > maxHP
        }
    }

    public Boolean isInBattle() { return this.inBattle != null; }

    public College getCollegeAllegiance() {
        return this.initialCollege;
    }

    public void setHP(Integer hp) {
        if(hp <= 0){
            if(this.isInBattle()) {
                ((OtherShip)this.inBattle).inBattle = null;
                this.inBattle = null;
            }
            if(startMinigame()){
                hp = 1;
            }
            else{
                //GAME OVER SCREEN
            }
        }

        if(hp > maxHP) {
            hp = maxHP;
        }
        this.currentHP = hp;
    }

    public Boolean move(Pirates.dir direction) {
        if(this.inBattle != null) { System.out.println("Error: OtherShip tried to move in battle."); return false; }//Can't move in battle

        int[] location = this.location.getLocation();
        switch (direction){
            case N:
                location[1]++;
                break;
            case E:
                location[0]++;
                break;
            case S:
                location[1]--;
                break;
            case W:
                location[0]--;
                break;
            default:    throw new InvalidParameterException();
        }
        if(location[0] < 0 || location[0] > Pirates.map.length - 1 || location[1] < 0 || location[1] > Pirates.map[0].length - 1) {
            return false;
        }
        Location newlocation = Pirates.map[location[0]][location[1]];
        //Can't move out of bounds or inside another ship
        if(newlocation.ship != null)
        {
            battleShip((OtherShip) newlocation.ship);
            return false;
        } else  if(newlocation.building instanceof College){
            College college = (College) newlocation.building;
            if(!college.getCaptured()){
                attackCollege(college);
            }
            return false;
        } else {
            for (Obstacle obstacle : newlocation.obstacles) {
                this.setHP(this.getHP() - obstacle.getDamage());
            }
            newlocation.ship = this;
            this.location.ship = null;
            this.location = newlocation;
        }
        return true;
    }

    public void battleShip(OtherShip target)
    {
        this.inBattle = target;
        College enemyCollege = this.inBattle.getCollegeAllegiance();
        if(!enemyCollege.getHostile()) {
            enemyCollege.toggleHostile();
        }
        target.setInBattle(this);
    }

    public void attack() {
        Ship target = this.inBattle;
        int damage = this.baseDamage;

        Iterator<Buff> iter = buffs.iterator();//Check for damage buffs
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

    public void attackCollege(College college) {
        int damage = this.baseDamage;

        Iterator<Buff> iter = buffs.iterator();//Check for damage buffs
        while(iter.hasNext()) {
            Buff buff = iter.next();
            if (buff.getStat().equalsIgnoreCase("attack")) {
                damage += buff.getAmount();
            }
        }

        college.setHP(college.getHP() - damage);
    }

    public void flee() {
        if(this.isInBattle()) {
            ((OtherShip)this.inBattle).inBattle = null;
        }
        this.inBattle = null;
    }

    public Location getLocation() {
        return this.location;
    }

    private Boolean startMinigame()
    {
        return true;
    }
}
