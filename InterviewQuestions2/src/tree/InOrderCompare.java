package tree;

import java.util.Stack;

import org.junit.Test;


/**
 * http://www.careercup.com/question?id=5086938656669696
 * Compare two binary trees in order.
 * @author suketu
 *
 */
public class InOrderCompare {
	
	@Test
	public void testSuccess() {
		
		TreeNode<Integer> root = new TreeNode<Integer>(10);
		root.value = 10;
		root.left = new TreeNode<Integer>(2);
		root.right = new TreeNode<Integer>(4);
		
		
		TreeNode<Integer> root2= new TreeNode<Integer>(4);
		root2.left = new TreeNode<Integer>(10);
		root2.left.setLeft(new TreeNode<Integer>(2));
		boolean areEqual = CompareTrees(root,  root2);
		
		System.out.println(areEqual);
	}

	public boolean CompareTrees(ITreeNode<Integer> root1, ITreeNode<Integer> root2) {
		Stack<ITreeNode<Integer>> tree1 = new Stack<ITreeNode<Integer>>(), tree2 = new Stack<ITreeNode<Integer>>();
		
		ITreeNode<Integer> temp = root1;
		
		while(temp != null) {
			tree1.push(temp);
			temp = temp.getLeft();
		}
		temp = root2;
		while(temp != null) {
			tree2.push(temp);
			temp = temp.getLeft();
		}
		
		ITreeNode<Integer> toExamine1 = null, toExamine2 = null;
		
		while(true) {
			// if both empty
			if(tree1.isEmpty() && tree2.isEmpty()) {
				return true;
			}
			
			// if only one empty
			if(tree1.isEmpty() || tree2.isEmpty()) {
				return false;
			}
			
			// get next element, queue right and all its left children.
			toExamine1 = tree1.pop();
			temp = toExamine1.getRight();
			while(temp != null) {
				tree1.push(temp);
				temp = temp.getLeft();
			}
			
			toExamine2 = tree2.pop();
			temp = toExamine2.getRight();
			while(temp != null) {
				tree2.push(temp);
				temp = temp.getLeft();
			}
			
			if(!toExamine1.getValue().equals(toExamine2.getValue())) {
				System.out.println(toExamine1.getValue());
				System.out.println(toExamine2.getValue());
				return false;
			}
		}
	}
}
