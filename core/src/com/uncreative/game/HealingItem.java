public class HealingItem implements Item{
  private Integer goldWorth;
  private Integer xpRequired;
  private Integer uses;
  private Integer healAmount;
  
  public HealingItem(Integer heal, Integer worth, Integer xp, Integer uses){
    this.healAmount = heal;
    this.goldWorth = worth;
    this.xpRequired = xp;
    this.uses = uses;
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
  
  public Boolean use(){
    return false;
  }
}
