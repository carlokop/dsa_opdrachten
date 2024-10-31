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
	
	/**
	 * Maakt een artifact
	 * @param id               unieke identifier
	 * @param firstElement     prijs
	 * @param secondElement    waarde
	 */
	public Artifact(int id, double firstElement,double secondElement) {
		this.id = id;
		this.price = firstElement;
		this.value = secondElement;
	}
	
	/**
	 * Geeft de id
	 * @return de id
	 */
	public int getID() {
		return this.id;
	}
		
	/**
	 * Geeft de prijs
	 * @return de prijs
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Geeft de negatieve prijs
	 * @return de prijs * -1
	 */
	public double getNegativePrice() {
		return -this.price;
	}
	
	/**
	 * Geeft de waarde
	 * @return de waarde
	 */
	public double getValue() {
		return this.value;
	}
	
	
	/**
	 * Prints de keys en value
	 * @return key value string
	 */
	public String toStringKeyValues() {
	  return "(" + this.price + ", " + this.value + ")";
	}
  
	

	/**
	 * geeft een string representatie van het artifact1
	 */
	@Override
	public String toString() {
		String result = "\n<ARTIFACT>";
		result += "id: " + this.id + ", ";
		result +="price: " + this.price;
		result += ", ";
		result += "value: " + this.value;
		result += "</ARTIFACT>";
		return result;
	}
	
	/**
	 * Vergelijkt twee artifacts
	 * @return true als de id hetzelfde is
	 */
	 @Override
	public boolean equals(Object object) {
		if (object instanceof Artifact) {
			Artifact artifact2 = (Artifact) object;
			return this.getID() == artifact2.getID();			
		}
		System.out.println("geen object van" + object.getClass());
		return false;
	
	}
	
	 /**
	  * Geeft de id als hashcode
	  * @return de hashcode
	  */
	@Override
	public int hashCode() {
		return this.id;
	}
	
	 /**
	  * Vergelijkt twee artifacts en geeft true als het artifact het artifact other overtreft
	  * Prijs en value moeten doubles zijn tussen -Double.MAX_VALUE en Double.MAX_VALUE en geeft anders false terug
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
      double tolerance = 1e-9; // Kleine waarde voor precisie
      
      //fix getal kleiner dan -Double.MAX_VALUE of groter dan Double.MAX_VALUE geeft altijd false
      if (!Double.isFinite(one_price) || !Double.isFinite(one_value) ||
          !Double.isFinite(two_price) || !Double.isFinite(two_value)) {
          return false;
      }
      
      return (
          (two_price <= one_price && two_value >= one_value) &&
          (two_price < one_price || two_value > one_value)
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
                return prijsVergelijking; //Vergelijk op basis van de prijs
            }
            // Bij gelijke prijs, vergelijk op waarde van hoog naar laag
            return Double.compare(other.getValue(), artifact.getValue()); 
        }
    };
    

	



  
}