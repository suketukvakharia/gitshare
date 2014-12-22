package datastructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import datastructures.MyTrie.TrieNode;

/**
 * http://www.careercup.com/question?id=5841660122497024
 * 
 * @author suketu
 *
 */
public class UseCharsToFormWords {

    String toTest = "xxxxismyfriendwhatthex";
    
    @Test
    public void testTrieSolution() throws Exception {
        
        TrieNode dict = loadTrie("english.txt");
        
        
        List<Character> toUse = new LinkedList<Character>();
        for(Character character: toTest.toCharArray()) 
            toUse.add(character);
        
        System.out.println("Trie" + canUseAll(dict, toUse));
    }
    
    @Test 
    public void testMapSolution() throws Exception {
        
        Set<String> dict = loadSetDict("english.txt");
        
        System.out.println("Map" + canUseAll(dict, toTest));
    }
    
    public TrieNode loadTrie(String fileName) throws Exception {
        MyTrie trie = new MyTrie();
        
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        
        String word;
        
        while((word = br.readLine()) != null) {
            trie.put(word);
        }
        
        return trie.getRoot();
    }
    
    private Set<String> loadSetDict(String fileName) throws Exception {
        
        Set<String> dict = new HashSet<String>(); 
        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
        
        String word;
        
        while((word = br.readLine()) != null) {
            dict.add(word);
        }
        return dict;
    }

    public boolean canUseAll(TrieNode root, List<Character> toUse) {
        return canUseAll(root, root, toUse, new HashSet<String>(), "");
    }
    
    private boolean canUseAll(TrieNode root, TrieNode current, List<Character> toUse,
            Set<String> usedWords, String currentWord) {
        
        if(toUse.isEmpty() && current.isLeafNode && !usedWords.contains(currentWord)) {
            System.out.println("Using words: ");
            for(String word: usedWords) {
                System.out.print(word+",");
            }
            System.out.println();
            return true;
        }
        
        for (Entry<Character, TrieNode> entry : current.childNodes.entrySet()) {
            
            if(toUse.contains(entry.getKey())) {
                toUse.remove(entry.getKey());
                if(canUseAll(root, entry.getValue(), toUse, usedWords, currentWord + entry.getKey())) {
                    return true;
                }
                toUse.add(entry.getKey());
            }
        }
        
        if(current.isLeafNode && !usedWords.contains(currentWord)) {
            usedWords.add(currentWord);
            if(canUseAll(root, root, toUse, usedWords, "")) {
                return true;
            }
            usedWords.remove(currentWord);
        }
        return false;
    }
    
    public boolean canUseAll(Set<String> dict, String toUse) {
        
        int[] toUseArr = stringToArr(toUse);
        
        return canUseAll(dict, toUseArr, new HashSet<String>());
        
    }

    private boolean canUseAll(Set<String> dict, int[] toUseArr,
            HashSet<String> usedWords) {
        
        if(isEmpty(toUseArr)) {
            return true;
        }
        
        for(String word: dict) {
            
            int[] wordArr = stringToArr(word);
            
            if(!usedWords.contains(word) && doesFit(wordArr, toUseArr)) {
                
                usedWords.add(word);
                removeArr(wordArr,toUseArr);
                if(canUseAll(dict, toUseArr, usedWords)) { 
                    return true;
                }
                addArr(wordArr,toUseArr);
                usedWords.remove(word);
            }
        }
        return false;
    }

    private boolean isEmpty(int[] toUseArr) {
        int i; 
        for(i = 0; i < toUseArr.length && toUseArr[i] == 0; i++) ;
        return i == toUseArr.length;
    }

    private void addArr(int[] wordArr, int[] toUseArr) {
        for(int i = 0; i < wordArr.length; i++) {
            toUseArr[i] = toUseArr[i] + wordArr[i];
        }
    }

    private void removeArr(int[] wordArr, int[] toUseArr) {
        for(int i = 0; i < wordArr.length; i++) {
            toUseArr[i] = toUseArr[i] - wordArr[i];
        }
    }

    private boolean doesFit(int[] wordArr, int[] toUseArr) {
        int i;
        for(i = 0; i < wordArr.length && wordArr[i] <= toUseArr[i]; i++);
        return i== wordArr.length;
    }

    private int[] stringToArr(String toUse) {
        int[] toReturn = new int[128];
        for(char toUseChar: toUse.toCharArray()) 
            toReturn[toUseChar]++;
        return toReturn;
    }
}
