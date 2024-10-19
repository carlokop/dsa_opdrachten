package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import opgave.Restaurant;
import opgave.RestaurantHerzien;

public class TestRestaurantHerzien {

  private RestaurantHerzien r;
  
  @Test
  public void TestInsert() { 
    r = new RestaurantHerzien();
    
//    geen tijdsloten
    assertEquals(0,r.aantalTijdsloten());
    
    //insert tijdsloten 8:00 t/m 22:00
    for(int i=800; i<=2200; i+=100) {
      r.insert(i);
    }
    
    assertEquals(15,r.aantalTijdsloten()); 
   
    
  }
  
  @Test
  public void TestBook() {
    r = new RestaurantHerzien();
    r.insert(1200);
    r.insert(1400);   
    
    //hier gaat nog van alles fout in het bepalen van de closers beschikbare index
    r.book(1400, "carlo");
    r.book(1400, "henk");
    System.out.println(r.toString());
  }
  
  

}
