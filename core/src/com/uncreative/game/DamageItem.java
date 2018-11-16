package com.uncreative.game

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
	
	public Boolean use() {
		return false;
	}
}
