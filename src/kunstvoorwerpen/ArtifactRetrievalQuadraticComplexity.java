package kunstvoorwerpen;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Implementatie van ArtifactOrdering Interface met kwadratische tijdscomplexiteit 
 * voor de implementatie van de  getUnbeatedArtifacts methode.
 */
public class ArtifactRetrievalQuadraticComplexity  implements ArtifactOrdering {
	// See: https://en.wikipedia.org/wiki/Multi-objective_optimization

  
	/**
	 * Maakt een ArtifactRetrievalQuadraticComplexity
	 */
	public ArtifactRetrievalQuadraticComplexity() {super();}
		
	/***
	 *  Vindt de set van "onovertroffen" kunstvoorwerpen uit de lijst van kunstvoorwerpen.
	 *  Dit zijn de kunstvoorwerpen (artifacts) a_u waarvoor er geen ander kunstvoorwerp bestaat a_x
	 *  in de lijst zodat
	 *  
	 *  dit klopt niet!!!
	 *  (a_x.price >= a_u.price >= && a_x.value >= a_u.value) && 
	 *  (a_x.price > a_u.price >= || a_x.value > a_u.value)
	 *  
	 *   Gegeven een input lijst van grootte N, moet deze implementatie een tijdscomplexiteit van
	 *   (maximaal) O(n^2) hebben. 
	 */
	@Override
	public Set<Artifact> getUnbeatedArtifacts(Set<Artifact> artifacts){
		//TODO: FIXME
		Set<Artifact> result = new HashSet<>();
		
		//O(n2)
	    for (Artifact artifact : artifacts) {  //O(n)
	        boolean isOvertroffen = false;  // Veronderstel dat het artifact onovertroffen is

	        for (Artifact other : artifacts) {  //O(n)
	 
	            //Controleer of het andere artifact het huidige artifact overtreft
	            if(Artifact.dominates(artifact,other)) {
	               isOvertroffen = true;
	               break;  //stop de innerloop item is overtroffen
	            };
	        }

	        //Voeg onovertroffen artifact toe aan de hashset
	        if (!isOvertroffen) {
	            result.add(artifact);
	        }
	    }
	    		
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

     *   @param artifacts  de art van artifacts 
     *   @param priceWeight de weging van de prijs
     *   @param valueWeight de weging van de waarde
     *   @return de gesorteerde set artifacts
     *   
     *   Deze methode lijkt hetzelfde als die in de klasse ArtifactRetrievalQuadraticComplexity en is een kopie daarvan
     *   In deze methode gebru
	 */
	@Override
	public SortedSet<Artifact> getScoreOrderedArtiacts(Set<Artifact> artifacts, int priceWeight, int valueWeight) {
               
	  //handler voor sorteren op basis van de score
	  class ArtifactComparator implements Comparator<Artifact> {
        @Override
        public int compare(Artifact artifact, Artifact other) {     //O(1)
            double score_artifact = priceWeight * artifact.getPrice() + valueWeight * artifact.getValue();
            double score_other = priceWeight * other.getPrice() + valueWeight * other.getValue();
            return Double.compare(score_other, score_artifact); 
        }
      }

      SortedSet<Artifact> result = new TreeSet<>(new ArtifactComparator());

      // Voeg alle artifacts toe aan de gesorteerde set
      result.addAll(artifacts);  //O(n log n) 
    
      return result;
    
	}
	

	/***
	 * Main methode, slechts gebruikt voor het kleinschalig testen van de functionaliteit.
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Artifacts separated by ";" formatted as: artifactID,price,value	
		String solutionsString = "0,0,9;1,1,10;2,2,11;3,3,5;4,4,4";
		Set<Artifact> artifacts = Artifact.createArtifactsFromArtifactsString(solutionsString);
		ArtifactRetrievalQuadraticComplexity artifactRetrieval = new ArtifactRetrievalQuadraticComplexity();
		 Set<Artifact> unbeatedArtifacts = 
				 artifactRetrieval.getUnbeatedArtifacts(artifacts);
		 System.out.println("\nUnbeated Artifacts: " + unbeatedArtifacts);
		
		 SortedSet<Artifact> scoredArtifacts = 
				 artifactRetrieval.getScoreOrderedArtiacts(artifacts, 1, 10);
		 System.out.println("\nWeight scored artifacts: " + scoredArtifacts);
	}


}