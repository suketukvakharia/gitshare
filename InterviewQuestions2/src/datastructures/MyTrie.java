package datastructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import diagnostic.LogMemory;

public class MyTrie {

    
    public static void main(String[] args) throws Exception {
        
        LogMemory.log();

        
        BufferedReader br = new BufferedReader(new FileReader(new File("english.txt")));
        
        
//        Set<String> dict = new HashSet<String>();
//        String word;
//        while((word = br.readLine()) != null) {
//            dict.add(word);
//        }
//        
//        System.out.println(dict.contains("zygoma"));
//        System.out.println(dict.contains("aani"));
//        System.out.println(dict.contains("xxxxxxxdsfd"));
        
        
        MyTrie trie = new MyTrie();
        
        String word;
        while((word = br.readLine()) != null) {
            trie.put(word);
        }
        
        System.out.println(trie.contains("zygoma"));
        System.out.println(trie.contains("aani"));
        System.out.println(trie.contains("xxxxxxxdsfd"));
        
        LogMemory.log();
        Thread.sleep(2000);

        
        br.close();
    }
    
    private final TrieNode root;
    
    public MyTrie() {
        root = new TrieNode();
    }
    /**
     * Simply says if the value is in the associated trie.
     * @param value
     */
    public boolean contains(String value) {
        char[] searchFor = value.toCharArray();
        
        TrieNode node = root;
        
        for(int i = 0; i < searchFor.length && node != null; i++) {
            node = node.childNodes.get(searchFor[i]);
        }
        return node != null && node.isLeafNode;
    }
    
    public void put(String value) {
        
        char[] putCharArr = value.toCharArray();
        TrieNode node = root;
        
        for(int i =0; i < putCharArr.length; i++) {
            
            TrieNode child = node.childNodes.get(putCharArr[i]);
            
            if(child == null) {
                child = new TrieNode();
                node.childNodes.put(putCharArr[i],child);
            }
            node = child;
        }
        node.isLeafNode = true;
    }
    
    public TrieNode getRoot() {
        return root;
    }

    public class TrieNode {
        
        public TrieNode() {
            this.childNodes = new HashMap<>();
        }
        
        public Map<Character,TrieNode> childNodes; // get the character by casting it.
        boolean isLeafNode;
    }
}
