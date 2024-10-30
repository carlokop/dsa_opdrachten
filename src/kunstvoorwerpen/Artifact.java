package kunstvoorwerpen;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/***
 * Artifact/kunstvoorwerp klasse
 */
public class Artifact   {
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
		System.out.println("geen object van" + object.getClass());
		return false;
	
	}
	
	@Override
	public int hashCode() {
		return this.id;
	}
	
	 /**
     * @param solutionOne     artifact basis
     * @param solutionTwo     artifact om te testen 
     * @return true als solutionOne wordt overtroffen door solutionTwo
     * O(1)
     */
    public static boolean dominates(Artifact artifact, Artifact other) {
      double one_price = artifact.getPrice();
      double one_value = artifact.getValue();
      double two_price = other.getPrice();
      double two_value = other.getValue();
      
      return (
          two_price <= one_price && two_value >= one_value)
          && (two_price < one_price || two_value > one_value
       );
      
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
		 
	/**
	 * Comparator die eerst prijs vergelijkt van laag naar hoog en bij gelijke prijs de waarde van hoog naar laag
	 * Als hiermee gesorteerd wordt komt het artifact met de laagste prijs eerst
	 * Bij gelijke prijs het artifact met de hoogste waarde
	 */
    public static final Comparator<Artifact> PRIJS_WAARDE_COMPARATOR = new Comparator<Artifact>() {
        @Override
        public int compare(Artifact artifact, Artifact other) {
            int prijsVergelijking = Double.compare(artifact.getPrice(), other.getPrice());
            if (prijsVergelijking != 0) {
                return prijsVergelijking; // Lager is beter
            }
            // Bij gelijke prijs, vergelijk op waarde van hoog naar laag
            return Double.compare(other.getValue(), artifact.getValue()); // Omgekeerd, zodat hoger beter is
        }
    };
    

	



  
}