package datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


/**
 * http://www.careercup.com/question?id=5106806204399616
 * @author suketu
 *
 */
public class ShortestUniquePrefix {

    @Test 
    public void simpleTest() {
        
        List<String> strings = Arrays.asList("bearcat", "bear", "bears");
        this.printShortestPrefixes(strings);
    }
    
    public void printShortestPrefixes(List<String> args) {
        
        // put them all in the trie.
        TrieNode<Character> root = new TrieNode<Character>(null);
        for(String arg: args) {
            if(arg == null) continue;
            TrieNode<Character> current = root;
            
            for(char c: arg.toCharArray()) {
                current = current.addChildNode(c);
            }
            current.addChildNode(null);
        }
        printPrefixes(root);
    }
    
    
    private void printPrefixes(TrieNode<Character> node) {
        
        List<String> uniquePrefixes = new LinkedList<>();
        
        for(TrieNode<Character> childNode: node.getNextNodes()) {
            getUniquePrefixes(childNode, uniquePrefixes, "");
        }
        for(String prefix: uniquePrefixes) {
            System.out.println(prefix);
        }
    }


    private void getUniquePrefixes(TrieNode<Character> node,
            List<String> uniquePrefixes, String prefixSoFar) {
        
        if(node.value == null) {
            uniquePrefixes.add(prefixSoFar);
            return;
        }
        List<TrieNode<Character>> childNodes = node.getNextNodes();
        
        if(node.getPaths() <= 1) {
            // prefix done.
            uniquePrefixes.add(prefixSoFar + node.value);
        }
        else {
            for(TrieNode<Character> childNode:childNodes) {
                getUniquePrefixes(childNode, uniquePrefixes, prefixSoFar+node.value);
            }
        }
        
    }


    private class TrieNode<T extends Comparable<T>> {
        
        private List<TrieNode<T>> nextNodes = new ArrayList<>();
        private final T value;
        private int paths = 0;
        
        public TrieNode(T value) {
            super();
            this.value = value;
        }
        
        public TrieNode<T> addChildNode(T value) {
            TrieNode<T> newChildNode = this.getNextNode(value);
            if(newChildNode == null) {
                 newChildNode = new TrieNode<T>(value);
                 this.nextNodes.add(newChildNode);
            }
            this.paths++;
            return newChildNode;
        }

        public TrieNode<T> getNextNode(T value) {
            for(TrieNode<T> node: this.nextNodes) {
                if(node.value == null && value == null) return node;
                if(node.value == null) continue;
                if(node.value.equals(value)) {
                    return node;
                }
            }
            return null;
        }

        public List<TrieNode<T>> getNextNodes() {
            return nextNodes;
        }

        public int getPaths() {
            return paths;
        }
    }
}
