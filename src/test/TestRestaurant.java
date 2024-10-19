package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import opgave.Reservation;
import opgave.Restaurant;

public class TestRestaurant {

  private Restaurant r;
  
  @BeforeEach
  public void setUp() {
    r = new Restaurant(800,2200);
  }
  
  @Test
  public void TestInsert() { 
    
    //geen tijdsloten
    r = new Restaurant(800,700);  //fix om lege tijdsloten te maken
    assertEquals(0,r.aantalTijdsloten());
    
   //insert automatisch tijdsloten 8:00 t/m 22:00
    r = new Restaurant(800,2200);
    
    assertEquals(15,r.aantalTijdsloten()); 
    
    //insert ongeldige tijden worden overgeslagen
    r.insert(0);
    r.insert(100);
    r.insert(700);
    r.insert(2300);
    r.insert(1260);
    r.insert(0070);
    r.insert(-1000);
    assertEquals(15,r.aantalTijdsloten());
     
  }
  
  @Test
  public void DubbeleInsertTest() { 
    
    assertEquals(15,r.aantalTijdsloten()); 
    r.insert(1000);  //reeds door contstructor ingevoegd
    r.insert(1050);  //wordt afgerond naar 10 uur
    
    assertEquals(15,r.aantalTijdsloten()); 
   
  }
 
  
  @Test
  public void TestBook() {
   
    assertNull(r.search(1200));

    r.book(1200, "carlo"); 
    assertNotNull(r.search(1200));
    assertEquals("carlo",r.search(1200).getNaam());
    assertEquals(1200,r.search(1200).getTijd());
    
    //boeking ergens halverwege een gewenste tijdslot
    r.book(1530, "henk");   //tijdslot 1500
    assertEquals("henk",r.search(1500).getNaam());
    assertEquals(1530,r.search(1500).getTijd());
    
    //tijdslot niet meer beschikbaar wordt niet geboekt
    r.book(1500, "harry");
    assertEquals("henk",r.search(1500).getNaam());
    assertNotEquals("harry",r.search(1500).getNaam());
    assertNull(r.search(1400));
    assertNull(r.search(1600));
    
    //print naar het console
    r.printReservationsInOrder(true);
    r.printReservationsInOrder(false);

  }
  
  @Test 
  public void searchTest() {
    
    r.book(1400, "carlo");
    
    Reservation res = r.search(1400);
    assertNotNull(res);
    assertEquals(1400,res.getTijd());
    assertEquals("carlo",res.getNaam());
    
    res = r.search(1000);
    assertNull(res);
  }
  
  @Test 
  public void searchClosestAvailableTest() {

    //exacte tijd en beschikbaar
    assertEquals(1200,r.findClosestAvailableTime(1200)); 
    
    //tijdsblok ondergrens dichtstbij is vrij
    assertEquals(1200,r.findClosestAvailableTime(1215)); 
    
    //tijdsblok bovenrgrens dichtstbij is vrij
    assertEquals(800,r.findClosestAvailableTime(700)); 
    
    //twee tijdsblokken even ver beide vrij
    assertEquals(1200,r.findClosestAvailableTime(1230)); 
  
    r.book(1400, "carlo");
    //tijdsblok dichtstbij is reeds geboekt
    assertEquals(1500,r.findClosestAvailableTime(1405)); 
    assertEquals(1300,r.findClosestAvailableTime(1359)); 
    assertEquals(1300,r.findClosestAvailableTime(1400)); 
    
    //Vergelijking werkt met minuten niet met getallen
    assertEquals(1300,r.findClosestAvailableTime(1231));
    assertEquals(1200,r.findClosestAvailableTime(1229));
    
    //ongeldige tijdsformaat invoer
    assertEquals(-1,r.findClosestAvailableTime(70));
    assertEquals(-1,r.findClosestAvailableTime(-500));
    
    //vul alle tijdsloten
    for(int i=800; i<=2200; i+=100) {
      r.book(i, "carlo");
    }
    
    //volgeboekt
    assertEquals(-1,r.findClosestAvailableTime(500));
    assertEquals(-1,r.findClosestAvailableTime(800));
    assertEquals(-1,r.findClosestAvailableTime(1200));
    assertEquals(-1,r.findClosestAvailableTime(2200));
    assertEquals(-1,r.findClosestAvailableTime(2300));
    
  }
  
  @Test
  public void cancelTest() {
    r.book(1420, "carlo");
    assertNotNull(r.search(1400));
    
    r.cancel(1420);
    assertNull(r.search(1400));
    
  }
 
  
  

}
