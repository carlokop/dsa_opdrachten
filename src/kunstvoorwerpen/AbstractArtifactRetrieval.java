package kunstvoorwerpen;

import java.util.List;

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
  
  /**
   * Geeft aan of artifact wint van een other artifact
   * @param artifact base line artifact
   * @param other    te testen artifact
   * @return         true als een artifact overtreffen wordt het other artifact
   */
  public boolean overtroffen(Artifact artifact, Artifact other) {
    return (
        other.getPrice() <= artifact.getPrice() && other.getValue() >= artifact.getValue())
        && (other.getPrice() < artifact.getPrice() || other.getValue() > artifact.getValue()
     );
  }
  
  
  /**
   * Zoekt naar een Artifact in een lijst en geeft de index
   * @param list     te doorzoeken lijst moet op waarde gesorteerd zijn
   * @param start    start index
   * @param end      end index
   * @param doel     het te vinden artifact
   * @return         de index van het gevonden artifect of -1 al niet gevonden
   */
  public int binarySearchValueSortedArtifact(List<Artifact> list, int start, int end, Artifact doel) {  
    //niet gevonden
    if(start > end) {
      return -1;
    }
    
    int mid = start + (end - start) / 2;
    Artifact artifact = list.get(mid);

    //gevonden
    if(doel.equals(artifact)) {
      return mid;
    }
    
    //zoek naar value
    if(doel.getValue() >= artifact.getValue() 
       //|| (doel.getValue() == artifact.getValue() && doel.getPrice() < artifact.getPrice())
        ) {
      //zoek links
      return binarySearchValueSortedArtifact(list, start, mid-1, doel);
    } else {
      //zoek rechts
      return binarySearchValueSortedArtifact(list, mid+1, end, doel);
    }
    
      
  }
  
  
  
  
  
  
  

}
