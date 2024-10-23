package mediaan;

//import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMediaan {
  
  Mediaan m;
  
  @BeforeEach
  void setup() {
    m = new Mediaan();
  }
  
  @Test
  public void TestMediaanConstruct() {
    assertNotNull(m);
    assertEquals(0,m.getL().size());
    assertEquals(0,m.getU().size());
  }
  
  @Test
  public void TestAdd() {
    //lege lijst
    assertEquals(0,m.getL().size());
    assertEquals(0,m.getU().size());
    assertEquals(0,m.mediaan());
    
    //voeg 1 item toe aan lege lijst moet in l
    m.add(1);
    assertEquals(1,m.getL().size());
    assertEquals(0,m.getU().size());
    assertEquals(1,m.mediaan());
    
    //voeg 1 item toe. moet in u
    m.add(2);
    assertEquals(1,m.getL().size());
    assertEquals(1,m.getU().size());
    assertEquals(1,m.mediaan());    //breuk 1.5 int deling
    
    //voeg 1 item toe. moet in u
    m.add(3);
    assertEquals(2,m.getL().size());
    assertEquals(1,m.getU().size());
    assertEquals(2,m.mediaan());
    
    //voeg items toe. lijst even aantal waarbij median duidelijk groter wordt dan l.peek()
    m.add(8);
    m.add(9);
    m.add(10);
    assertEquals(5,m.mediaan());        //5.5 int deling
    assertEquals(3,m.getL().size());
    assertEquals(3,m.getU().size());
    
    //voeg item toe > median die in l moet
    m.add(6);
    assertEquals(6,m.mediaan());
    assertEquals(4,m.getL().size());
    assertEquals(3,m.getU().size());
    
    //lijst l bevat nu 1,2,3,6
    //lijst u bevat 8,9,10
    assertTrue(m.getL().contains(1));
    assertTrue(m.getL().contains(2));
    assertTrue(m.getL().contains(3));
    assertTrue(m.getL().contains(6));
    assertTrue(m.getU().contains(8));
    assertTrue(m.getU().contains(9));
    assertTrue(m.getU().contains(10));
    
    
    //reset met 1,2 in l en 20,21 in u
    m = new Mediaan();
    m.add(1);
    m.add(2);
    m.add(20);
    m.add(21);

    
    //voeg item < median toe. 
    //Dit item moet aan l worden toegevoegd ivm even aantal
    m.add(7);
    assertEquals(7,m.mediaan());
    assertTrue(m.getL().contains(7));
    
    //voeg item < median toe. 
    //Dit item moet aan l worden toegevoegs ivm < median maar 7 verplaatst naar u 
    m.add(3);
    assertEquals(5,m.mediaan());
    assertTrue(m.getL().contains(3));
    assertTrue(m.getU().contains(7));
    
    //voeg item > median toe en > dan het laagste getal in u
    //enty moet in u worden opgeslagen en laagste getal uit u verplaast naar i
    m.add(8);
    assertEquals(7,m.mediaan());
    assertTrue(m.getL().contains(7));
    assertTrue(m.getU().contains(8));
  
  }
  
  @Test
  public void TestCalcMediaan() {
    //lege lijst
    assertEquals(0,m.mediaan());
    
    //oneven aantal
    m.add(2);
    assertEquals(2,m.mediaan());
    
    //even aantal geheel getal
    m.add(4);
    assertEquals(3,m.mediaan());
    
    //even aantal breuk
    m.add(3);
    m.add(4);
    assertEquals(3,m.mediaan());    //3.5 int deling
    
  }
  
  
  
  

} //class
