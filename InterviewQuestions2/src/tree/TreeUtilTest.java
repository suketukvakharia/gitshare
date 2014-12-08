package tree;

import org.junit.Assert;
import org.junit.Test;


public class TreeUtilTest {

    
    
    
    @Test
    public void testISBalancedSuccess() {
        
        TreeNode<Integer> node = new TreeNode<>();
        node.left = new TreeNode<>(); 
        node.right = new TreeNode<>();
        
        
        node.getLeft().setLeft(new TreeNode<Integer>());
        node.getLeft().setRight(new TreeNode<Integer>());
        
        Assert.assertTrue(BinaryTreeUtil.isBalanced(node));
    }
    
    @Test
    public void testISBalancedSuccessNull() {
        
        TreeNode<Integer> node = null;
        
        Assert.assertTrue(BinaryTreeUtil.isBalanced(node));
    }
    
    @Test
    public void testISBalancedFailure() {
        
        TreeNode<Integer> node = new TreeNode<>();
        node.left = new TreeNode<>(); 
        
        node.getLeft().setLeft(new TreeNode<Integer>());
        node.getLeft().setRight(new TreeNode<Integer>());
        
        Assert.assertFalse(BinaryTreeUtil.isBalanced(node));
    }
    
}
