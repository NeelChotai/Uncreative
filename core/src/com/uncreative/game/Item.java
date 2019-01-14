package com.uncreative.game;
public interface Item {
    public Integer getGold();
    public Integer getXP();
    public Integer usesLeft();
    public Boolean use(PlayerShip ship, Ship target);
    public String getName();
}
