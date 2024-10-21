package opgave;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Houd een lijst met getallen bij en berekend daaruit de mediaan
 */
public class Mediaan  {
  
  /**
   * De lijst met getallen kleiner dan =n gelijk aan de mediaan
   * Als de maat van l+u oneven is bevat deze een entry meer dan u 
   */
  PriorityQueue<Integer> l;
  /**
   * Lijst met getakken groter dan of gelijk aan de mediaan
   */
  PriorityQueue<Integer> u;
  
  /**
  * Creert een lege collectie van getallen.
  */
  public Mediaan() {
    l = new PriorityQueue<Integer>(Comparator.reverseOrder());
    u = new PriorityQueue<Integer>();
  }
  
    
    /**
  * Geeft de mediaan van deze collectie getallen terug in O(1).
  *
  * @return de mediaan van deze collectie. 0 als de collectie leeg is.
  * 
  * Bij een breuk wordt er naar beneden afgerond
  * 
  */
  public int mediaan() {
  //als l null is, is ook u null
    if(l.peek() == null) return 0;
    
    //bij even aantal is de mediaan het gemiddelde tussen de twee
    if(equalSize()) {
      return (l.peek() + u.peek()) /2;
    } else {
      //bij oneven is de mediaan het eerste item uit l
      return l.peek();
    }
  };
  
  
  /**
  * Voegt een getal toe aan deze collectie met worst-case complexiteit van
  * O(log n), met n het huidig aantal getallen in de collectie.
  * Voegt een item toe aan PQ l als de som van de size van l en u even is en anders in u
  *
  * @param i het getal om toe te voegen.
  */
  /*
  @ @Contract evenAantal { //voegt item toe aan PQ l
  @   @requires l.size() == u.size() 
  @   @ensures l.size() == u.size()+1
  @ }
  @
  @ @Contract onevenAantal { //voegt item toe aan PQ u
  @   @requires l.size() == u.size() + 1 
  @   @ensures l.size() == u.size()
  @ }
  */
  public void add(int i) {
    if(l.size() == 0 && u.size() == 0) {  
      l.offer(i);           //O(1) omdat de lijst leeg is
      return;
    }
    if(equalSize()) {
      //voeg toe aan l
      if(i<=mediaan() || i<u.peek()) {
        l.offer(i);         //O(log n)
      } else {
        //voeg i toe aan u maar haal het minimum uit u en stop die in l
        u.offer(i);         //O(log n)
        l.offer(u.poll());  //O(log n + log n)
      }
    } else {
      //voeg toe aan u
      if(i>=mediaan() ) {
        u.offer(i);         //O(log n)
      } else {
        l.offer(i);         //O(log n)
        u.offer(l.poll());  //O(log n + log n)
      }
    }
  }
  
  
  /**
   * Geeft de priorityQueue kleiner of gelijk aan de  mediaan
   * @return de priorittyQueue
   */
  public PriorityQueue<Integer> getL() {
    return l;
  }
  
  /**
   * Geeft de priorityQueue kleiner of gelijk aan de  mediaan
   * @return de priorittyQueue
   */
  public PriorityQueue<Integer> getU() {
    return u;
  }
  
  /**
   * Geeft aan of het aantal item in PriorityQueue l en PriorityQueue u gelijk is
   * @return true als het formaat gelijk is
   */
  private boolean equalSize() {
    return l.size() == u.size();
  }
  
  
 



  } //class