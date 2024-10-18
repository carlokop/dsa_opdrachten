package datastructure;

public class TreeMap extends AbstractSortedMap<K,V>  {
  
  protected BalancedBinaryTree<K,V> tree = new BalancedBinaryTree<>();

  public TreeMap() {
    super();
    tree.addRoot(null);
  }
  
  public TreeMap(Comparator<K> comp) {
    super();
    tree.addRoot(null);
  }

}
