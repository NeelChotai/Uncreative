package com.uncreative.game;

public class BuffItem implements Item {
    private Integer goldWorth;
    private Integer xpRequired;
    private Integer uses;
    public Buff buff;

    public BuffItem(Integer gold, Integer xp, Integer uses)//, Buff buff)
    {
        this.goldWorth = gold;
        this.xpRequired = xp;
        this.uses = uses;
        //this.buff = buff;
    }
    public Integer getGold()
    {
        return this.goldWorth;
    }
    public Integer getXP()
    {
        return this.xpRequired;
    }
    public Integer usesLeft()
    {
        return this.uses;
    }

    public Boolean use(Ship ship)
    {
        if(ship.getActiveBuffs().contains(this.buff))
        {
            return false;
        } else {
            ship.activeBuffs.append(this.buff);
            if(this.uses != -1) { this.uses--; }//if uses = -1, then infinite uses
            if(this.uses == 0) {
                ship.Inventory.removeItem(this);
            }
            return true;
        }
    }

    public Boolean use(Ship ship, Ship s)
    {
        return this.use(ship);
    }
}