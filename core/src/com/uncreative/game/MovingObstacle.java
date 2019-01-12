package com.uncreative.game;

import java.security.InvalidParameterException;
import java.util.Iterator;

public class MovingObstacle extends Obstacle{
    private Boolean following;

    public MovingObstacle(Integer damage, String type, Location location, Boolean following){
        super(damage, type, location);
        this.following = following;
    }

    public Boolean getFollowing(){
        return this.following;
    }
    public void toggleFollowing(){
        this.following = !this.following;
    }

    public void move(Pirates.dir direction){
        int[] location = this.location.getLocation();
        switch (direction){
            case N:
                location[0] = location[0];
                location[1] = location[1] + 1;
                break;
            case E:
                location[0] = location[0] + 1;
                location[1] = location[1];
                break;
            case S:
                location[0] = location[0];
                location[1] = location[1] - 1;
                break;
            case W:
                location[0] = location[0] - 1;
                location[1] = location[1];
                break;
            default:    throw new InvalidParameterException();
        }
        Location newlocation = Pirates.map[location[0]][location[1]];
        if(newlocation.ship != null) {
            newlocation.ship.setHP(newlocation.ship.getHP() - this.getDamage());
        } else {
            Iterator<Obstacle> iter = newlocation.obstacles.iterator();
            while(iter.hasNext()) {
                if(iter.next().getType() == this.getType()) {//Can't have 2 objects of the same type on top of eachother.
                    return;
                }
            }
            this.location = newlocation;
        }
    }

    public void followShip(PlayerShip ship){
        int dx = this.location.getLocation()[0] - ship.location.getLocation()[0];
        int dy = this.location.getLocation()[1] - ship.location.getLocation()[1];
        if(Math.abs(dx) >= Math.abs(dy)) {//Move towards x
            if(dx > 0) {
                this.move(Pirates.dir.E);
            } else {
                this.move(Pirates.dir.W);
            }
        } else {//Move towards y
            if(dy > 0) {
                this.move(Pirates.dir.N);
            } else {
                this.move(Pirates.dir.S);
            }
        }
    }
}