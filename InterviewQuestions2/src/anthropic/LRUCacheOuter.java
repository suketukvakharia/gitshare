package anthropic;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LRUCacheOuter {

    /**
     * Build LRU cache using doubly linked list and HashMap
     */

    private class LRUCache<K extends Comparable<K>,V>  {
        private final int size;
        private final Map<K,Node> keyToVal;
        private final DoublyLinkedList lruLinkedList;

        public LRUCache(int size) {
            this.size = size;
            this.keyToVal = new HashMap<>();
            this.lruLinkedList = new DoublyLinkedList();
        }

        @Override
        public String toString() {
            return "LRUCache{" +
                    "size=" + size +
                    ", keyToVal=" + keyToVal +
                    ", lruLinkedList=" + lruLinkedList +
                    '}';
        }

        /**
         * Evicts the given key value from cache.
         * @param key
         */
        private void evict(K key) {
            Node node = keyToVal.get(key);
            if (node == null) {
                return;
            }
            keyToVal.remove(key);
            lruLinkedList.remove(node);
        }

        private void evictLast() {
            // find last entry
            Node tail = lruLinkedList.getTail();
            if (tail == null) {
                return;
            }
            lruLinkedList.remove(tail);
            keyToVal.remove(tail.key);
        }

        private void evictFirst() {
            // find last entry
            Node head = lruLinkedList.getHead();
            if (head == null) {
                return;
            }
            lruLinkedList.remove(head);
            keyToVal.remove(head.key);
        }


        private void put(K key, V val) {
            // if value exists remove it
            evict(key);
            System.err.println(this);

            // put into hash map
            Node append = lruLinkedList.append(val, key);
            keyToVal.put(key, append);

            // evict if over the limit on number of elements
            if (keyToVal.size() > size) {
                evictFirst();
            }
        }

        private V get(K key) {
            // get value from hash map
            Node node = keyToVal.get(key);
            if (node == null) {
                return null;
            }
            // update doubly linked list move the element to the end of the DLL
            lruLinkedList.moveToBack(node);
            return node.value;
        }

        private class Node {
            final V value;
            final K key;
            Node next = null;
            Node previous = null;

            public Node(V value, K key) {
                this.value = value;
                this.key = key;
            }

            @Override
            public String toString() {
                return "Node{" +
                        "value=" + value +
                        ", key=" + key +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                Node node = (Node) o;
                return Objects.equals(value, node.value) && Objects.equals(key, node.key) && Objects.equals(next, node.next) && Objects.equals(previous, node.previous);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, key, next, previous);
            }
        }

        private class DoublyLinkedList {
            private Node head;
            private Node tail;

            public DoublyLinkedList() {
                head = null;
                tail = null;
            }

            private Node getHead() {
                return head;
            }

            private Node getTail() {
                return tail;
            }

            @Override
            public String toString() {
                Node node = head;
                if (node == null) {
                    return "EMPTY";
                }
                StringBuilder sb = new StringBuilder();
                sb.append("[\n");
                while (node != null) {
                    sb.append(node.toString());
                    sb.append(",\n");
                    node = node.next;
                }
                sb.append("]");
                return  sb.toString();
            }

            // places element to end of the queue
            private Node append(V value, K key) {
                Node newNode = new Node(value, key);
                if (head == null || tail == null) {
                    head = newNode;
                    tail = newNode;
                    return newNode;
                }

                // tail needs to be re-assigned
                placeAfter(tail, newNode);
                tail = newNode;
                return tail;
            }

            private void placeAfter(Node before, Node newNodeAfter) {
                if (before == null) {
                    newNodeAfter.previous = null;
                    return;
                }
                newNodeAfter.next = before.next;
                before.next = newNodeAfter;

                newNodeAfter.previous = before;
                if (newNodeAfter.next != null)  {
                    newNodeAfter.next.previous = newNodeAfter;
                }
            }

            // Will need for LRU cache
            // Removes an element and moves it to end of the Doubly linked list
            private Node moveToBack(Node element) {
                if (element == null) {
                    return element;
                }

                // remove element first
                remove(element);

                placeAfter(tail, element);
                tail = element;
                if (head == null) {
                    head = element;
                }
                return tail;
            }

            /**
             * Also manage updating tail and head at the same time
             * @param element
             */
            private Node remove(Node element) {
                Node next = element.next;
                Node previous = element.previous;

                if (next == null && previous == null) {
                    head = null;
                    tail = null;
                }
                else if (next == null) {
                    tail = previous;
                    previous.next = null;
                }
                else if (previous == null) {
                    head = next;
                    next.previous = null;
                }
                else {
                    previous.next = next;
                    next.previous = previous;
                }
                element.next = null;
                element.previous = null;
                return element;
            }
        }
    }

    @Test
    public void test1() {
        LRUCache<String, String> lruCache = new LRUCache<>(2);
        System.err.println(lruCache);

        Assert.assertNull(lruCache.get("a"));

        // Verify we can add an element get it back
        lruCache.put("a", "A");
        System.err.println(lruCache);
        Assert.assertEquals(lruCache.get("a"), "A");
        // verify internal state
        System.err.println(lruCache);

        lruCache.put("b", "B");
        System.err.println(lruCache);
        Assert.assertEquals(lruCache.get("b"), "B");

        lruCache.put("c", "C");
        System.err.println(lruCache);
        Assert.assertEquals(lruCache.get("c"), "C");

        Assert.assertEquals(lruCache.get("b"), "B");
        lruCache.put("d", "D");
        System.err.println(lruCache);
        Assert.assertEquals(lruCache.get("d"), "D");
        Assert.assertNull(lruCache.get("a"));
        Assert.assertNull(lruCache.get("c"));

    }
}
