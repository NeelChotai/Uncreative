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

	public Boolean use(PlayerShip ship) {
		Objective[] prerequisites = this.objective.getPrerequisites();
		for(int i = 0; i < prerequisites.length; i++) {
			if (!prerequisites[i].getCompleted())//If not all prerequisites are completed, this objective can't be
			{
				return false;
			}
		}
		if (this.building.location == ship.location) {
			ship.inventory.items.remove(this);
			return true;
		}
	}

	public Boolean use(PlayerShip ship, Ship s)
	{
		return this.use(ship);
	}
}
