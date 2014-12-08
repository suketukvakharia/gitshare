package tree;

public class TreeContainsLinear {
    
    
    
    
    public static <T extends Comparable<T>> LinearTreeNode<T> getKthNode(LinearTreeNode<T> rootNode, int k ) {
        
        // null check or k smaller than 0. Also return null if number of nodes is smaller than k.
        if(rootNode == null || k <= 0 || rootNode.numberOfnodes < k) return null;
        
        int travelledSoFar = 0;
        LinearTreeNode<T> current = rootNode;
        
        // loop until we find the value.
        while(current  != null) {
            
            int currentPosition = current.left == null? 0: current.left.numberOfnodes + 1;
            
            if(currentPosition + travelledSoFar == k ) {
                return current;
            }
            else if(currentPosition + travelledSoFar > k) {
                current = current.left;
            }
            else {
                travelledSoFar += currentPosition;
                current = current.right;
            }
        }
        return null;
    }
    
    
    
    
    public class LinearTreeNode<T extends Comparable<T>> implements ITreeNode<T> {
        LinearTreeNode<T> left, right;
        T value;
        int numberOfnodes;
        @Override
        public ITreeNode<T> getLeft() {
            return this.left;
        }
        @Override
        public void setLeft(ITreeNode<T> left) {
        }
        @Override
        public ITreeNode<T> getRight() {
            return this.right;
        }
        @Override
        public void setRight(ITreeNode<T> right) {
        }
        @Override
        public T getValue() {
            return this.value;
        }
        @Override
        public void setValue(T value) {
            this.value = value;
        }
    }

}
