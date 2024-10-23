package restaurant;
public class Reservation implements Comparable<Reservation> {
  
  private int tijd;
  private String naam;

  /**
   * Maakt een reservering
   * @param tijd    tijd van de reservering
   * @param naam    naam van de reservering
   */
  public Reservation(int tijd, String naam) {
    this.tijd = tijd;
    this.naam = naam;
  }
  
  
  /**
   * geeft de tijd van de reservering
   * @return  de tijd
   * Complexiteit O(1)
   */
  public int getTijd() {
    return tijd;
  }
  
  /**
   * Geeft de naam van de resrevering
   * @return de naam
   * Complexiteit O(1)
   */
  public String getNaam() {
    return naam;
  }
  
  /**
   * Vergelijkt welke reservering de eerste tijd heeft
   * @return kleiner dan 0 als de tijd van this kleiner is
   * Complexiteit O(1) hier vergelijken we alleen twee waarden en doorlopen geen lijst
   */
  @Override
  public int compareTo(Reservation res) {
    return Integer.compare(this.tijd,res.tijd);
  }
  
  /**
   * Geeft een string representatie van het object
   * @return Reservering: naam  hh:mm;
   * Complexiteit O(1) 
   */
  @Override
  public String toString() {
    int uren = tijd / 100;
    String minuten = String.format("%02d", tijd % 100);
    return "Reservering: " + naam + " om " + uren + ":" + minuten +"\n";
  }
  
  /**
   * Vergelijkt naam en tijd .
   * @return true als zelfde naam en tijd
   * Complexiteit O(1)
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Reservation res = (Reservation) o;
    return tijd == res.getTijd() && naam.equals(res.naam);
  }
  

  

}
