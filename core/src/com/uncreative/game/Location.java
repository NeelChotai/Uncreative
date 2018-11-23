package com.uncreative.game;

public class Location {
    private Integer x;
    private Integer y;
    public Ship ship;
    public Building building;
    public Obstacle obstacle;

    public Location (Integer x, Integer y)
    {
        this.x = x;
        this.y = y;
    }

    public Integer[] getLocation()
    {
         Integer[] location = {this.x, this.y} ;
         return location;
    }
}
