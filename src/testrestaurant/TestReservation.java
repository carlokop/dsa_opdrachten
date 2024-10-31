package testrestaurant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import restaurant.Reservation;

/**
 * Tests de reservering
 */
public class TestReservation {
  
  /**
   * Tests de equals methode
   */
  @Test
  public void equalsTest() {
    //happy
    Reservation r = new Reservation(100, "Carlo");
    Reservation r2 = new Reservation(100, "Carlo");
    assertTrue(r.equals(r));
    assertTrue(r.equals(r2));
    assertTrue(r2.equals(r));
    Reservation r3 = new Reservation(100, "Henk");
    assertFalse(r.equals(r3));
    
    //null
    assertFalse(r.equals(null));
    //classcast exception 
    assertFalse(r.equals(new Object()));
    
  }
  
  /**
   * Tests de compareto methode
   */
  @Test
  public void equalsCompareTo() {
    Reservation r = new Reservation(1000, "Carlo");
    Reservation r2 = new Reservation(1100, "Carlo"); 
    Reservation r3 = new Reservation(1000, "Carlo"); 
    assertTrue(r.compareTo(r2) < 0);
    assertTrue(r.compareTo(r3) == 0);
    assertTrue(r.compareTo(r) == 0);
    assertTrue(r2.compareTo(r3) > 0);
  }
  
  

}
