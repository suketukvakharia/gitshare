package tree;

import java.util.Stack;

import org.junit.Test;

public class StackTreeTraversal {
	
	
	@Test 
	public void testPreOrder() {
TreeNode<Integer> root = getTree();
		
		doPreOrderTraversal(root);
	}
	
	@Test
	public void testInOrder() {
		TreeNode<Integer> root = getTree();
		
		doInOrderTraversal(root);
	}

	private TreeNode<Integer> getTree() {
		TreeNode<Integer> root = new TreeNode<Integer>(5);
		root.left = new TreeNode<Integer>(3);
		root.left.setLeft(new TreeNode<Integer>(1));
		root.left.setRight(new TreeNode<Integer>(4));
		
		root.right = new TreeNode<Integer>(15);
		return root;
	}
	
	public void doPreOrderTraversal(ITreeNode<Integer> root) {
		ITreeNode<Integer> current = root;
		Stack<ITreeNode<Integer>> stack = new Stack<ITreeNode<Integer>>();
		
		while(!stack.isEmpty() || current != null) {
			if(current != null) {
				System.out.println(current.getValue());
				stack.push(current.getRight());
				current = current.getLeft();
			}
			else {
				current = stack.pop();
			}
		}
	}
	
	public void doInOrderTraversal(ITreeNode<Integer> root) {
		ITreeNode<Integer> current = root;
		Stack<ITreeNode<Integer>> stack = new Stack<ITreeNode<Integer>>();
		
		while(!stack.isEmpty() || current != null) {
			
			if(current != null) {
				stack.push(current);
				current = current.getLeft();
			}
			else {
				current = stack.pop();
				System.out.println(current.getValue());
				current = current.getRight();
			}
		}
		
	}

}
