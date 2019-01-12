package com.uncreative.game;

import java.util.ArrayList;

public class Location {
    private int x;
    private int y;
    public Ship ship;
    public ArrayList<Obstacle> obstacles;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        this.ship = null;
        this.obstacles = new ArrayList<Obstacle>();
    }

    public int[] getLocation() {
        int[] loc = {this.x, this.y};
        return loc;
    }
}
