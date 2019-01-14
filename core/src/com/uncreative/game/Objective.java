package com.uncreative.game;

public class Objective {
	private String description;
	private Objective[] prerequisites;
	private boolean completed;
	private Integer xpReward;
	
	public Objective(String desc, Objective[] prereq, Integer reward) {
		this.description = desc;
		this.prerequisites = prereq;
		this.completed = false;
		this.xpReward = reward;
	}
	
	public String getDescription() {
		return this.description;
	}
	public Objective[] getPrerequisites() {
		return this.prerequisites;
	}
	public Boolean getCompleted() {
		return this.completed;
	}
	public Integer getReward() {
		return this.xpReward;
	}
	public void complete() {
		this.completed = true;
	}
}
