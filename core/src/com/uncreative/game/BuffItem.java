package com.uncreative.game;

public class BuffItem implements Item {
    private Integer goldWorth;
    private Integer xpRequired;
    private Integer uses;
    public Buff buff;
    private String name;
    private Boolean isInfinite;

    public BuffItem(Integer gold, Integer xp, Integer uses, Buff buff, String name, Boolean isInfinite)
    {
        this.goldWorth = gold;
        this.xpRequired = xp;
        this.uses = uses;
        this.buff = buff;
        this.name = name;
        this.isInfinite = isInfinite;
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

    public Boolean use(PlayerShip ship, Ship s)
    {
        if(s.getActiveBuffs().contains(this.buff) || this.xpRequired > ship.getXP())
        {
            return false;
        } else {
            ship.addBuff(this.buff);
            if(this.isInfinite) { return true; }//if uses = -1, then infinite uses
            this.uses--;
            if(this.uses <= 0) {
                ship.inventory.items.remove(this);
            }
            return true;
        }
    }

    public String getName(){
        return this.name;
    }
}