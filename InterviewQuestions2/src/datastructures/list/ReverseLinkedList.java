package datastructures.list;

import java.util.Random;

public class ReverseLinkedList {

    
    private static class Node {
        int value;
        Node next;
    }
    
    public static void main(String[] args) {
        
        Node root = new Node();
        root.value = 10;
        
        
        SetupRoot(root, 10);
        Print(root);
        
        Node newRoot = ReverseList(root);
        
        Print(newRoot);
        Print(root);

        
    }

    private static void Print(Node root) {
        Node current = root;
        while(current != null) {
            System.out.print(current.value+ "->");
            current = current.next;
        }
        System.out.println();
    }

    private static Node ReverseList(Node root) {
        
        Node previous = null;
        Node current = root;
        Node next = root.next;
        while(current.next != null) {
            
            current.next = previous;
            previous = current;
            current = next;
            next = next.next;
        }
        current.next = previous;
        return current;
    }

    private static void SetupRoot(Node root, int i) {
        
        Random rand = new Random();
        Node current = root;
        for(; i > 0; i--) {
            int nextVal = rand.nextInt();
            Node next = new Node();
            next.value = nextVal;
            current.next = next;
            current = next;
        }
    }
}

