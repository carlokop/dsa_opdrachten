package restaurant;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class Restaurant implements ReservationOperations {
  
  /**
   * @param key (Integer) is het tijdslot
   * @param value (Node> een node element die initieel null zal zijn en bij creatie als key een kopie van hat tijdslot zal bevatten omdat de get methode alleen de value returned 
   */
  private TreeMap<Integer, Reservation> tijdsloten = new TreeMap<>();
  private int start_tijdslot;
  private int eind_tijdslot;
  
  /**
   * Maakt een restaurant aan en maakt instanties aan van alle tijdsloten waarop gereserveerd kan worden
   * @param start   start tijdstip eerste tijdslot
   * @param eind    eind tijdstip laatste tijdslot
   * Complexiteit O(n log n) Dit heeft te maken met de for loop. n is hierbij het aantal tijdsloten tussen begin en eindtijd
   */
  public Restaurant(int start, int eind) {
    this.start_tijdslot = start;
    this.eind_tijdslot = eind;
    
    //maak alle tijdsloten
    if(Tijd.checkTime(start) && Tijd.checkTime(eind)) {
      LocalTime starttijd = Tijd.maakTijd(start);
      LocalTime eindtijd = Tijd.maakTijd(eind);
      
      for(int i=starttijd.getHour(); i<= eindtijd.getHour(); i++) {  //O(n)
        insert(i*100);  //O(log n)
      }
    }
    
    
  }
  
  /**
   * Maak het opgegeven tijdslot aan.
   * @param time tijdslot in formaat hhmm (bijv. 800 voor 08:00, 1400 voor 14:00)
   * Complexity O(log n) er worden hier een aantal operaties in constante tijd uitgevoerd en een invoegmethode op een gebalanceerde binaire boom
   * De invoegoperatie moet eerst de boom doorlopen tot aan de gevonden key. Dit is gelijk aan de hoogte van de boom ofwel O(log n)
   */
  @Override
  public void insert(int time)  {    
    if(!Tijd.checkTime(time)) { return;} //O(1)
    
    //tijdslot moet heel uur zijn afgerond naar beneden
    int tijdslot = time / 100 * 100;        //O(1)
    if(tijdslot >= this.start_tijdslot && tijdslot <= this.eind_tijdslot) {
      tijdsloten.put(tijdslot,null);        //O(log n)  
    }
  }

  /**
   * Zoek naar een reservering op het aangegeven tijdslot.
   * @param time gewenst tijdslot in formaat hhmm
   * @return null als het tijdslot niet bestaat of niet vrij is
   * instantie van Reservation als tijdslot bestaat en gereserveerd is
   * Complexity O(log n) voor de zoekoperatie moet de boom doorlopen worden tot aan de gevonden key. Dit is gelijk aan de hoogte van de boom ofwel O(log n)
   */
  @Override
  public Reservation search(int time) {
    if(!Tijd.checkTime(time)) { return null;}  //O(1)
    
    Reservation reservering = tijdsloten.get(time); //O(log n)
    return reservering;
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
   * Er worden hier een aantal zoekoperaties gedaan die in O(log n) gedaan worden
   * Echter het zoeken naar de dichtsbijzijnde beschikbare key daar is geen directe oplossing voor in de TreeMap
   * Dit werkt met de lowerKey en higherKey die steeds in O(log n) zoeken naar de dichtsbijzijnde hoger of lagere key
   * Echter controleert die niet of het element beschikbaar is. Hierdoor moet als die niet beschikbaar is opnieuw het volgende lowerKey gaan zoeken in O(log n)
   * Potentieel moet zo de hele lijst doorlopen worden waardoor n operaties nodig zijn die ieder O(log n) tijd kost. Dus O(n log n)
   */
  @Override
  public int findClosestAvailableTime(int time) {
    //onjuiste tijd
    if(!Tijd.checkTime(time)) { return -1; }
    
    //Voor de opgegeveven tijd gelijk aan het tijdslot en er is geen reservering
    Reservation reservering = tijdsloten.get(time);            // O(log n)
    if(reservering == null && tijdsloten.containsKey(time)) {  // O(log n)
      return time;
    }

    Integer lower = searchClosestAvailableLower(time);          //O(n log n)
    Integer higher = searchClosestAvailableHigher(time);        //O(n log n)
    
    //volgeboekt
    if(lower == -1 && higher == -1) {
      return -1;
    }
    
    if(higher == -1) { return lower; }
    if(lower == -1) { return higher; }

    int distLower = 0;
    int distHigher = 0;
    
    try {
      //dit zou allemaal in O(1) uitgevoerd moeten worden
      LocalTime lowertime = Tijd.maakTijd(lower);
      LocalTime highertime = Tijd.maakTijd(higher); 
      LocalTime voorkeurtijd = Tijd.maakTijd(time); 
      
      //bereken aantal minuten verschil tussen tijden en voorkeur
      Duration dlower = Duration.between(lowertime, voorkeurtijd);
      Duration dhigher = Duration.between(highertime, voorkeurtijd);
      distLower = Math.abs((int) dlower.toMinutes());
      distHigher = Math.abs((int) dhigher.toMinutes());
      
    } catch(IllegalArgumentException e) {}
     
    return distLower <= distHigher && distLower != -1 ? lower : higher;
    
  }
  
  
  
  /**
   * Zoekt naar een beschikbaar tijdslot die lager is dan gegeven time
   * Complexity O(n logn) omdat we steeds het dichtsbijzijnde element zoeken die potentieel niet beschikbaar is en de hele lijst kleiner dan time doorlopen moet worden
   * @param time gewenste tijdstop
   * @return tijdstip beschikbaar
   * Er worden hier een aantal zoekoperaties gedaan die in O(log n) gedaan worden
   * Echter het zoeken naar de dichtsbijzijnde beschikbare key daar is geen directe oplossing voor in de TreeMap
   * Dit werkt met de lowerKey en higherKey die steeds in O(log n) zoeken naar de dichtsbijzijnde hoger of lagere key
   * Echter controleert die niet of het element beschikbaar is. Hierdoor moet als die niet beschikbaar is opnieuw het volgende lowerKey gaan zoeken in O(log n)
   * Potentieel moet zo de hele lijst doorlopen worden waardoor n operaties nodig zijn die ieder O(log n) tijd kost. Dus O(n log n)
   */
  private int searchClosestAvailableLower(int time) {
    Integer lowerKey = tijdsloten.lowerKey(time);  // O(log n)
    while (lowerKey != null) {                     // O(n)
      Reservation res = tijdsloten.get(lowerKey);  // O(log n)
      if(res == null) {
          return lowerKey; 
      } else {
        lowerKey = tijdsloten.lowerKey(lowerKey);  // O(log n)
      }
    }
    return -1;
  }
  
  /**
   * Zoekt naar een beschikbaar tijdslot die hoger is dan gegeven time
   * Complexity O(n logn) omdat we steeds het dichtsbijzijnde element zoeken die potentieel niet beschikbaar is en de hele lijst groter dan time doorlopen moet worden
   * @param time gewenste tijdstop
   * @return tijdstip beschikbaar
   * Er worden hier een aantal zoekoperaties gedaan die in O(log n) gedaan worden
   * Echter het zoeken naar de dichtsbijzijnde beschikbare key daar is geen directe oplossing voor in de TreeMap
   * Dit werkt met de lowerKey en higherKey die steeds in O(log n) zoeken naar de dichtsbijzijnde hoger of lagere key
   * Echter controleert die niet of het element beschikbaar is. Hierdoor moet als die niet beschikbaar is opnieuw het volgende lowerKey gaan zoeken in O(log n)
   * Potentieel moet zo de hele lijst doorlopen worden waardoor n operaties nodig zijn die ieder O(log n) tijd kost. Dus O(n log n)
   */
  private int searchClosestAvailableHigher(int time) {
    Integer higherKey = tijdsloten.higherKey(time); // O(log n)
    while (higherKey != null) {                     // O(n)
      Reservation res = tijdsloten.get(higherKey);  // O(log n)
      if(res == null) {
          return higherKey; 
      } else {
        higherKey = tijdsloten.higherKey(higherKey);  // O(log n)
      }
    }
    return -1;
  }
  

  /**
   * Boek het opgegeven tijdslot met de opgegeven naam.
   * Het tijdslot moet bestaan en vrij zijn, anders wordt er niet gereserveerd.
   * @param time tijdslot in formaat hhmm (bijv. 800 voor 08:00, 1330 voor 13:30)
   * @param name naam voor reservering (bijv. "Smit")
   * Complexiteit O(log n)
   * In deze methode worden een aantal operaties gedaan in constante tijd
   * Daarnaast zij er twee operaties die ieder O(log n) kosten deze hebben betrekking tot het zoeken en invoegen in de boom
   * Voor het invoegen van een item moet de boom van boven naar beneden worden doorlopen om het item te vinden
   */
  @Override
  public void book(int time, String name) {
    
    //zoek dichtsbijzijnde beschikbaar tijdslot
    int tijdslot = time / 100 * 100;        //O(1)
    
    //valt boeking in het tijdslot en is beschikbaar?
    if(
        isInTijdslot(time,tijdslot) &&      //O(1)
        tijdsloten.get(tijdslot) == null) { //O(log n)
      tijdsloten.put(tijdslot,new Reservation(time,name));  // O(log n)
    }
  }
  
  /**
   * Controleert of een gewenst tijdstip in een gegeven tijdslot valt
   * @param time        gewenste tijd hhmm
   * @param tijdslot    tijdslot hh*100 
   * @return            true als gewenste tijd in het tijdslot valt
   * Complexiteit O(1)
   */
  private boolean isInTijdslot(int time, int tijdslot) {
    if(tijdslot == -1 || !Tijd.checkTime(time)) return false;
    LocalTime tijdTijdslot = Tijd.maakTijd(tijdslot);
    LocalTime tijdGewenst = Tijd.maakTijd(time);   
    return tijdTijdslot.getHour() == tijdGewenst.getHour();
  }

  /**
  * Print de reserveringslijst op volgorde.
  * @param all Als all==true, print dan alle tijdsloten,
  * anders alleen de geboekte tijdsloten.
  * Complexiteit O(n)
  * In deze methode wordt via de iterator de hele boom doorlopen. Hierwij wordt iedere node een keer aangeroepen en worden daarop alleen operaties uitgevoerd die in constante tijd uitgevoerd worden.
  */
  @Override
  public void printReservationsInOrder(boolean all) {
      
    StringBuilder str = new StringBuilder("Reserveringen:\n");
    Iterator<Map.Entry<Integer, Reservation>> iterator = tijdsloten.entrySet().iterator();
   
    while (iterator.hasNext()) {
      Map.Entry<Integer, Reservation> entry = iterator.next();
      Integer tijdslot = entry.getKey();
      Reservation element = entry.getValue();
      
      if(all || element != null) {
        
        int uren = tijdslot / 100;
        String minuten = String.format("%02d", tijdslot % 100);
        str.append("Tijdslot: ")
        .append(uren + ":" + minuten);
        
        //reservering
        if(element == null) {
          str.append(", Geen reservering")
          .append("\n");
        }
        else {
          str.append(", " + element.toString());
        }
        
      } 
    }
    System.out.println(str.toString());
    
  }
  

  /**
   * Verwijderd een reservering voor een gegeven tijd
   * @param time    tijd reservering
   * Complexitei O(log n)
   * Voor deze operatie zijn een paar acties nodig die in constante tijd uitgevoerd worden
   * Verder wordt de put methode aangeroepen die intern eerst op zoek moet gaan naar het juiste element. Hiervoor moet de boom doorlopen worden van boven naar beneden in O(log n) tijd
   */
  @Override
  public void cancel(int time) {
    //onjuiste tijd
    if(!Tijd.checkTime(time)) { return; }
    
    int tijdslot = time /100 * 100;     //O(1)
    Reservation res = search(tijdslot); //O(log n)
    if(res != null) {
      tijdsloten.put(tijdslot, null);   //O(log n)
    }
  }
  
  /**
   * Geeft het aantal tijdsloten
   * @return het aantal tijdsloten
   * Complexiteit O(1) er wordt hier alleen een methode aangeroepen die in constante tijd wordt uitgevoerd
   */
  public int aantalTijdsloten() {
    return tijdsloten.size();
  }
  

  
  

}
