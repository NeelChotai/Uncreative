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

    public College(String name, Integer goldOnCapture, Integer xpOnCapture, Boolean captured, ArrayList<Buff> buffs) {
        this.name = name;
        this.ships = new ArrayList<OtherShip>();
        this.goldOnCapture = goldOnCapture;
        this.xpOnCapture = xpOnCapture;
        this.captured = captured;
        this.hostile = false;
        this.buffs = new ArrayList<Buff>();
        this.buffs.addAll(buffs);
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

    public void setCaptured() {
        this.captured = true;
        this.ships.clear();//Kill all of it's ships
        this.hostile = false;
    }

    public void toggleHostile() {
        this.hostile = !this.hostile;
    }

    public ArrayList<Buff> getBuffs() {
        return this.buffs;
    }

}
