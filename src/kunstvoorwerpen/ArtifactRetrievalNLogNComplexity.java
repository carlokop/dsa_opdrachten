package kunstvoorwerpen;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;




/**
 * Implementatie van ArtifactOrdering Interface met O(n log n) tijdscomplexiteit 
 * voor de implementatie van de  getUnbeatedArtifacts methode.
 */
public class ArtifactRetrievalNLogNComplexity implements ArtifactOrdering {
	// See: https://en.wikipedia.org/wiki/Multi-objective_optimization

	/**
	 * maakt een ArtifactRetrievalNLogNComplexity
	 */
	public ArtifactRetrievalNLogNComplexity() {super();}
		

	
	/***
	 *  Vindt de set van "onovertroffen" kunstvoorwerpen uit de lijst van kunstvoorwerpen.
	 *  Dit zijn de kunstvoorwerpen (artifacts) a_u waarvoor er geen ander kunstvoorwerp bestaat a_x
	 *  in de lijst zodat
	 *  
	 *  (a_x.price >= a_u.price >= && a_x.value >= a_u.value) && 
	 *  (a_x.price > a_u.price >= || a_x.value > a_u.value)
	 *  
	 *   Gegeven een input lijst van grootte N, moet deze implementatie een tijdscomplexiteit van
	 *   O(n log n) hebben. 
	 *   
	 *   @param artifacts de art van artifacts 
	 *   @return de set van onovertroffen artifacts
	 *   Complexiteit theta(n log n) doordat er 2x een th(n log n) actie doorlopen wordt
	 */
	@Override
	public Set<Artifact> getUnbeatedArtifacts(Set<Artifact> artifacts) { 

		Set<Artifact> result = new HashSet<>(); 
		
		//heapsort waarbij we hier alle elementen op de pq zetten en later doorlopen
  	    PriorityQueue<Artifact> pq = new PriorityQueue<>(Artifact.PRIJS_WAARDE_COMPARATOR);
        pq.addAll(artifacts);  //th(n log n)    
        
        /**
         * Als we artifacts in lijst zetten beginnen we met het artifact met de laagste prijs en de hoogste waarde
         * Als de prijs gelijk is met de hoogste waarde eerst
         * 
         * Vanwege de sortering kunnen alleen artifacts links van gegeven artifact het artifact overtreffen
         * Door het bijhouden van de reeds hoogste waarde van alle artifacts links van gegeven artifact wordt een artifact niet overwonnen 
         * als waarde groter is dan gegeven max
         */        
        //met hulp van de dominates methode
        Artifact maxValueArtifact = null;
        while (!pq.isEmpty()) {                    //O(n)
          Artifact artifact = pq.poll();           //th(log n)
          if(maxValueArtifact == null || !Artifact.dominates(artifact, maxValueArtifact)) {  //O(1)
            result.add(artifact);                  //th(log n)
            maxValueArtifact = artifact;
          }
       }
        
        //op deze manier hebben we de dominates methode niet nodig. 
        //Zelfde complexiteit maar wel een paar constante operaties sneller
        //O(n log n)
//        double maxValue = Double.NEGATIVE_INFINITY;
//        while (!pq.isEmpty()) {                     //O(n)
//            Artifact artifact = pq.poll();          //O(log n)
//            if (artifact.getValue() > maxValue) {
//              result.add(artifact);                 //(O log n)
//                maxValue = artifact.getValue(); 
//            }
//        }
        
  
        return result;

	}
	

	
	
	/***
	 *   Orden de kunstvoorwerpen score, gedefinieerd als:
	 *    
	 *   score = a.price * priceWeight + a.value * valueWeight
	 *   
	 *   afhankelijk van de in de input gegeven prijs en waarde gewichten.
	 *   
	 *   De sorteer volgorde moet aflopend zijn, zodat het kunstvoorwerp met de hoogste score 
	 *   zich op index 0 in het resultaat bevind.
	 *  
	 *   Gegeven een input lijst van grootte N, moet deze implementatie een tijdscomplexiteit van
	 *   O(n log n) hebben. 
	 *   
	 *   @param artifacts  de art van artifacts 
	 *   @param priceWeight de weging van de prijs
	 *   @param valueWeight de weging van de waarde
     *   @return de gesorteerde set artifacts
	 *   
	 *   Deze methode lijkt hetzelfde als die in de klasse ArtifactRetrievalQuadraticComplexity en is een kopie daarvan
	 *   In deze methode gebruik ik een nieuwe comparitor die vergelijkt op basis van de weging en het op een treeset zet op dezelfde manier als de priorityqueue
	 *   Complexiteit th(n log n)
	 */
	@Override
	public SortedSet<Artifact> getScoreOrderedArtiacts(Set<Artifact> artifacts, int priceWeight, int valueWeight) {
	//handler voor sorteren op basis van de score
      class ArtifactComparator implements Comparator<Artifact> {
        @Override
        public int compare(Artifact artifact, Artifact other) {
            double score_artifact = priceWeight * artifact.getPrice() + valueWeight * artifact.getValue();
            double score_other = priceWeight * other.getPrice() + valueWeight * other.getValue();
            return Double.compare(score_other, score_artifact); 
        }
      }

      SortedSet<Artifact> result = new TreeSet<>(new ArtifactComparator());

      // Voeg alle artifacts toe aan de gesorteerde set
      result.addAll(artifacts);
    
      return result;
	}
	

	/***
	 * Main methode, slechts gebruikt voor het kleinschalig testen van de functionaliteit.
	 * @param args
	 */
	public static void main(String[] args)
	{
		//Artifacts separated by ";" formatted as: artifactID,price,value	
		String solutionsString = "0,0,9;1,1,10;2,2,11;3,3,5;4,4,4";
		Set<Artifact> artifacts = Artifact.createArtifactsFromArtifactsString(solutionsString);
		ArtifactRetrievalQuadraticComplexity artifactRetrieval = new ArtifactRetrievalQuadraticComplexity();
		 Set<Artifact> unbeatedArtifacts = artifactRetrieval.getUnbeatedArtifacts(artifacts);
		 System.out.println("\nUnbeated Artifacts: " + unbeatedArtifacts);
		
	 
		 SortedSet<Artifact> scoredArtifacts = 
				 artifactRetrieval.getScoreOrderedArtiacts(artifacts, 1, 10);
		 System.out.println("\nWeight scored artifacts: " + scoredArtifacts);
	 

	}


}