package opgave;

import java.util.Iterator;
import java.util.TreeSet;


public class Restaurant implements ReservationOperations {
  
  private TreeSet<Reservation> tijdsloten = new TreeSet<>();
  

  /**
   * Maak het opgegeven tijdslot aan.
   * @param time tijdslot in formaat hhmm (bijv. 800 voor 08:00, 1400 voor 14:00)
   */
  @Override
  public void insert(int time)  {    
    if(time < 800 || time > 2259) {
      return;
    }
    //tijdslot moet heel uur zijn afgerond naar beneden
    int tijd = time / 100 * 100;
    Reservation reservation = new Reservation(tijd,null);
    tijdsloten.add(reservation);
  }


  /**
   * Zoekt naar een tijdslot 
   * Complexity O(log n + log n)
   * @return de reservering op het tijdslot of null
   */
  @Override
  public Reservation search(int time) {
    Reservation res = new Reservation(time, null);
    if(tijdsloten.contains(res)) {      //contains O(log n)
      return tijdsloten.ceiling(res);   //ceiling O(log n)
    }
    return null;
  }

  /**
   * Zoek het dichtstbijzijnde beschikbare tijdslot bij de opgegeven tijd.
   * Als de exacte tijd niet beschikbaar is,
   * zoek dan de dichtstbijzijnde beschikbare tijd.
   * Als er twee tijdsloten worden gevonden die even ver
   * van de gegeven tijd liggen (één eerder en één later),
   * retourneer dan de eerdere.
   * Als geen tijdslot beschikbaar is, returneer dan -1.
   * @param time gewenst tijdslot in formaat hhmm
   * @return eerst beschikbaar tijdslot als een tijdslot kan worden gevonden
   * -1 als geen tijdslot kan worden gevonden
   */
  @Override
  public int findClosestAvailableTime(int time) {
    Reservation res = new Reservation(time, null);
    //opgegeven tijd is beschikbaar
    if(tijdsloten.contains(res)) {  
      System.out.println(tijdsloten.ceiling(res));
    }
    //Vind dichtsbijzijnde tijd dit moet recursief
    
    //Dichtsbijzijnde tijd even ver van elkaar, geef dan de eerdere tijd terug
    
    //anders geef de tijd die het dichtsbij ligt
    return time;

  }

  /**
   * Boek het opgegeven tijdslot met de opgegeven naam.
   * Het tijdslot moet bestaan en vrij zijn, anders wordt er niet gereserveerd.
   * @param time tijdslot in formaat hhmm (bijv. 800 voor 08:00, 1330 voor 13:30)
   * @param name naam voor reservering (bijv. "Smit")
   */
  @Override
  public void book(int time, String name) {
    //zoek dichtsbijzijnd beschikbaar tijdslot
    int tijdslot = findClosestAvailableTime(time);
    if(tijdslot != -1) {
      //niet volgeboekt reservering kan gemaakt worden
      Reservation res = tijdsloten.ceiling(new Reservation(tijdslot, null));
      res.setNaam(name);
    }       
  }

  @Override
  public void printReservationsInOrder(boolean all) {
    
  }

  @Override
  public void cancel(int time) {
    // TODO Auto-generated method stub
    
  }
  
  /**
   * Geeft het overzicht aan reserveringen
   * @return alle tijdsloten enreserveringen
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Reserveringen:\n");
    Iterator<Reservation> iterator = tijdsloten.iterator();
    while (iterator.hasNext()) {
        Reservation element = iterator.next();
        int uren = element.getTijd() / 100;
        String minuten = String.format("%02d", element.getTijd() % 100);
        sb.append("Tijdslot: " + uren + ":" + minuten)
        .append(", Reservering: ")
        .append(element.getNaam() != null ? element.getNaam() : "Geen reservering")
        .append("\n");
    }
    return sb.toString();
}
  
  /**
   * Geeft het aantal tijdsloten
   * @return het aantal tijdsloten
   */
  public int aantalTijdsloten() {
    return tijdsloten.size();
  }
  

  
  

}
