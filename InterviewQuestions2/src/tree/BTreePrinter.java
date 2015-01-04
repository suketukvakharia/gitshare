package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.junit.Test;


public class BTreePrinter {

    @Test
    public void testPrintTreeNew() {
        
        System.out.println( (2 << -1));
        System.out.println( (2 << 0));
        System.out.println( (2 << 1));

        
        AVLTree<Integer> tree = new AVLTree<>();

        Random rand = new Random();
        for(int i =0; i < 10; i++) {
            tree.add(rand.nextInt(90)+10);
        }
        
        BTreePrinter.printTreeNode(tree.root);
        
        System.out.println();
        System.out.println();
        
        BTreePrinter.printTreeNew(tree.root);
    }
    
    public static void printTreeNew(ITreeNode<Integer> root) {
        
        int d = BinaryTreeUtil.getHeight(root);
        
        Queue<NodePosition> current = new LinkedList<BTreePrinter.NodePosition>(),
                next = new LinkedList<BTreePrinter.NodePosition>();
        current.add(new NodePosition(root, (2 << d-1) -1));
        d--;
        
        int posInLine = 0;
        while(!current.isEmpty() || !next.isEmpty()) {
            if(current.isEmpty()) {
                current = next;
                next = new LinkedList<BTreePrinter.NodePosition>();
                d--;
                posInLine = 0;
                System.out.println();
            }
            
            NodePosition currentPos = current.remove();
            while(posInLine < currentPos.position) {
                posInLine++;
                System.out.print("   ");
            }
            System.out.print(String.format("%3d", currentPos.node.getValue()));
            posInLine++;
            
            if(currentPos.node.getLeft() != null) {
                next.add(new NodePosition(currentPos.node.getLeft(), currentPos.position - (d-1 == -1 ? 1:(2 << d-1)) ));
            }
            
            if(currentPos.node.getRight() != null) {
                next.add(new NodePosition(currentPos.node.getRight(), currentPos.position + (d-1 == -1 ? 1:(2 << d-1)) ));
            }
        }
        System.out.println();
    }
    
    private static class NodePosition {
        public NodePosition(ITreeNode<Integer> node, int position) {
            this.node = node;
            this.position = position;
        }
        ITreeNode<Integer> node;
        int position;
    }
    
    
    
    public static <T extends Comparable<T>> void printTreeNode(ITreeNode<T> root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printTreeNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<T>> void printTreeNodeInternal(List<ITreeNode<T>> TreeNodes, int level, int maxLevel) {
        if (TreeNodes.isEmpty() || BTreePrinter.isAllElementsNull(TreeNodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<ITreeNode<T>> newTreeNodes = new ArrayList<ITreeNode<T>>();
        for (ITreeNode<T> node : TreeNodes) {
            if (node != null) {
                System.out.print(node.getValue());
                newTreeNodes.add(node.getLeft());
                newTreeNodes.add(node.getRight());
            } else {
                newTreeNodes.add(null);
                newTreeNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < TreeNodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (TreeNodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (TreeNodes.get(j).getLeft() != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (TreeNodes.get(j).getRight() != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printTreeNodeInternal(newTreeNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<T>> int maxLevel(ITreeNode<T> TreeNode) {
        if (TreeNode == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(TreeNode.getLeft()), BTreePrinter.maxLevel(TreeNode.getRight())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}