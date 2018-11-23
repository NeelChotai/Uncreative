//package com.uncreative.game;
//
//public class HealingItem implements Item{
//  private Integer healAmount;
//  private Integer goldWorth;
//  private Integer xpRequired;
//  private Integer uses;
//
//  public HealingItem(Integer heal, Integer gold, Integer xp, Integer uses){
//    this.healAmount = heal;
//    this.goldWorth = gold;
//    this.xpRequired = xp;
//    this.uses = uses;
//  }
//
//  public Integer getHealAmount(){
//    return this.healAmount;
//  }
//  public Integer getGold(){
//    return this.goldWorth;
//  }
//  public Integer getXP(){
//    return this.xpRequired;
//  }
//  public Integer usesLeft(){
//    return this.uses;
//  }
//
//  public Boolean use(Ship ship){
//    ship.setHP(ship.getHP + this.getHealAmount());
//    if(this.uses != -1) { this.uses--; }//if uses = -1, then infinite uses
//    if(this.uses == 0)
//    {
//      ship.Inventory.removeItem(this);
//    }
//  }
//}
