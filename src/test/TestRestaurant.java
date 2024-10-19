package test;

import static org.junit.Assert.assertEquals;

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
//    assertNotNull(r.search(800));
//    assertNotNull(r.search(1500));
//    assertNotNull(r.search(2200));
    
//    //item niet in set
//    assertNull(r.search(500));
    
    //reset
    r = new Restaurant();
    
    //als we starttijd ongelijk aan heel uur toevoegen wordt deze toegewezen aan heel uur
//    r.insert(1215);
//    assertNotNull(r.search(1200));
//    
//    //ongeldige tijd wordt niet toegevoegd
//    r.insert(-100);
//    assertNull(r.search(-100));
//    
//    //fractionele tijd maar met ongeldige minuten wordt gewoon aangemaakt als tijdsblok
//    r.insert(180);
//    assertNotNull(r.search(100));
    
  }
  
  
  @Test 
  public void TestSearch() {
    r = new Restaurant();
    r.insert(1200);
    r.insert(1400);
    
    //er is een tijdslot om 12 uur 
    //System.out.println(r.search(1200));
    //assertNotNull(r.search(1200));
//    
//    //er is geen tijdslot om 13 uur 
//    assertNull(r.search(1300));
//    
//    //er is geen tijdslot om 12:15 uur 
//    assertNull(r.search(1215));

  }
  
  @Test
  public void TestFindClosestAvailableTime() {
    r = new Restaurant();
    r.insert(1200);
    r.insert(1400);
    
    //Boeking op het exacte moment van het tijdslot + beschikbaar
    assertEquals(1400,r.findClosestAvailableTime(1400));
    
    //op dichtsbijzijnde tijdslot 1200 uur
    assertEquals(1200,r.findClosestAvailableTime(1215));
    assertEquals(1200,r.findClosestAvailableTime(1259));
    
    //op gelijke afstand gaat naar eerdere tijd
    assertEquals(1200,r.findClosestAvailableTime(1300));
    assertEquals(1400,r.findClosestAvailableTime(1301));
    
    //geen eerdere of latere data
    assertEquals(1200,r.findClosestAvailableTime(1000));
    assertEquals(1400,r.findClosestAvailableTime(2000));
    
    //boek 1400 vol
    r.book(1400, "carlo");
    
    //Boeking op het exacte moment van het tijdslot + niet meer beschikbaar
    assertEquals(1200,r.findClosestAvailableTime(1400));
    assertEquals(1200,r.findClosestAvailableTime(1500));
    assertEquals(1200,r.findClosestAvailableTime(1000));
    
    r.book(1400, "henk");
    
    //geen boekingen meer volgeboekt
    assertEquals(-1,r.findClosestAvailableTime(1000));
    assertEquals(-1,r.findClosestAvailableTime(1200));
    assertEquals(-1,r.findClosestAvailableTime(1400));
    assertEquals(-1,r.findClosestAvailableTime(1500));
    
    //ongeldig tijdsformaat
    assertEquals(-2,r.findClosestAvailableTime(70));
    
    
    //boek 1400 vol
    //test bovenstaande
    //  assertEquals(-1,r.findClosestAvailableTime(1200));
    //  assertEquals(-1,r.findClosestAvailableTime(1400));

    
  }
  
  @Test
  public void TestBook() {
    r = new Restaurant();
    r.insert(1200);
    r.insert(1400);   
    
//    r.book(1400, "carlo");
//    r.book(1400, "henk");
//    System.out.println(r.toString());
  }
  
  
  
  

}
