package com.uncreative.game;

public class Objective {
	private String description;
	private Objective prerequisites;
	private boolean completed;
	private Integer expReward;
	
	public Objective(String desc, Objective prereq, Integer reward) {
		this.description = desc;
		this.prequisites = prereq;
		this.completed = False;
		this.expReward = reward;
	}
	
	public String getDescription() {
		return this.description;
	}
	public Objective getPrerequisites() {
		return this.prerequisites;
	}
	public Boolean getCompleted() {
		return this.completed;
	}
	public Integer getReward() {
		return this.expReward;
	}
	public void complete() {
		this.completed = True;
	}
}
