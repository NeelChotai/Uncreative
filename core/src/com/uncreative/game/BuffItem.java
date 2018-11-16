package com.uncreative.game;

public class BuffItem implements Item {
    private Integer goldWorth;
    private Integer xpRequired;
    private Integer uses;
    //public Buff buff;

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
        if(ship.activeBuffs.contains(this.buff))
        {
            return false;
        } else {
            if(this.uses != -1) { this.uses--; }//if uses = -1, then infinite uses
            if(this.uses == 0)
            {
                ship.Inventory.removeItem(this);
            }
            ship.activeBuffs.append(this.buff);
            return true;
        }
    }
}