package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;




public class AVLTreeTest {
    
    Logger logger = LogManager.getLogger(AVLTreeTest.class);
//    @Test 
    public void simpleTest() {
        
        
        AVLTree<Integer> tree = new AVLTree<>();
        
        Random rand = new Random();
        for(int i =0; i < 10; i++) {
            tree.add(rand.nextInt(90)+10);
        }
        
        BTreePrinter.printTreeNode(tree.root);
        
        System.out.println(BinaryTreeUtil.isBalanced(tree.root));
        
    }
    
    
    @Test 
    public void removeTest() {
        
        
        AVLTree<Integer> tree = new AVLTree<>();
        
        Random rand = new Random();
        List<Integer> usedInts = new ArrayList<>();
        
        for(int i =0; i < 10; i++) {
            int randomNumber = rand.nextInt(90)+10;
            tree.add(randomNumber);
            usedInts.add(randomNumber);
        }
        
        logger.debug("After Adding All");
        BTreePrinter.printTreeNode(tree.root);
        
        // removing.
        for(int toRemove: usedInts) {
            
            tree.remove(toRemove);
            
            logger.debug("After removing:" + toRemove);
            if(tree.root != null) 
                BTreePrinter.printTreeNode(tree.root);
            
            Assert.assertTrue(BinaryTreeUtil.isBalanced(tree.root));
        }
        
        System.out.println(BinaryTreeUtil.isBalanced(tree.root));
    }
}
