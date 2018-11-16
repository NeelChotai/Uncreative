public interface Item {
  private Integer goldWorth;
  private Integer xpRequired;
  private Integer uses;
  
  public Integer getGold();
  public Integer getXP();
  public Integer usesLeft();
  public Boolean use();
}
