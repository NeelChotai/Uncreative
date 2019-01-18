package com.uncreative.game;

import java.util.ArrayList;

public class College implements Building {
    private String name;
    public ArrayList<OtherShip> ships;
    private Integer goldOnCapture;
    private Integer xpOnCapture;
    private Boolean captured;
    private Boolean hostile;
    public ArrayList<Buff> buffs;
    private Integer hp;
    public Location location;

    public College(String name, Integer hp, Integer goldOnCapture, Integer xpOnCapture, Boolean captured, ArrayList<Buff> buffs, Location location) {
        this.name = name;
        this.ships = new ArrayList<OtherShip>();
        this.goldOnCapture = goldOnCapture;
        this.xpOnCapture = xpOnCapture;
        this.captured = captured;
        this.hostile = false;
        this.buffs = new ArrayList<Buff>();
        this.buffs.addAll(buffs);
        this.hp = hp;
        this.location = location;
    }
    public String getName() { return this.name; }

    public Integer getGold() {
        return this.goldOnCapture;
    }

    public Integer getXP() {
        return this.xpOnCapture;
    }

    public Boolean getCaptured() {
        return this.captured;
    }

    public Boolean getHostile() {
        return this.hostile;
    }

    public Location getLocation() { return this.location; }

    public void setCaptured() {
        this.captured = true;
        this.ships.clear();//Kill all of it's ships
        this.hostile = false;
    }

    public Integer getHP() {
        return this.hp;
    }

    public void setHP(Integer hp) {
        if(hp < this.hp) {
            this.hostile = true;
        }
        if(hp <= 0) {
            this.setCaptured();
        } else {
            this.hp = hp;
        }
    }

    public void toggleHostile() {
        this.hostile = !this.hostile;
    }

    public ArrayList<Buff> getBuffs() {
        return this.buffs;
    }

}
