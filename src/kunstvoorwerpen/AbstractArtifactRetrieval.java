package kunstvoorwerpen;


public abstract class AbstractArtifactRetrieval {

  
  /**
   * @param solutionOne     artifact basis
   * @param solutionTwo     artifact om te testen 
   * @return true als solutionOne een score groter dan of gelijk aan solutionTwo heeft
   */
  public boolean dominates(Artifact solutionOne, Artifact solutionTwo) {
    double one_price = solutionOne.getPrice();
    double one_value = solutionOne.getValue();
    double two_price = solutionTwo.getPrice();
    double two_value = solutionTwo.getValue();
    
    return (
        two_price <= one_price && two_value >= one_value)
        && (two_price < one_price || two_value > one_value
     );
    
  }  
  
  
  
  
  
  
  
  
  
  
  

}
