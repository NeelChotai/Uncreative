package com.uncreative.game;

import java.util.Random;

public class DamageItem implements Item{
	private Integer damageAmount;
	private Integer hitChance;
    private Integer goldWorth;
    private Integer xpRequired;
    private Integer uses;

	public DamageItem(Integer damage, Integer chance, Integer gold, Integer xp, Integer uses) {
		this.damageAmount = damage;
		this.hitChance = chance;
		this.goldWorth = gold;
		this.xpRequired = xp;
		this.uses = uses;
	}

	public Integer getDamage() {
		return this.damageAmount;
	}
	public Integer getHitChance() {
		return this.hitChance;
	}
	public Integer getGold() {
		return this.goldWorth;
	}
	public Integer getXP() {
		return this.xpRequired;
	}
	public Integer usesLeft() {
		return this.uses;
	}

	public Boolean use(PlayerShip ship, Ship target) {
        if(this.uses != -1) { this.uses--; }//if uses = -1, then infinite uses
        if(this.uses == 0)
        {
            ship.inventory.items.remove(this);
        }
        Random rand = new Random();
        Integer i = rand.nextInt(100);
        if(i < this.hitChance)//HIT
        {
            target.setHP(target.getHP() - this.getDamage());//Items ignore defense!
            return true;
        }
        else
        {
            return false;
        }
	}
}
