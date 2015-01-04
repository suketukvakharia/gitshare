package graph;

import java.util.HashMap;
import java.util.Map;

public class GraphNode {
    
    public GraphNode(int value) {
        this.adjacencies = new HashMap<GraphNode, Integer>();
        this.value = value;
    }
    Map<GraphNode,Integer> adjacencies;
    
    int value;
    
    public void addAdjacency(GraphNode node, int cost) {
        this.adjacencies.put(node, cost);
    }

}
