package opgave;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class Restaurant implements ReservationOperations {
  
  /**
   * @param key (Integer) is het tijdslot
   * @param value (Node> een node element die initieel null zal zijn en bij creatie als key een kopie van hat tijdslot zal bevatten omdat de get methode alleen de value returned 
   */
  private TreeMap<Integer, Node<Integer,Reservation>> tijdsloten = new TreeMap<>();
  
  class Node<K,V> {
    K key;
    V value = null;     
    
    Node(K key) {
      this.key = key;
    }
    
    Node(K key,V value) {
      this.key = key;
      this.value = value;
    }
    
    protected K getKey() {return this.key;}
    protected V getValue() {return this.value;}
    protected void setValue(V value) {this.value = value;}
  }
  

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
    int tijdslot = time / 100 * 100;
    Node<Integer,Reservation> node = new Node<>(tijdslot);
    tijdsloten.put(tijdslot,node);
  }

  /**
   * Zoek naar een reservering op het aangegeven tijdslot.
   * @param time gewenst tijdslot in formaat hhmm
   * @return null als het tijdslot niet bestaat of niet vrij is
   * instantie van Reservation als tijdslot bestaat en gereserveerd is
   */
  @Override
  public Reservation search(int time) {
    Node<Integer,Reservation> node = tijdsloten.get(time);
    return node.getValue();
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
   * Complexiteit O(n logn)
   */
  @Override
  public int findClosestAvailableTime(int time) {
    //Voor de opgegeveven tijd is er een tijdslot en dit is beschikbaar
    Node<Integer,Reservation> node = tijdsloten.get(time);
    if(node != null && node.getValue() == null) {
      return time;
    }
    
    Integer lowertime = searchClosestAvailableLower(time);
    Integer highertime = searchClosestAvailableHigher(time);
    
    //volgeboekt
    if(lowertime == -1 && highertime == -1) {
      return -1;
    }
    
    int distLower = Math.abs(time-lowertime);
    int distHigher = Math.abs(time-highertime);
    
    if(highertime == -1) { return lowertime; } 
    return distLower <= distHigher && distLower != -1 ? lowertime : highertime;
    
  }
  
  /**
   * Zoekt naar een beschikbaar tijdslot die lager is dan gegeven time
   * Complexity O(n logn) omdat we steeds het dichtsbijzijnde element zoeken die potentieel niet beschikbaar is en de hele lijst kleiner dan time doorlopen moet worden
   * @param time gewenste tijdstop
   * @return tijdstip beschikbaar
   */
  private int searchClosestAvailableLower(int time) {
    Integer lowerKey = tijdsloten.lowerKey(time);
    while (lowerKey != null) {
      Node<Integer, Reservation> node = tijdsloten.get(lowerKey);
      if (node != null && node.getValue() == null) {
          return lowerKey; 
      }
      lowerKey = tijdsloten.lowerKey(lowerKey); 
    }
    return -1;
  }
  
  /**
   * Zoekt naar een beschikbaar tijdslot die hoger is dan gegeven time
   * Complexity O(n logn) omdat we steeds het dichtsbijzijnde element zoeken die potentieel niet beschikbaar is en de hele lijst groter dan time doorlopen moet worden
   * @param time gewenste tijdstop
   * @return tijdstip beschikbaar
   */
  private int searchClosestAvailableHigher(int time) {
    Integer higherKey = tijdsloten.higherKey(time);
    while (higherKey != null) {
      Node<Integer, Reservation> node = tijdsloten.get(higherKey);
      if (node != null && node.getValue() == null) {
          return higherKey; 
      }
      higherKey = tijdsloten.higherKey(higherKey);
    }
    return -1;
  }
  

  /**
   * Boek het opgegeven tijdslot met de opgegeven naam.
   * Het tijdslot moet bestaan en vrij zijn, anders wordt er niet gereserveerd.
   * @param time tijdslot in formaat hhmm (bijv. 800 voor 08:00, 1330 voor 13:30)
   * @param name naam voor reservering (bijv. "Smit")
   */
  @Override
  public void book(int time, String name) {
    //zoek dichtsbijzijnde beschikbaar tijdslot
    int tijdslot = findClosestAvailableTime(time);
    if(tijdslot != -1) {
      Node<Integer,Reservation> node = tijdsloten.get(tijdslot);
      node.setValue(new Reservation(time,name));
    };
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
    StringBuilder str = new StringBuilder("Reserveringen:\n");
    Iterator<Map.Entry<Integer, Node<Integer, Reservation>>> iterator = tijdsloten.entrySet().iterator();
    
    while (iterator.hasNext()) {
      Map.Entry<Integer, Node<Integer, Reservation>> entry = iterator.next();
      Integer tijdslot = entry.getKey();
      Node<Integer, Reservation> node = entry.getValue();
     
      //tijdslot notatie
      int uren = tijdslot / 100;
      String minuten = String.format("%02d", tijdslot % 100);
      str.append("Tijdslot: ")
      .append(uren + ":" + minuten);
      
      //reservering
      Reservation element = node.getValue();
      if(element == null) {
        str.append(", Geen reservering")
        .append("\n");
      }
      else {
        str.append(", " + element.toString());
      }
    }
    return str.toString();
  }
  
  
  /**
   * Geeft het aantal tijdsloten
   * @return het aantal tijdsloten
   */
  public int aantalTijdsloten() {
    return tijdsloten.size();
  }
  

  
  

}
