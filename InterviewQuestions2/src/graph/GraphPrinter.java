package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GraphPrinter {

    
    public static void printAsAdjacency(GraphNode[] nodes) {
        
        Map<GraphNode, Integer> graphToIndexMap = new HashMap<GraphNode, Integer>();
        
        for(int i = 0; i < nodes.length; i++) {
            graphToIndexMap.put(nodes[i], i);
        }
        
        int[][] toPrintGrid = new int[nodes.length][nodes.length];
        
        // print the adjacency list.
        for(int i = 0; i < nodes.length; i++) {
            for(Entry<GraphNode, Integer> adjacency: nodes[i].adjacencies.entrySet()) {
                toPrintGrid[i][graphToIndexMap.get(adjacency.getKey())] = adjacency.getValue();
            }
        }
        
        for(int i = 0; i < toPrintGrid.length; i++) {
            System.out.print(String.format(" %3d ", nodes[i].value));
            System.out.print("[");
            for(int j = 0; j < toPrintGrid[i].length; j++) {
                System.out.print(String.format(" %3d ", toPrintGrid[i][j]));
            }
            System.out.println("]");
        }
    }
}
