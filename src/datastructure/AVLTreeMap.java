package datastructure;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Deze klasse vervangt de Ingebouwde Java TreeMap klasse en is gegenereerd door ChatGPT zonder al te veel aanpassingen
 * Ik gebruik deze als abstracte klasse in plaats van alle klasse uit het boek overschrijven want dat zou enorm veel tijd kosten 
 * Reden hiervoor is dat het O(n log n) zou kosten om met de TreeMap klasse het dichtstbijzijnde tijdslot te vinden die beschikbaar is 
 * Dit vanwegen het gebrek in de Java klasse van de mogelijkheid om in O(1) de parent, children, siblings en isInternal of isExternal aan te roepen
 * 
 * @param <K>
 * @param <V>
 */
public class AVLTreeMap<K extends Comparable<K>, V> extends AbstractMap<K, V> implements Map<K, V> {
  
    private AVLNode<K, V> root;
    
    class AVLNode<K, V> {
      K key;
      V value;
      int height;
      int size; // Track the size of the subtree for O(1) size() calls
      AVLNode<K, V> left, right;

      AVLNode(K key, V value) {
          this.key = key;
          this.value = value;
          this.height = 1;
          this.size = 1; // Start with size 1 (this node)
      }
  }

    // Get the height of the node
    private int height(AVLNode<K, V> N) {
        return (N == null) ? 0 : N.height;
    }

    // Get the size of the node's subtree
    private int size(AVLNode<K, V> N) {
        return (N == null) ? 0 : N.size;
    }

    // Update size and height of the node
    private void updateNode(AVLNode<K, V> node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
            node.size = 1 + size(node.left) + size(node.right);
        }
    }

    // Right rotate
    private AVLNode<K, V> rightRotate(AVLNode<K, V> y) {
        AVLNode<K, V> x = y.left;
        AVLNode<K, V> T2 = x.right;

        x.right = y;
        y.left = T2;

        updateNode(y);
        updateNode(x);

        return x;
    }

    // Left rotate
    private AVLNode<K, V> leftRotate(AVLNode<K, V> x) {
        AVLNode<K, V> y = x.right;
        AVLNode<K, V> T2 = y.left;

        y.left = x;
        x.right = T2;

        updateNode(x);
        updateNode(y);

        return y;
    }

    // Get balance factor
    private int getBalance(AVLNode<K, V> N) {
        return (N == null) ? 0 : height(N.left) - height(N.right);
    }

    /**
     * Voegt een key value pair toe aan de boom
     * @param key       gegeven unieke sleutel
     * @param value     de waarde
     */
    @Override
    public V put(K key, V value) {
        root = put(root, key, value);
        return value; // retourneert de waarde die is toegevoegd of vervangen
    }
    
    private AVLNode<K, V> put(AVLNode<K, V> node, K key, V value) {
        if (node == null)
            return new AVLNode<>(key, value);

        if (key.compareTo(node.key) < 0)
            node.left = put(node.left, key, value);
        else if (key.compareTo(node.key) > 0)
            node.right = put(node.right, key, value);
        else
            node.value = value;

        updateNode(node);

        // Balance the tree
        return balance(node, key);
    }

    // Balance the AVL tree
    private AVLNode<K, V> balance(AVLNode<K, V> node, K key) {
        int balance = getBalance(node);

        if (balance > 1 && key.compareTo(node.left.key) < 0)
            return rightRotate(node);

        if (balance < -1 && key.compareTo(node.right.key) > 0)
            return leftRotate(node);

        if (balance > 1 && key.compareTo(node.left.key) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && key.compareTo(node.right.key) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Haalt een gegeven value uit de lijst met opgegeven key
     * @param key   de sleutel waarop gezocht moet worden
     * @return      de gevonden waarde
     */
    @Override
    public V get(Object key) {
        AVLNode<K, V> node = get(root, (K) key);
        return node != null ? node.value : null; // retourneert de waarde of null
    }

    private AVLNode<K, V> get(AVLNode<K, V> node, K key) {
        if (node == null)
            return null;
        if (key.compareTo(node.key) < 0)
            return get(node.left, key);
        else if (key.compareTo(node.key) > 0)
            return get(node.right, key);
        else
            return node;
    }

    // Remove a node by key
    @Override
    public V remove(Object key) {
        V oldValue = get(key); // bewaar oude waarde
        root = remove(root, (K) key); // verwijder het knooppunt
        return oldValue; // retourneert de oude waarde
    }

    private AVLNode<K, V> remove(AVLNode<K, V> node, K key) {
        if (node == null)
            return null;

        if (key.compareTo(node.key) < 0)
            node.left = remove(node.left, key);
        else if (key.compareTo(node.key) > 0)
            node.right = remove(node.right, key);
        else {
            // Node with only one child or no child
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            // Node with two children: Get the inorder successor
            AVLNode<K, V> temp = minValueNode(node.right);

            node.key = temp.key;
            node.value = temp.value;

            node.right = remove(node.right, temp.key);
        }

        updateNode(node);

        return balance(node, key);
    }

    // Find the node with the minimum key
    private AVLNode<K, V> minValueNode(AVLNode<K, V> node) {
        AVLNode<K, V> current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // Find the node with the maximum key
    private AVLNode<K, V> maxValueNode(AVLNode<K, V> node) {
        AVLNode<K, V> current = node;
        while (current.right != null)
            current = current.right;
        return current;
    }

    /**
     * Zoekt het element met de laagste key
     * @return entry van de key met laagst waarde
     */
    public Map.Entry<K, V> firstEntry() {
        AVLNode<K, V> node = minValueNode(root);
        return node != null ? new AbstractMap.SimpleEntry<>(node.key, node.value) : null;
    }

    /**
     * Zoekt het element met de hoogste key
     * @return entry van de key met hoogste waarde
     */
    public Map.Entry<K, V> lastEntry() {
        AVLNode<K, V> node = maxValueNode(root);
        return node != null ? new AbstractMap.SimpleEntry<>(node.key, node.value) : null;
    }

    /**
     * Geeft weer of de boom leeg is
     * @return true als de boom leeg is
     */
    @Override
    public boolean isEmpty() {
        return root == null; // geeft waar of niet waar terug als de boom leeg is
    }

    /**
     * Geeft het aantal entries in de boo,
     * @return het aantal entries
     */
    @Override
    public int size() {
        return size(root); // retourneert de grootte van de boom
    }
    
    /**
     * Geeft de totale hoogte van de boom
     * @return de hoogte
     */
    public int getHeight() {
        return height(root); // Roep de bestaande private height methode aan met de root
    }

    /**
     * Geeft de entry waarbij de key groter dan of gelijk aan opgegeven key is
     * @param key   de opgegeven key waarop gefilterd moet worden
     * @return      de kleinste entry waarvan de key groter dan of gelijk is aan de key waarop gefilterd moet worden
     */
    public Map.Entry<K, V> ceilingEntry(K key) {
      AVLNode<K, V> node = ceilingNode(root, key);
      return node != null ? new AbstractMap.SimpleEntry<>(node.key, node.value) : null;
    }

    private AVLNode<K, V> ceilingNode(AVLNode<K, V> node, K key) {
        if (node == null) return null;
  
        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) > 0) {
            return ceilingNode(node.right, key);
        } else {
            AVLNode<K, V> t = ceilingNode(node.left, key);
            return (t != null) ? t : node;
        }
    }

    /**
     * Geeft de entry waarbij de key kleiner dan of gelijk aan opgegeven key is
     * @param key   de opgegeven key waarop gefilterd moet worden
     * @return      de grootste entry waarvan de key kleiner dan of gelijk is aan de key waarop gefilterd moet worden
     */
    public Map.Entry<K, V> floorEntry(K key) {
        AVLNode<K, V> node = floorNode(root, key);
        return node != null ? new AbstractMap.SimpleEntry<>(node.key, node.value) : null;
    }

    private AVLNode<K, V> floorNode(AVLNode<K, V> node, K key) {
        if (node == null) return null;
  
        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return floorNode(node.left, key);
        } else {
            AVLNode<K, V> t = floorNode(node.right, key);
            return (t != null) ? t : node;
        }
    }

    /**
     * Geeft de entry waarbij de key het grootste element is wat strikt kleiner is dan gegeven sleutel
     * @param key   de opgegeven key waarop gefilterd moet worden
     * @return      de grootste entry waarvan de key kleiner is dan de key waarop gefilterd moet worden
     */
    public Map.Entry<K, V> lowerEntry(K key) {
        AVLNode<K, V> node = lowerNode(root, key);
        return node != null ? new AbstractMap.SimpleEntry<>(node.key, node.value) : null;
    }
  
    private AVLNode<K, V> lowerNode(AVLNode<K, V> node, K key) {
        if (node == null) return null;
  
        if (key.compareTo(node.key) <= 0) {
            return lowerNode(node.left, key);
        } else {
            AVLNode<K, V> t = lowerNode(node.right, key);
            return (t != null) ? t : node;
        }
    }

    /**
     * Geeft de entry waarbij de key het kleinste element is wat strikt groter is dan gegeven sleutel
     * @param key   de opgegeven key waarop gefilterd moet worden
     * @return      de grootste entry waarvan de key kleiner is dan de key waarop gefilterd moet worden
     */
    public Map.Entry<K, V> higherEntry(K key) {
        AVLNode<K, V> node = higherNode(root, key);
        return node != null ? new AbstractMap.SimpleEntry<>(node.key, node.value) : null;
    }
  
    private AVLNode<K, V> higherNode(AVLNode<K, V> node, K key) {
        if (node == null) return null;
  
        if (key.compareTo(node.key) >= 0) {
            return higherNode(node.right, key);
        } else {
            AVLNode<K, V> t = higherNode(node.left, key);
            return (t != null) ? t : node;
        }
    }
  
    /**
     * Maakt een deelboom van de boom tussen gegeven begin en eindkeys
     * @param fromKey   minimale key
     * @param toKey     maximnale key
     * @return      Deelboom
     */
    public Map<K, V> subMap(K fromKey, K toKey) {
      AVLTreeMap<K, V> subMap = new AVLTreeMap<>(); // Gebruik AVLTreeMap in plaats van TreeMap
      collectSubMap(root, fromKey, toKey, subMap);
      return subMap;
    }
  
    private void collectSubMap(AVLNode<K, V> node, K fromKey, K toKey, AVLTreeMap<K, V> subMap) {
        if (node == null) return;
    
        if (fromKey.compareTo(node.key) < 0) {
            collectSubMap(node.left, fromKey, toKey, subMap);
        }
    
        if (fromKey.compareTo(node.key) <= 0 && toKey.compareTo(node.key) >= 0) {
            subMap.put(node.key, node.value); // Voeg toe aan de AVLTreeMap
        }
    
        if (toKey.compareTo(node.key) > 0) {
            collectSubMap(node.right, fromKey, toKey, subMap);
        }
    }
  
  
    /**
     * Geeft de verzameling van entries in gesorteerde volgorde
     * @return  de verzameling entries
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> entrySet = new LinkedHashSet<>();
        collectEntries(root, entrySet);
        return entrySet; // retourneert de set van invoerparen
    }
  
    private void collectEntries(AVLNode<K, V> node, Set<Map.Entry<K, V>> entrySet) {
        if (node == null) return;
        collectEntries(node.left, entrySet);
        entrySet.add(new AbstractMap.SimpleEntry<>(node.key, node.value));
        collectEntries(node.right, entrySet);
    }
  
    /**
     * Geeft de verzameling van keys in gesorteerde volgorde
     * @return  de verzameling keys
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new LinkedHashSet<>();
        collectKeys(root, keySet);
        return keySet; // retourneert de set van sleutels
    }
  
    private void collectKeys(AVLNode<K, V> node, Set<K> keySet) {
        if (node == null) return;
        collectKeys(node.left, keySet);
        keySet.add(node.key);
        collectKeys(node.right, keySet);
    }
  
    /**
     * Geeft de verzameling van values in gesorteerde volgorde
     * @return  de verzameling values
     */
    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        collectValues(root, values);
        return values; // retourneert de collectie van waarden
    }
  
    private void collectValues(AVLNode<K, V> node, List<V> values) {
        if (node == null) return;
        collectValues(node.left, values);
        values.add(node.value);
        collectValues(node.right, values);
    }

}
