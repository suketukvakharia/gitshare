package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

public class DjiktraDistance {
    
    @Test
    public void testSuccess() {
        
        GraphNode[] nodes = GraphUtil.generateRandomBidirectionalGraph(5);
        
        GraphPrinter.printAsAdjacency(nodes);
        
        Map<GraphNode, Integer> runDjikstra = runDjikstra(nodes[0]);
        
        for(Entry<GraphNode, Integer> entry: runDjikstra.entrySet()) {
            System.out.println("Distance to:" + entry.getKey().value + " is:" + entry.getValue());
        }
    }
    
    public static Map<GraphNode, Integer> runDjikstra(GraphNode startingNode) {
        
        Map<GraphNode, Integer> minPathToNodes = new HashMap<GraphNode, Integer>();
        
        Set<GraphNode> visitedNodes = new HashSet<GraphNode>();
        
        Map<GraphNode, Integer> currentMinPathToNodes = new HashMap<GraphNode, Integer>();
        currentMinPathToNodes.put(startingNode, 0);
        
        while(!currentMinPathToNodes.isEmpty()) {
            
            // get min from currentNodes
            GraphNode minCostNode = null;
            int minCost = Integer.MAX_VALUE;
            for(Entry<GraphNode, Integer> currentNodeEntry: currentMinPathToNodes.entrySet()) {
                if(currentNodeEntry.getValue() < minCost) {
                    minCost = currentNodeEntry.getValue();
                    minCostNode = currentNodeEntry.getKey();
                }
            }
            
            currentMinPathToNodes.remove(minCostNode);
            visitedNodes.add(minCostNode);
            
            // iterate over all adjacencies of min cost node and update nodes as needed.
            for(Entry<GraphNode, Integer> adjacency: minCostNode.adjacencies.entrySet()) {
                
                // update toReturn value if its better
                if(!minPathToNodes.containsKey(adjacency.getKey()) || minPathToNodes.get(adjacency.getKey()) > adjacency.getValue()) {
                    minPathToNodes.put(adjacency.getKey(), adjacency.getValue());
                }
                
                // update currentNodes value if not visited and its better.
                if( !visitedNodes.contains(adjacency.getKey()) && 
                        (!currentMinPathToNodes.containsKey(adjacency.getKey()) || currentMinPathToNodes.get(adjacency.getKey()) > adjacency.getValue() )) {
                    currentMinPathToNodes.put(adjacency.getKey(), adjacency.getValue());
                }
            }
        }
        
        return minPathToNodes;
    }
}
