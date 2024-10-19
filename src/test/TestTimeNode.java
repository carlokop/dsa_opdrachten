package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.junit.Test;

import opgave.TimeNode;

public class TestTimeNode {

  @Test
  public void maakTijdTest() {
    TimeNode t = new TimeNode(100);
    LocalTime tijd = t.getTime();
    
    assertEquals(1,tijd.getHour());
    assertEquals(0,tijd.getMinute());
    
    t = new TimeNode(1230);
    tijd = t.getTime();
    
    assertEquals(12,tijd.getHour());
    assertEquals(30,tijd.getMinute());
    
    IllegalArgumentException err = assertThrows(IllegalArgumentException.class, () -> {
      new TimeNode(70);
    });
    
    assertEquals("Ongeldig tijdsformaat",err.getMessage());
    
  }
  
  @Test
  public void checkTimeTest() {
    //alleen minuten
    assertTrue(TimeNode.checkTime(0));
    assertTrue(TimeNode.checkTime(30));
    assertTrue(TimeNode.checkTime(59));
    assertFalse(TimeNode.checkTime(-1));
    assertFalse(TimeNode.checkTime(60));
    
    //uren
    assertTrue(TimeNode.checkTime(0000));
    assertTrue(TimeNode.checkTime(100));
    assertTrue(TimeNode.checkTime(1000));
    assertTrue(TimeNode.checkTime(2300));
    assertFalse(TimeNode.checkTime(2400));
    
    //uren en minuten
    assertTrue(TimeNode.checkTime(101));
    assertTrue(TimeNode.checkTime(130));
    assertTrue(TimeNode.checkTime(159));
    assertFalse(TimeNode.checkTime(160));
    assertFalse(TimeNode.checkTime(199));
    
    assertTrue(TimeNode.checkTime(901));
    assertTrue(TimeNode.checkTime(930));
    assertTrue(TimeNode.checkTime(959));
    assertFalse(TimeNode.checkTime(960));
    assertFalse(TimeNode.checkTime(999));
    
    assertTrue(TimeNode.checkTime(1001));
    assertTrue(TimeNode.checkTime(1030));
    assertTrue(TimeNode.checkTime(1059));
    assertFalse(TimeNode.checkTime(160));
    assertFalse(TimeNode.checkTime(1099));
    
    assertTrue(TimeNode.checkTime(2301));
    assertTrue(TimeNode.checkTime(2330));
    assertTrue(TimeNode.checkTime(2359));
    assertFalse(TimeNode.checkTime(2360));
    assertFalse(TimeNode.checkTime(2399));
    
    assertFalse(TimeNode.checkTime(2400));
    assertFalse(TimeNode.checkTime(2401));
    assertFalse(TimeNode.checkTime(2430));
    assertFalse(TimeNode.checkTime(2459));
    assertFalse(TimeNode.checkTime(2499));
    
    assertFalse(TimeNode.checkTime(10000));
   
    
  }

}
