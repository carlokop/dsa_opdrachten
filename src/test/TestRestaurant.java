package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import opgave.Restaurant;

public class TestRestaurant {
  
  Restaurant r;
  
  @Test
  public void TestInsert() {  
    r = new Restaurant();
    
    //geen tijdsloten
    assertEquals(0,r.aantalTijdsloten());
    
    //insert tijdsloten 8:00 t/m 22:00
    for(int i=800; i<=2200; i+=100) {
      r.insert(i);
    }
    
    assertEquals(15,r.aantalTijdsloten());
    
    //Duplicaten 
    r.insert(1200);
    assertEquals(15,r.aantalTijdsloten());
    
    //item in set
    assertNotNull(r.search(800));
    assertNotNull(r.search(1500));
    assertNotNull(r.search(2200));
    
    //item niet in set
    assertNull(r.search(500));
    
    //als we starttijd ongelijk aan heel uur toevoegen wordt deze toegewezen aan heel uur
    r.insert(2315);
    assertNotNull(r.search(2300));
    
    //ongeldige tijd wordt niet toegevoegd
    r.insert(-100);
    assertNull(r.search(-100));
    
    //fractionele tijd maar met ongeldige minuten wordt gewoon aangemaakt als tijdsblok
    r.insert(180);
    assertNotNull(r.search(100));
    
  }
  
  
  @Test 
  public void TestSearch() {
    r = new Restaurant();
    r.insert(1200);
    r.insert(1400);
    
    //er is een tijdslot om 12 uur 
    assertNotNull(r.search(1200));
    
    //er is geen tijdslot om 13 uur 
    assertNull(r.search(1300));
    
    //er is geen tijdslot om 12:15 uur 
    assertNull(r.search(1215));

  }
  
  @Test
  public void TestFindClosestAvailableTime() {
    r = new Restaurant();
    r.insert(1200);
    r.insert(1400);
    
    //dit verder uitwerken na de boeking 
    
  }
  

}
