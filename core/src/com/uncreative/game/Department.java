package com.uncreative.game;

public class Department implements Building{
    public String name;
    private Integer healCost;
    public Integer upgradeStatCost;
    public Inventory inventory;
    private final double sellMultiplier = 0.5;
    private Location location;

    public Department(String name, Integer healCost, Integer upgradeStatCost, Item[] items, Location location) {
        this.name = name;
        this.healCost = healCost;
        this.upgradeStatCost = upgradeStatCost;
        this.inventory = new Inventory(512);
        for(Item item : items) {inventory.addItem(item); }
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public Boolean buyItem(Item item, PlayerShip ship) {
        if(!inventory.items.contains(item)) {
            System.out.println("Building " + this.name + " tried to sell item it did not have: " + item.getName());
            return false;
        }
        if(ship.getGoldAvailable() < item.getGold()) {
            return false;
        }
        ship.setGoldAvailable(ship.getGoldAvailable() - item.getGold());
        ship.inventory.addItem(item);
        this.inventory.removeItem(item);
        return true;
    }

    public Boolean sellItem(Item item, PlayerShip ship) {
        if(!ship.inventory.items.contains(item)) {
            return false;
        }
        ship.inventory.removeItem(item);
        this.inventory.addItem(item);
        ship.setGoldAvailable(ship.getGoldAvailable() + (int) (item.getGold()*sellMultiplier));//Sell for 0.5 the value
        return true;
    }

    public Boolean upgradeStat(PlayerShip ship, String stat) {
        if(ship.getGoldAvailable() < this.upgradeStatCost) {
            return false;
        }
        if(!(stat.equals("attack") || stat.equals("defense") || stat.equals("maxHP"))) {
            return false;
        }
        ship.setGoldAvailable(ship.getGoldAvailable() - this.upgradeStatCost);
        Buff buff = new Buff(stat, 1, 1, true);
        ship.addBuff(buff);
        return true;
    }

    public Boolean healShip(PlayerShip ship, Integer amount) {
        if(amount > (ship.getMaxHP() - ship.getHP())) {
            amount = ship.getMaxHP() - ship.getHP();
        }
        if(ship.getGoldAvailable() < amount * this.healCost || amount <= 0) {
            return false;
        }
        ship.setGoldAvailable(ship.getGoldAvailable() - (amount * this.healCost));
        ship.setHP(ship.getHP() + amount);
        return true;
    }

    public Integer getHealCost() {
        return this.healCost;
    }

    public Location getLocation() { return this.location; }
}
