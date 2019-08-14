package datastructures.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ReverseDoublyLinkedList {

    public class Node<T extends Comparable<T>> {
        T value;
        Node next;
        Node prev;

        Node(T value, Node next, Node prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                    .add("value=" + value)
                    .add("prev=" + (prev != null ? prev.value : "null"))
                    .add("next=" + next)
                    .toString();
        }
    }

    private Node reverseDoublyLinkedList(Node root) {
        if (root == null || root.next == null) {
            return root;
        }

        // reverse a node at a time.
        Node current = root;
        Node prev = null;
        Node next;
        while (current != null) {

            next = current.next;

            current.next = prev;
            current.prev = next;

            prev = current;
            current = next;
        }
        return prev;
    }

    @Test
    public void test3() {
        List<Node<Integer>> nodes = getNodes(3);
        Node<Integer> head = nodes.get(0);
        System.out.println(head);
        Node newHead = reverseDoublyLinkedList(head);
        System.out.println(newHead);
    }

    @Test
    public void test2() {
        List<Node<Integer>> nodes = getNodes(2);
        Node<Integer> head = nodes.get(0);
        System.out.println(head);
        Node newHead = reverseDoublyLinkedList(head);
        System.out.println(newHead);
    }

    @Test
    public void test1() {
        List<Node<Integer>> nodes = getNodes(1);
        Node<Integer> head = nodes.get(0);
        System.out.println(head);
        Node newHead = reverseDoublyLinkedList(head);
        System.out.println(newHead);
    }

    private List<Node<Integer>> getNodes(int count) {
        List<Node<Integer>> nodes = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            nodes.add(new Node<>(i, null, null));
        }
        for (int i = 0; i < nodes.size(); i++) {
            Node<Integer> current = nodes.get(i);
            Node<Integer> prev = (i - 1) > 0 ? nodes.get(i-1) : null;
            Node<Integer> next = (i + 1) < nodes.size() ? nodes.get(i+1) : null;
            current.next = next;
            current.prev = prev;
        }
        return nodes;
    }
}
