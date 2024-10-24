package kunstvoorwerpen;


public abstract class AbstractArtifactRetrieval {
  
  protected double WEIGHT_PRICE  = 5;  //dit moet later vanuit sortedSet methode
  protected double WEIGHT_VALUE  = 3;
  
  /**
   *  Deze functie test, gegeven twee kunstvoorwerpen artifactOne en artifactTwo, of artifactOne
  artifactTwo overtreft. Dit is het geval als artifactOne gelijke of grotere waardes heeft voor –
  PRICE (negatieve prijs) en WAARDE en een grotere waarde voor tenminste een van beide
  eigenschappen. (We gebruiken de negatieve prijs, zodat we uniform voor beide
  eigenschappen naar grotere waarden kunnen zoeken, in plaats van ze verschillend te moeten
  behandelen.)
   * @param solutionOne
   * @param solutionTwo
   * @return true als solutionOne een score groter dan of gelijk aan solutionTwo heeft
   */
  public boolean dominates(Artifact solutionOne, Artifact solutionTwo) {
    return solutionOne.bepaalScore(WEIGHT_PRICE, WEIGHT_VALUE) >= solutionTwo.bepaalScore(WEIGHT_PRICE, WEIGHT_VALUE);
  }
  
  
  
  
  
  
  
  
  
  

}
