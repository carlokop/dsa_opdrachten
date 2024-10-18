package opgave;
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
   */
  public int getTijd() {
    return tijd;
  }
  
  /**
   * Geeft de naam van de resrevering
   * @return de naam
   */
  public String getNaam() {
    return naam;
  }
  
  /**
   * Sets de naam van de resrevering
   * @param de naam
   */
  public void setNaam(String naam) {
    this.naam = naam;
  }


  /**
   * Vergelijkt welke reservering de eerste tijd heeft
   * @return kleiner dan 0 als de tijd van this kleiner is
   */
  @Override
  public int compareTo(Reservation res) {
    return Integer.compare(this.tijd,res.tijd);
  }
  
  /**
   * Geeft een string representatie van het object
   * @return "Reservering: naam  tijd;
   */
  @Override
  public String toString() {
    return "Reservering: " + tijd + " " + naam + "\n";
  }
  
  /**
   * Vergelijkt naam en tijd .
   * @return true als zelfde naam en tijd
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Reservation res = (Reservation) o;
    return tijd == res.getTijd() && naam.equals(res.naam);
  }
  


  

}
