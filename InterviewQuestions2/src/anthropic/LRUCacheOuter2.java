package anthropic;

import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Assert;

/**
 * Implement an LRU cache with serialization and evolving constraints.
 *
 * Your program is running slowly because it's accessing data from disk over and over again.
 * To improve the performance, you want to build a simple key-value store to cache this data in
 * memory, but you also want to limit the amount of memory used. You decide to build a caching
 * system that only keeps the N most recently used items—also known as a least recently used (LRU)
 * cache.
 *
 * Write a class LRUCache(n) that accepts a size limit n. It should support a set(key, value)
 * method for inserting or updating items and a get(key) method for retrieving items. Can you
 * implement a solution where both of these methods run in O(1) time?
 *
 *
 * We will use a combination of hash map and linked list.
 * Linked list will keep track of what's utilized.
 */
public class LRUCacheOuter2<K, V> {

  private class Node {
    // mutable values
    K key;
    V val;
    Node next;
    Node previous;
    private Node() {

    }

    @Override
    public String toString() {
      return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
          .add("key=" + key)
          .add("val=" + val)
          .toString();
    }
  }

  private final ConcurrentHashMap<K,Node> cache  = new ConcurrentHashMap();
  private final Node left = new Node();
  private final Node right = new Node();
  int capacity;
  public LRUCacheOuter2(int capacity) {
    // setup the Linked list during instantiation
    left.next = right;
    left.previous = null;
    // kv are already null
    right.next = null;
    right.previous = left;

    // validate the size of this*
    this.capacity = capacity;
  }

  public LRUCacheOuter2() {
    this(100);
  }

  public void removeFromLL(Node node) {
    Node nextNode = node.next;
    Node prevNode = node.previous;

    prevNode.next = nextNode;
    nextNode.previous = prevNode;
  }

  public void insertLeft(Node node) {
    Node leftNode = this.left;
    Node midNode = node;
    Node rightNode = this.left.next;

    leftNode.next = midNode;
    midNode.next = rightNode;
    // right next don't matter

    rightNode.previous = midNode;
    midNode.previous = leftNode;
  }


  public void put(K key, V value) {

    Node node = new Node();
    node.key = key;
    node.val = value;
    insertLeft(node);
    this.cache.put(key, node);

    if (this.cache.size() > capacity) {
      // find the key to remove
      Node nodeToRemove = right.previous;
      this.cache.remove(nodeToRemove.key);
      System.err.println("Removing node:" +  nodeToRemove);
      // remove from linked list*
      removeFromLL(nodeToRemove);
    }
  }

  public V get(K key) {
    Node toReturn = this.cache.get(key);
    if (toReturn == null) {
      return null;
    }

    // manage LL*
    removeFromLL(toReturn);
    insertLeft(toReturn);
    return toReturn.val;
  }

  public static void main() {
    // create cache
    LRUCacheOuter2<String, String> cache = new LRUCacheOuter2<>(2);

    cache.put("a", "A");
    cache.put("b", "B");
    cache.put("c", "C");

    Assert.assertNull(cache.get("a"));
    Assert.assertEquals("B", cache.get("b"));
    Assert.assertEquals("C", cache.get("c"));
  }
}
