package kunstvoorwerpen;

import java.util.HashSet;
import java.util.Set;

/***
 * Artifact/kunstvoorwerp klasse
 */
public class Artifact{
	/** D ID van het kunstvoorwerp*/
	private final int id; 
	/** De prijs van het kunstvoorwerp*/
	private final double price;
	/** De waarde van het kunstvoorwerp*/
	private final double value;
	
	
	public Artifact(int id, double firstElement,double secondElement) {
		this.id = id;
		this.price = firstElement;
		this.value = secondElement;
	}
	
	public int getID() {
		return this.id;
	}
		
	public double getPrice() {
		return this.price;
	}
	
	public double getNegativePrice() {
		return -this.price;
	}
	
	
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Bepaald de score van het Artifact 
	 * @param weightPrice  Gewicht van de prijs 
	 * @param weightValue  Gewicht van de waarde
	 * @return score
	 */
	public double bepaalScore(double weightPrice, double weightValue) {
      return weightPrice * price + weightValue * value;
    }
	
	public String toStringKeyValues() {
	  return "(" + this.price + ", " + this.value + ")";
	}
  
	

	public String toString() {
		String result = "\n<ARTIFACT>";
		result += "id: " + this.id + ", ";
		result +="price: " + this.price;
		result += ", ";
		result += "value: " + this.value;
		result += "</ARTIFACT>";
		return result;
	}
	
	 @Override
	public boolean equals(Object object) {
		if (object instanceof Artifact) {
			Artifact artifact2 = (Artifact) object;
			return this.getID() == artifact2.getID();			
		}
		return false;
	
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}
	
	/***
	 * Dit is een convenience methode voor het snel creëren van een set artifacts.
	 * @param pairsString
	 * @return
	 */
	public static Set<Artifact> createArtifactsFromArtifactsString(String pairsString) {
		 Set<Artifact> artifacts = new HashSet<>();
		 String[] solutionsStrings = pairsString.split(";");
		
		 for(String solutionString : solutionsStrings) {
			 String[] parts = solutionString.split(",");
			 if(!(parts.length == 3)) {
				 throw new RuntimeException("Error: unexpected number of parts: " 
			 + parts.length + " expected three");
			 }
			 Artifact artifact = new Artifact(Integer.parseInt(parts[0]), Double.parseDouble(parts[1]),
					 Double.parseDouble(parts[2]));
			 artifacts.add(artifact);
		 }
		System.out.println("Artifacts: " + artifacts);
	
		return artifacts;
	}
}