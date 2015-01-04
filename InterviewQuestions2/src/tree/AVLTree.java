package tree;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AVLTree<T extends Comparable<T>> {
    
    
    Logger logger = LogManager.getLogger(AVLTree.class);

    public AVLNode root;
    
    
    public boolean remove(T value) {
        if(value == null) return false;
        
        return remove(this.root, value);
    }
    
    private boolean remove(AVLNode node, T value) {
        if(node == null || node.value == null) return false;
        
        int comparison = node.value.compareTo(value);
        
        if(comparison == 0) {
            // value is equal.
            
            removeNode(node);
        }
        else if(comparison > 0) {
            // look in the left subtree
            remove(node.left, value);
        }
        else {
            // look in the right subtree
            remove(node.right, value);
        }

        return true;
    }
    
    
    private void removeNode(AVLNode node) {
        
        if(node == this.root && node.left == null && node.right == null) {
            this.root = null;
            return;
        }
        
        logger.debug("Attempting to remove node:" + node);
        BTreePrinter.printTreeNode(this.root);
        
        // find the next smallest element to replace this with. 
        AVLNode toReplaceWith;
        
        if(node.left == null && node.right == null) {
            resetParentLink(node);
            adjustParents(node.parent);
            node = null; 
            return;
        } else if(node.right != null ){
            toReplaceWith = node.right;
            while(toReplaceWith.left != null) toReplaceWith = toReplaceWith.left;
        } else {
            toReplaceWith = node.left;
            while(toReplaceWith.right != null) toReplaceWith = toReplaceWith.right;
        }
        
        // reassign left child to parent of nextSmallest.
        if(toReplaceWith.parent.left == toReplaceWith) {
            toReplaceWith.parent.setLeft(toReplaceWith.left);
        } else {
            toReplaceWith.parent.setRight(toReplaceWith.left);
        }
        
        // replace value of node!
        node.value = toReplaceWith.value;
        
        // nextSmallest is effectively out 
        // Adjust all parents of nextSmallest if needed 
        
        adjustParents(toReplaceWith.parent);
    }

    private void adjustParents(AVLNode toAdjustNode) {
        
        while(toAdjustNode != null) {
            logger.debug("Adjusting node:" + toAdjustNode);
            adjustBalance(toAdjustNode);
            logger.debug("Done Adjusting node:" + toAdjustNode);
            
            toAdjustNode = toAdjustNode.parent;
        }
    }

    private void resetParentLink(AVLNode node) {
        if(node.parent.left == node) node.parent.left = null;
        else node.parent.right = null;
        
    }

    public void add(T value) {
        logger.debug("ADDING: " + value);
        if(this.root == null) {
            this.root = new AVLNode(value);
        } else {
            // find the best place for this node.
            add(this.root, value);
        }
        logger.debug("");
        logger.debug("");
    }

    
    private void add(AVLNode node, T value) {
        int comparison = node.value.compareTo(value);
        
        if(comparison == 0) {
            // preventing duplicate
            return;
        }
        else if(comparison < 0) {
            // node is smaller.
            if(node.right == null) {
                node.right = new AVLNode(value, node);
            }
            else {
                add(node.right, value);
            }
        }
        else if(comparison > 0) {
            // node is larger.
            if(node.left == null) {
                node.left = new AVLNode(value, node);
            }
            else {
                add(node.left, value);
            }
        }
        
        // Make sure we are balanced.
        adjustBalance(node);
        
        return;
    }

    private void updateHeight(AVLNode node) {
        node.height = Math.max(node.right == null ? 0 : node.right.height, node.left == null ? 0 : node.left.height) + 1;
    }

    private void adjustBalance(AVLNode node) {
        int balance = getBalanceFactor(node);
        logger.debug(node);
        
        if(balance == 2) {
            logger.debug("Left Left case.");
            // left left balanced.
             rotateRight(node);
        }
        else if(balance == -2) {
            
            logger.debug("Right Right case.");
            // right right case.
            rotateLeft(node);
        }
        updateHeight(node);
    }

    private void rotateLeft(AVLNode node) {
        logger.debug("Before Rotate Left:" + node);
        BTreePrinter.printTreeNode(this.root);
        
        if(getBalanceFactor(node.right) == 1) {
            this.rotateRight(node.right);
            
            logger.debug("Intermediate Rotate Right:" + node);
            BTreePrinter.printTreeNode(this.root);
        }
        
        AVLNode rc = node.right, rclc = node.right.left;
        logger.debug("Rotate right:" + node.value + " RightChild:" + node.right.value);
        
        if(node.parent != null) {
            if(node.parent.left == node) node.parent.setLeft(rc);
            else node.parent.setRight(rc);
        } else {
             logger.debug("Setting root:" + rc);
            this.root = rc;
            rc.setParent(null);
        }
        node.setRight(rclc);
        rc.setLeft(node);
        
        // recalculate heights where needed.
        updateHeight(node);
        updateHeight(rc);
        
        logger.debug("After Rotate Left:" + node);
        BTreePrinter.printTreeNode(this.root);
    }

    private void rotateRight(AVLNode node) {
        
        logger.debug("Before Rotate Right:" + node);
        BTreePrinter.printTreeNode(this.root);
        
        if(getBalanceFactor(node.left) == -1) {
            this.rotateLeft(node.left);
            
            logger.debug("Intermediate Rotate Left:" + node);
            BTreePrinter.printTreeNode(this.root);
        }
        
        AVLNode lc = node.left, lcrc = node.left.right;
        
        if(node.parent != null) {
            if(node.parent.left == node) node.parent.setLeft(lc);
            else node.parent.setRight(lc);
        } else {
            logger.debug("Setting root:" + lc);
            this.root = lc;
            lc.setParent(null);
        }

        node.setLeft(lcrc);
        lc.setRight(node);
        
        // recalculate heights where needed.
        updateHeight(node);
        updateHeight(lc);
        
        logger.debug("After Rotate Right:" + node);
        BTreePrinter.printTreeNode(this.root);
    }
    
    private int getBalanceFactor(AVLNode node) {
        int balance;
        int rheight = node.right == null ? 0 : node.right.height;
        int lheight = node.left == null ? 0 : node.left.height;
        balance = lheight - rheight;
        return balance;
    }

    public class AVLNode implements ITreeNode<T> {
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node value:" + this.value);
            
            if(this.left != null) {
                sb.append(" left:" + this.left.value + " height:" + this.left.height);
            } else {
                sb.append(" left:    height: ");
            }
            
            if(this.right != null) {
                sb.append(" right:" + this.right.value + " height:" + this.right.height);
            } else {
                sb.append(" right:    height: ");
            }
            
            if(this.parent != null) {
                sb.append(" parent:" + this.parent.value + " height:" + this.parent.height);
            } else {
                sb.append(" parent:    height: ");
            }
            return sb.toString();
        }

        AVLNode left, right, parent;
        int height = 1;
        T value;
        
        public AVLNode(T value) {
            this.value = value;
        }
        
        public AVLNode(T value, AVLNode parent) {
            this.value = value;
            this.parent = parent;
        }
        
        @Override
        public ITreeNode<T> getLeft() {
            return this.left;
        }
        
        public AVLNode getParent() {
            return this.parent;
        }
        
        public void setParent(AVLNode parent) {
            this.parent = parent;
        }
        
        @Override
        public void setLeft(ITreeNode<T> left) {
            if(left == null || left instanceof AVLTree.AVLNode) {
                AVLNode leftCasted = (AVLNode) left;
                this.left = leftCasted;
                if(leftCasted != null) leftCasted.parent = this;
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        
        @Override
        public ITreeNode<T> getRight() {
            return this.right;
        }
        
        @Override
        public void setRight(ITreeNode<T> right) {
            if(right == null || right instanceof AVLTree.AVLNode) {
                AVLNode rightCasted = (AVLNode) right;
                this.right = rightCasted;
                if(rightCasted != null) rightCasted.parent = this;
            } else {
                throw new IllegalArgumentException();
            }
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
