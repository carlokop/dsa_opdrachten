package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import datastructure.AVLTreeMap;


class AVLTreeMapTest {

  private AVLTreeMap<Integer, String> avlTreeMap;

  @BeforeEach
  public void setUp() {
      avlTreeMap = new AVLTreeMap<>();
  }

  @Test
  public void testPutGet() {
    assertNull(avlTreeMap.get(1)); 
    avlTreeMap.put(1, "een");
    avlTreeMap.put(2, "twee");
    avlTreeMap.put(3, "drie");

    assertEquals("een", avlTreeMap.get(1));
    assertEquals("twee", avlTreeMap.get(2));
    assertEquals("drie", avlTreeMap.get(3));
    assertNull(avlTreeMap.get(4)); 
  }
 
  
  @Test
  public void testHeight() { 
    assertEquals(0, avlTreeMap.getHeight());
    avlTreeMap.put(1, "een");
    assertEquals(1, avlTreeMap.getHeight());
    avlTreeMap.put(2, "twee");
    assertEquals(2, avlTreeMap.getHeight());
    avlTreeMap.put(3, "drie");
    assertEquals(2, avlTreeMap.getHeight());
    avlTreeMap.put(4, "vier");
    assertEquals(3, avlTreeMap.getHeight());
  }

  @Test
  public void testRemove() {
      avlTreeMap.put(1, "een");
      avlTreeMap.put(2, "twee");
      avlTreeMap.remove(1);

      assertNull(avlTreeMap.get(1));
      assertEquals("twee", avlTreeMap.get(2));
  }

  @Test
  public void testSize() {
      assertEquals(0, avlTreeMap.size());
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(2, "Twee");
      assertEquals(2, avlTreeMap.size());
      avlTreeMap.remove(1);
      assertEquals(1, avlTreeMap.size());
  }
  
  @Test
  public void testDuplicaten() {
      avlTreeMap.put(1, "Een");
      assertEquals("Een", avlTreeMap.get(1));
      assertEquals(1,avlTreeMap.size());
      
      avlTreeMap.put(1, "Een updated");
      assertEquals("Een updated", avlTreeMap.get(1));
      assertEquals(1,avlTreeMap.size());
  }

  @Test
  public void testIsEmpty() {
      assertTrue(avlTreeMap.isEmpty());
      avlTreeMap.put(1, "Een");
      assertFalse(avlTreeMap.isEmpty());
  }

  @Test
  public void testFirstEntry() {
      avlTreeMap.put(2, "Twee");
      avlTreeMap.put(1, "Een");
      Map.Entry<Integer, String> entry = avlTreeMap.firstEntry();

      assertNotNull(entry);
      assertEquals((Integer) 1, entry.getKey());
      assertEquals("Een", entry.getValue());
  }

  @Test
  public void testLastEntry() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(2, "Twee");
      Map.Entry<Integer, String> entry = avlTreeMap.lastEntry();

      assertNotNull(entry);
      assertEquals((Integer) 2, entry.getKey());
      assertEquals("Twee", entry.getValue());
  }

  @Test
  public void testFloorEntry() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(2, "Twee");
      Map.Entry<Integer, String> entry = avlTreeMap.floorEntry(2);

      assertNotNull(entry);
      assertEquals((Integer) 2, entry.getKey());
      assertEquals("Twee", entry.getValue());
      
      Map.Entry<Integer, String> entry2 = avlTreeMap.floorEntry(3);
      assertNotNull(entry2);
      assertEquals((Integer) 2, (Integer) entry.getKey());
      assertEquals("Twee", entry2.getValue());
  }

  @Test
  public void testCeilingEntry() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(3, "Drie");
      Map.Entry<Integer, String> entry = avlTreeMap.ceilingEntry(2);

      assertNotNull(entry);
      assertEquals((Integer) 3, entry.getKey());
      assertEquals("Drie", entry.getValue());
      
      Map.Entry<Integer, String> entry2 = avlTreeMap.ceilingEntry(1);
      assertNotNull(entry2);
      assertEquals((Integer) 1, entry2.getKey());
      assertEquals("Een", entry2.getValue());
  }

  @Test
  public void testHigherEntry() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(3, "Drie");
      Map.Entry<Integer, String> entry = avlTreeMap.higherEntry(1);

      assertNotNull(entry);
      assertEquals((Integer) 3, entry.getKey());
      assertEquals("Drie", entry.getValue());
      
      Map.Entry<Integer, String> entry2 = avlTreeMap.higherEntry(3);
      assertNull(entry2);
  }

  @Test
  public void testLowerEntry() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(3, "Drie");
      Map.Entry<Integer, String> entry = avlTreeMap.lowerEntry(3);

      assertNotNull(entry);
      assertEquals((Integer) 1, entry.getKey());
      assertEquals("Een", entry.getValue());
      
      Map.Entry<Integer, String> entry2 = avlTreeMap.lowerEntry(2);
      assertNotNull(entry2);
      assertEquals((Integer) 1, entry2.getKey());
      assertEquals("Een", entry2.getValue());
  }

  @Test
  public void testSubMap() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(2, "Twee");
      avlTreeMap.put(3, "Drie");
      avlTreeMap.put(4, "Vier");

      Map<Integer, String> subMap = avlTreeMap.subMap(1, 3);
      assertEquals(3, subMap.size());
      assertTrue(subMap.containsKey(1));
      assertTrue(subMap.containsKey(2));
      assertTrue(subMap.containsKey(3));
      assertFalse(subMap.containsKey(4));
  }

  @Test
  public void testEntrySet() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(2, "Twee");

      assertEquals(2, avlTreeMap.entrySet().size());
      assertTrue(avlTreeMap.entrySet().contains(Map.entry(1, "Een")));
      assertTrue(avlTreeMap.entrySet().contains(Map.entry(2, "Twee")));
  }

  @Test
  public void testKeySet() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(2, "Twee");

      assertEquals(2, avlTreeMap.keySet().size());
      assertTrue(avlTreeMap.keySet().contains(1));
      assertTrue(avlTreeMap.keySet().contains(2));
  }

  @Test
  public void testValues() {
      avlTreeMap.put(1, "Een");
      avlTreeMap.put(2, "Twee");
      avlTreeMap.put(3, "Drie");

      assertEquals(3, avlTreeMap.values().size());
      assertTrue(avlTreeMap.values().contains("Een"));
      assertTrue(avlTreeMap.values().contains("Twee"));
      assertTrue(avlTreeMap.values().contains("Drie"));
  }


}