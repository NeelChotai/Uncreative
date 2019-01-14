package com.uncreative.game;

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
    private Ship inBattle;
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
        for(Item item : items) { inventory.addItem(item); }
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

    public void move(Pirates.dir direction) {
        if(this.inBattle != null) { System.out.println("Error: OtherShip tried to move in battle."); return; }//Can't move in battle

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
        Location newlocation = Pirates.map[location[0]][location[1]];
        //Can't move out of bounds or inside another ship
        if(newlocation.ship != null || location[0] < 0 || location[0] > Pirates.size - 1 || location[1] < 0 || location[1] > Pirates.size - 1)
        {
            battleShip((OtherShip) newlocation.ship);
        } else {
            this.location = newlocation;
        }
        this.location.ship = this;
    }

    public void battleShip(OtherShip target)
    {
        this.inBattle = target;//Do some UI Stuff??
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

    }

    public void flee() {

    }

    private Boolean startMinigame()
    {

        return true; //init
    }
}
