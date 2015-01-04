package tree;

public class BinaryTreeUtil {

    
    public static <T extends Comparable<T>> boolean isBalanced(ITreeNode<T> root) {
        int depth = depthPlusBalanced(root);
        
        if(depth >= 0)
            return true;
        else 
            return false;
    }
    
    public static <T extends Comparable<T>> int getHeight(ITreeNode<T> node) {
        if(node == null) return 0;
        
        return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getLeft()));
        
    }

    private static <T extends Comparable<T>> int depthPlusBalanced(ITreeNode<T> root) {
        
        if(root == null) return 0;
        
        int leftDepth = depthPlusBalanced(root.getLeft());
        
        int rightDepth = depthPlusBalanced(root.getRight());
        
        if(Math.abs(leftDepth - rightDepth) > 1 || leftDepth < 0 || rightDepth < 0) {
            return -1;
        }
        
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
