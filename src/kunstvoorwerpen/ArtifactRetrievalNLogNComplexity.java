package kunstvoorwerpen;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;



/**
 * Implementatie van ArtifactOrdering Interface met O(n log n) tijdscomplexiteit 
 * voor de implementatie van de  getUnbeatedArtifacts methode.
 */
public class ArtifactRetrievalNLogNComplexity implements ArtifactOrdering{
	// See: https://en.wikipedia.org/wiki/Multi-objective_optimization

	
	public ArtifactRetrievalNLogNComplexity() {}
		
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
	 */
	@Override
	public Set<Artifact> getUnbeatedArtifacts(Set<Artifact> artifacts){
		//TODO: FIXME
		Set<Artifact> result = new HashSet<>();
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
	 */
	@Override
	public SortedSet<Artifact> getScoreOrderedArtiacts(Set<Artifact> artifacts, int priceWeight, int valueWeight) {
		SortedSet<Artifact> result = new TreeSet<>();
		//TODO: FIXME
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