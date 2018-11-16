package com.uncreative.game;

public class ObjectiveItem implements Item{
	private Objective objective;
	private Integer goldWorth;
	private Integer xpRequired;
	private Integer uses;
	private Building building;

	public ObjectiveItem(Objective objective, Integer gold, Integer xp, Integer uses, Building building) {
		this.objective = objective;
		this.goldWorth = gold;
		this.xpRequired = xp;
		this.uses = uses;
		this.building = building;
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
	
	public Boolean use(Ship ship) {
		for(int i = 0; i < this.objective.prerequisites.length(); i++) {
			if (!this.objective.prerequisites[i].getCompleted())//If not all prerequisites are completed, this objective can't be
			{
				return false;
			}
			if (this.building.location == ship.location) {
				if (this.uses != -1) {
					this.uses--;
				}//if uses = -1, then infinite uses
				if (this.uses == 0) {
					ship.Inventory.removeItem(this);
				}
				return true;
			}
		}
	}

	public Boolean use(Ship ship, Ship s)
	{
		return this.use(ship);
	}
}
