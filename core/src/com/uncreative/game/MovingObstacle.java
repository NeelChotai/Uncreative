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

    public void move(String direction){
        this.location.obstacles.remove(this);

        switch(direction){
            case "up":      ;
            case "down"     ;
            case "left"     ;
            case "right"    ;
            default: throw new IllegalArgumentException("valid directions: 'up' 'down' 'left' 'right'");
        }
    }

    public void followShip(){

    }
}