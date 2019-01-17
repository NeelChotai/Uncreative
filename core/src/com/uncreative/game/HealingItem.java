package com.uncreative.game;

public class HealingItem implements Item{
  private Integer healAmount;
  private Integer goldWorth;
  private Integer xpRequired;
  private Integer uses;
  private String name;
  private Boolean isInfinite;

  public HealingItem(Integer heal, Integer gold, Integer xp, Integer uses, String name, Boolean isInfinite){
    this.healAmount = heal;
    this.goldWorth = gold;
    this.xpRequired = xp;
    this.uses = uses;
    this.name = name;
    this.isInfinite = isInfinite;
  }

  public Integer getHealAmount(){
    return this.healAmount;
  }
  public Integer getGold(){
    return this.goldWorth;
  }
  public Integer getXP(){
    return this.xpRequired;
  }
  public Integer usesLeft(){
    return this.uses;
  }

  public Boolean use(PlayerShip ship, Ship s){
    if(s.getHP() == s.getMaxHP() || this.xpRequired > ship.getXP()) {
      return false;
    }
    s.setHP(s.getHP() + this.getHealAmount());
    if(!this.isInfinite) { this.uses--; }//if uses = -1, then infinite uses
    if(this.uses == 0 && !this.isInfinite)
    {
      ship.inventory.items.remove(this);
    }
    return true;
  }

  public String getName() { return this.name; }
}
