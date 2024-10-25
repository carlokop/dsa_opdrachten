package kunstvoorwerpen;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;




/**
 * Implementatie van ArtifactOrdering Interface met O(n log n) tijdscomplexiteit 
 * voor de implementatie van de  getUnbeatedArtifacts methode.
 */
public class ArtifactRetrievalNLogNComplexity extends AbstractArtifactRetrieval implements ArtifactOrdering {
	// See: https://en.wikipedia.org/wiki/Multi-objective_optimization

	
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
	 */
	@Override
	public Set<Artifact> getUnbeatedArtifacts(Set<Artifact> artifacts){
		//TODO: FIXME
		Set<Artifact> result = new HashSet<>();
		ArrayList<Artifact> priceList = new ArrayList<>();
		ArrayList<Artifact> valueSortedList = new ArrayList<>();       
		
		//zet artifacts op beide arrays
		for (Artifact artifact : artifacts) {     //O(n)
		  priceList.add(artifact);
		  valueSortedList.add(artifact);
		}
		
		//sorteer priceList op basis van prijs laag naar hoog en waarde van hoog naar laag
		//O(n)
        RadixArtifact.radixSort(priceList, true, true);
        RadixArtifact.radixSort(priceList, false, false);

        //sorteer valueSortedList op basis van waarde hoog naar laag en price van lag naar hoog
        RadixArtifact.radixSort(valueSortedList, false, false);
        RadixArtifact.radixSort(valueSortedList, true, true);
        

        
        //het element met de laagste prijd en hoogste waarde is sowieso onoverwonnen
        if(priceList.size() >= 1) {
          result.add(priceList.get(0));
        }
        //Het eerste element op de op waarde gesorteede lijst kan ook niet overwonnen zijn
        //De hashset staat geen duplicaten toe dus als dit hetzelfde artifact is als eerst element in de sortedlist wordt dit overgeslagen
        if(valueSortedList.size() >= 1) {
          result.add(valueSortedList.get(0));
        }

        //doorloop de gesorteerde lijst
        //O(n log n)
        for(int i=1; i<priceList.size(); i++) {    //O(n)          
          Artifact artifact = priceList.get(i);
         
          //in de reeds gesorteerde priceList is er een goede kans dat het vorige artifact op de array dit artifact overtreft
          //als het vorige element dit element overtreft zijn we in O(1) klaar en hoeven we niet verder te zoeken
          if(dominates(priceList.get(i-1), artifact)) {
            result.add(priceList.get(0));
            break;
          }
          
          //zoek artifact in de op waarde gesorteerde lijst
          int end = valueSortedList.size()-1;
          int index = binarySearchValueSortedArtifact(valueSortedList, 0 ,end, artifact);  //O(log n)                
          
          //als het artifact overtroffen is moet een element dat het artifact overtreft direct links ernaast op de array staan
          if(index >= 1) { //
           
            Artifact other = priceList.get(index-1);

            if(!dominates(artifact, other)) {
              result.add(artifact);
            }

          }
        
        }
       
		return result;
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
	    if(doel.getValue() >= artifact.getValue() || (doel.getValue() == artifact.getValue() && doel.getPrice() < artifact.getPrice())) {
	      //zoek links
	      return binarySearchValueSortedArtifact(list, start, mid-1, doel);
	    } else {
	      //zoek rechts
	      return binarySearchValueSortedArtifact(list, mid+1, end, doel);
	    }
	    
	      
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
	 *   Deze methode lijkt hetzelfde als die in de klasse ArtifactRetrievalQuadraticComplexity en is een kopie daarvan
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