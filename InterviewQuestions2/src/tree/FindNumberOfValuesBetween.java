package tree;

/**
 * http://www.careercup.com/question?id=5165570324430848
 * @author suketu
 *
 */
public class FindNumberOfValuesBetween {
    
    
    public int getNumberOfChildrenBetween(int from, int to, InternalNode root) {
        
        if(from > to) return -1;
        
        ValueSummary fromSummary = getValueSummary(from, root), toSummary = getValueSummary(to, root);
        
        return fromSummary.greaterThan - toSummary.greaterThan + (fromSummary.valueExists? 1:0) + (toSummary.valueExists? 1:0);
    }
    
    private ValueSummary getValueSummary(int from, InternalNode root) {
        ValueSummary newSummary = new ValueSummary();
        getValueSummary(from, root, newSummary);
        return newSummary;
    }

    private void getValueSummary(int value, InternalNode node, ValueSummary currentSummary) {
        
        if(node == null) return;
        
        if(node.value < value) {
            currentSummary.lessThan += 1 + node.leftChildrenCount;
            getValueSummary(value, node.right, currentSummary);
        }
        else if(node.value > value) {
            currentSummary.greaterThan += 1 + node.rightChildrenCount;
            getValueSummary(value, node.left, currentSummary);
        }
        else {
            currentSummary.lessThan += node.leftChildrenCount;
            currentSummary.greaterThan +=  node.rightChildrenCount;
            currentSummary.valueExists = true;
        }
    }

    private class ValueSummary {
        int greaterThan = 0, lessThan = 0;
        boolean valueExists = false;
    }
    
    private class InternalNode {
        public int value;
        public InternalNode left, right;
        public int leftChildrenCount, rightChildrenCount;
    }
}
