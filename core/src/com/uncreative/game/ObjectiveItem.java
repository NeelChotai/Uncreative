package com.uncreative.game;

public class ObjectiveItem implements Item{
	private Objective objective;
	private Integer goldWorth;
	private Integer xpRequired;
	private Integer uses;
	
	public ObjectiveItem(Objective objective, Integer gold, Integer xp, Integer uses) {
		this.objective = objective;
		this.goldWorth = gold;
		this.xpRequired = xp;
		this.uses = uses;
	}
	
	public Objective getObjective() {
		return this.objective;
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
