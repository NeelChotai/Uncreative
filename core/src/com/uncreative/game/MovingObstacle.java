package com.uncreative.game;

public class MovingObstacle extends Obstacle{
    private Boolean following;

    public MovingObstacle(Integer damage, String type, Location location, Boolean following){
        super(Integer damage, String type, Location location);
        this.following = following;
    }

    public Boolean getFollowing(){
        return this.following;
    }
    public void toggleFollowing(){
        this.following = this.following && false;
    }

    public void move(String dir){
        Location prev = this.location;
        int[] loc = prev.getLocation();
        this.location.obstacle = null;

        switch (dir){
            case "N":   this.location = Main.map[loc[0]][loc[1] + 1];
                break;
            case "NE":  this.location  = Main.map[loc[0] + 1][loc[1] + 1];
                break;
            case "E":   this.location = Main.map[loc[0] + 1][loc[1]];
                break;
            case "SE":  this.location = Main.map[loc[0] + 1][loc[1] - 1];
                break;
            case "S":   this.location = Main.map[loc[0]][loc[1] - 1];
                break;
            case "SW":  this.location = Main.map[loc[0] - 1][loc[1] - 1];
                break;
            case "W":   this.location = Main.map[loc[0] - 1][loc[1]];
                break;
            case "NW":  this.location = Main.map[loc[0] - 1][loc[1] + 1];
                break;
            default:    throw new InvalidParameterException();
                break;
        }

        if(this.location.obstacle != null) {//Can't move inside another ship
            this.location = prev;
            this.location.obstacle = this;
        }
        this.location.obstacle = this;
    }

    public void followShip(){

    }
}
