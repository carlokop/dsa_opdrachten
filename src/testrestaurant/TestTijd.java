package testrestaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.junit.Test;

import restaurant.Tijd;

/**
 * Test de tijdklasse
 */
public class TestTijd {

  /**
   * Tests of het maken van een tijdsobject goed gaat
   */
  @Test
  public void maakTijdTest() {
    LocalTime tijd = Tijd.maakTijd(100);
    
    assertEquals(1,tijd.getHour());
    assertEquals(0,tijd.getMinute());
    
    tijd = Tijd.maakTijd(1230);
    
    assertEquals(12,tijd.getHour());
    assertEquals(30,tijd.getMinute());
    
    IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> {
      Tijd.maakTijd(70);
    });
    
    assertEquals("Tijdsformaat 70 (hhmm) kan niet worden omgezet in een geldige tijd",err.getMessage());
    
  }
  
  /**
   * Tests het valideren van de tijd
   */
  @Test
  public void checkTimeTest() {
    //alleen minuten
    assertTrue(Tijd.checkTime(0));
    assertTrue(Tijd.checkTime(30));
    assertTrue(Tijd.checkTime(59));
    assertFalse(Tijd.checkTime(-1));
    assertFalse(Tijd.checkTime(60));
    
    //uren
    assertTrue(Tijd.checkTime(0000));
    assertTrue(Tijd.checkTime(100));
    assertTrue(Tijd.checkTime(1000));
    assertTrue(Tijd.checkTime(2300));
    assertFalse(Tijd.checkTime(2400));
    
    //uren en minuten
    assertTrue(Tijd.checkTime(101));
    assertTrue(Tijd.checkTime(130));
    assertTrue(Tijd.checkTime(159));
    assertFalse(Tijd.checkTime(160));
    assertFalse(Tijd.checkTime(199));
    
    assertTrue(Tijd.checkTime(901));
    assertTrue(Tijd.checkTime(930));
    assertTrue(Tijd.checkTime(959));
    assertFalse(Tijd.checkTime(960));
    assertFalse(Tijd.checkTime(999));
    
    assertTrue(Tijd.checkTime(1001));
    assertTrue(Tijd.checkTime(1030));
    assertTrue(Tijd.checkTime(1059));
    assertFalse(Tijd.checkTime(160));
    assertFalse(Tijd.checkTime(1099));
    
    assertTrue(Tijd.checkTime(2301));
    assertTrue(Tijd.checkTime(2330));
    assertTrue(Tijd.checkTime(2359));
    assertFalse(Tijd.checkTime(2360));
    assertFalse(Tijd.checkTime(2399));
    
    assertFalse(Tijd.checkTime(2400));
    assertFalse(Tijd.checkTime(2401));
    assertFalse(Tijd.checkTime(2430));
    assertFalse(Tijd.checkTime(2459));
    assertFalse(Tijd.checkTime(2499));
    
    assertFalse(Tijd.checkTime(10000));
   
    
  }

}
