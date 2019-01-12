package com.uncreative.game;

public class Buff {
	private String stat; //Valid stats: { "attack", "defense", "maxHP" }
	private Integer amount;
	public Integer duration;
	private Boolean isInfinite;
	
	public Buff(String stat, Integer amount, Integer duration, Boolean isInfinite) {
		this.stat = stat;
		this.amount = amount;
		this.duration = duration;
		this.isInfinite = isInfinite;
	}
	
	public String getString() {
		return this.stat;
	}
	public Integer getAmount() {
		return this.amount;
	}
	public Boolean isInfinite() { return this.isInfinite; }
}
