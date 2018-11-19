package com.uncreative.game;

public class Buff {
	private String stat; //Valid stats: { "attack", "defense", "maxHP" }
	private Integer amount;
	
	public Buff(String stat, Integer amount) {
		this.stat = stat;
		this.amount = amount;
	}
	
	public String getString() {
		return this.stat;
	}
	public Integer getAmount() {
		return this.amount;
	}
}
