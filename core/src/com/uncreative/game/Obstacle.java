package com.uncreative.game;

public class Obstacle {
    private Integer damage;
    private String type;
    public Location location;

    public Obstacle(Integer damage, String type, Location location) {
        this.damage = damage;
        this.type = type;
        this.location = location;
    }

    public Integer getDamage() {
        return this.damage;
    }
    public String getType() {
        return this.type;
    }
}