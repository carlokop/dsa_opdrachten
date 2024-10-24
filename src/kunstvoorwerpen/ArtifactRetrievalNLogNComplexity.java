package kunstvoorwerpen;

import java.util.ArrayList;
import java.util.Collections;
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
		ArrayList<Artifact> lijst = new ArrayList<>();
		
		//zet items op een array
//		for (Artifact artifact : artifacts) {
//		  lijst.add(artifact);
//		}
		
		ArrayList<Artifact> unsortedList = new ArrayList<>();
		unsortedList.add(new Artifact(1,2,9.01));
		unsortedList.add(new Artifact(2,2,9));
		unsortedList.add(new Artifact(3,1,8));
		unsortedList.add(new Artifact(4,5,0));
		unsortedList.add(new Artifact(5,3,3));
		unsortedList.add(new Artifact(6,1,10));
		unsortedList.add(new Artifact(7,3,2));
		unsortedList.add(new Artifact(8,2,5));
		unsortedList.add(new Artifact(9,3,1));
		
		//sorteer de array in O(n + N) met radix sort zodat de artifacts met laagste prijs en hoogste value eerst komen
		//sortereing [(price,value),(price,value,(...)]

		//georteerdelijst = [(1, 10), (1, 8), (2, 9), (2, 9), (2, 5), (3, 3), (3, 2), (3, 1), (5, 0)];
		
		//loop door de set met artifacts en doe binary search alleen items aan de linkerkant kunnen overtreffen
		//check eerst 
		
		
		
		StringBuilder str = new StringBuilder();
		for(Artifact artifact: unsortedList) {
		  str.append(artifact.toStringKeyValues());
		}
		
		System.out.println("Unsorted "+ str +"\n");
		
		// Step 1: Sort by value in descending order
        radixSort(unsortedList, true, true);

        // Step 2: Sort by price in ascending order
        radixSort(unsortedList, false, false);
		
		str = new StringBuilder();
        for(Artifact artifact: unsortedList) {
          str.append(artifact.toStringKeyValues());
        }
		
		System.out.println("Sorted "+ str +"\n");

		
		return result;
	}
	
	private void bucketSort(List<Artifact> artifacts) {
	  int n = artifacts.size();
	  
	  @SuppressWarnings("unchecked")
	  ArrayList<Artifact>[] buckets = (ArrayList<Artifact>[]) new ArrayList[n];

	  
	  // Zoek het maximum en minimum van de prijs om de buckets dynamisch te verdelen
      double maxPrice = Double.MIN_VALUE;
      double minPrice = Double.MAX_VALUE;
      
      for (int i = 0; i < n; i++) {
        buckets[i] = new ArrayList<>();
      }
      
      for (Artifact artifact : artifacts) {
        if (artifact.getPrice() > maxPrice) {
            maxPrice = artifact.getPrice();
        }
        if (artifact.getPrice() < minPrice) {
            minPrice = artifact.getPrice();
        }
      }
      
      // Plaats de artifacts in de juiste buckets op basis van hun prijs
      for (Artifact artifact : artifacts) {
          int bucketIndex = (int) ((artifact.getPrice() - minPrice) / (maxPrice - minPrice + 1) * n); 
          buckets[bucketIndex].add(artifact);
      }
      
     // Sorteer elke individuele bucket op basis van prijs (of value indien gewenst)
      for (int i = 0; i < n; i++) {
          Collections.sort(buckets[i], (a1, a2) -> Double.compare(a1.getPrice(), a2.getPrice()));
      }
      
      // Voeg de gesorteerde artifacts samen in de oorspronkelijke lijst
      int index = 0;
      for (int i = 0; i < n; i++) {
          for (Artifact artifact : buckets[i]) {
              artifacts.set(index++, artifact);
          }
      }
      
      
	}
	
	
	// Function to get the maximum price
	//O(n)
    public static int getMax(ArrayList<Artifact> artifacts, boolean isValue) {
        double max = isValue ? artifacts.get(0).getValue() : artifacts.get(0).getPrice();
        for (Artifact artifact : artifacts) {
            double current = isValue ? artifact.getValue() : artifact.getPrice();
            if (current > max) {
                max = current;
            }
        }
        return (int) (max * 100); // Scale to integer (2 decimal places)
    }
	 
 // Counting sort based on price or value
    public static void countingSort(ArrayList<Artifact> artifacts, int exp, boolean isValue, boolean descending) {
        int n = artifacts.size();
        ArrayList<Artifact> output = new ArrayList<>(Collections.nCopies(n, null));
        int[] count = new int[10]; // For digits 0-9

        // Initialize count array as 0
        for (int i = 0; i < 10; i++) {
            count[i] = 0;
        }

        // Count occurrences of digits
        for (Artifact artifact : artifacts) {
            int number = (int) ((isValue ? artifact.getValue() : artifact.getPrice()) * 100); // Scale to integer
            int digit = (number / exp) % 10;
            count[digit]++;
        }

        // Modify count array to store actual positions, for descending sort reverse
        if (descending) {
            for (int i = 8; i >= 0; i--) {
                count[i] += count[i + 1];
            }
        } else {
            for (int i = 1; i < 10; i++) {
                count[i] += count[i - 1];
            }
        }

        // Build the output array based on the count array
        for (int i = n - 1; i >= 0; i--) {
            int number = (int) ((isValue ? artifacts.get(i).getValue() : artifacts.get(i).getPrice()) * 100);
            int digit = (number / exp) % 10;
            output.set(count[digit] - 1, artifacts.get(i));
            count[digit]--;
        }

        // Copy the output array to artifacts
        for (int i = 0; i < n; i++) {
            artifacts.set(i, output.get(i));
        }
    }
	 
    

    
    
 // Radix Sort on price as already implemented (only for price sorting)
 // Radix Sort function
    public static void radixSort(ArrayList<Artifact> artifacts, boolean isValue, boolean descending) {
        int max = getMax(artifacts, isValue); // Get the maximum value for number of digits
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSort(artifacts, exp, isValue, descending);
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
//		String solutionsString = "0,0,9;1,1,10;2,2,11;3,3,5;4,4,4";
//		Set<Artifact> artifacts = Artifact.createArtifactsFromArtifactsString(solutionsString);
//		ArtifactRetrievalQuadraticComplexity artifactRetrieval = new ArtifactRetrievalQuadraticComplexity();
//		 Set<Artifact> unbeatedArtifacts = 
//				 artifactRetrieval.getUnbeatedArtifacts(artifacts);
		 //System.out.println("\nUnbeated Artifacts: " + unbeatedArtifacts);
		
	 
//		 SortedSet<Artifact> scoredArtifacts = 
//				 artifactRetrieval.getScoreOrderedArtiacts(artifacts, 1, 10);
		 //System.out.println("\nWeight scored artifacts: " + scoredArtifacts);
	  
	  ArtifactRetrievalNLogNComplexity a = new ArtifactRetrievalNLogNComplexity();
	  a.getUnbeatedArtifacts(null);
	}


}