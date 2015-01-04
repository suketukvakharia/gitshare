package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import math.GenerateRandom;

import org.junit.Test;

public class DjiktraDistance {
    
    @Test
    public void testSuccess() {
        
        GraphNode[] nodes = generateRandomBidirectionalGraph(5);
        
        GraphPrinter.printAsAdjacency(nodes);
        
        Map<GraphNode, Integer> runDjikstra = runDjikstra(nodes[0]);
        
        for(Entry<GraphNode, Integer> entry: runDjikstra.entrySet()) {
            System.out.println("Distance to:" + entry.getKey().value + " is:" + entry.getValue());
        }
    }
    
    public static GraphNode[] generateRandomBidirectionalGraph(int numberOfNodes) {
        
        GraphNode[] nodes = new GraphNode[numberOfNodes];
        double adjacencyPercent = .7;
        int maxDistance = numberOfNodes * 2;
        Random rand = new Random();
        
        for(int i = numberOfNodes-1; i >= 0; i--) {
            
            nodes[i] = new GraphNode(i);
            
            for(int j = i + 1; j < numberOfNodes; j++) {
                
                // random flip to determine if the adjacency should be created.
                if(rand.nextDouble() < adjacencyPercent) {
                    
                    // generate Random number from 0 to numberOfNodes to put in as adjacency level.
                    int cost = GenerateRandom.next(31)%maxDistance;
                    
                    nodes[i].addAdjacency(nodes[j], cost);
                    nodes[j].addAdjacency(nodes[i], cost);
                }
            }
        }
        
        return nodes; 
    }
    
    public static Map<GraphNode, Integer> runDjikstra(GraphNode startingNode) {
        
        Map<GraphNode, Integer> toReturn = new HashMap<GraphNode, Integer>();
        
        Set<GraphNode> visitedNodes = new HashSet<GraphNode>();
        
        Map<GraphNode, Integer> currentNodes = new HashMap<GraphNode, Integer>();
        currentNodes.put(startingNode, 0);
        
        while(!currentNodes.isEmpty()) {
            
            // get min from currentNodes
            GraphNode minCostNode = null;
            int minCost = Integer.MAX_VALUE;
            for(Entry<GraphNode, Integer> currentNodeEntry: currentNodes.entrySet()) {
                if(currentNodeEntry.getValue() < minCost) {
                    minCost = currentNodeEntry.getValue();
                    minCostNode = currentNodeEntry.getKey();
                }
            }
            
            currentNodes.remove(minCostNode);
            visitedNodes.add(minCostNode);
            
            // iterate over all adjacencies of min cost node and update nodes as needed.
            for(Entry<GraphNode, Integer> adjacency: minCostNode.adjacencies.entrySet()) {
                
                // update toReturn value if its better
                if(!toReturn.containsKey(adjacency.getKey()) || toReturn.get(adjacency.getKey()) > adjacency.getValue()) {
                    toReturn.put(adjacency.getKey(), adjacency.getValue());
                }
                
                // update currentNodes value if not visited and its better.
                if( !visitedNodes.contains(adjacency.getKey()) && 
                        (!currentNodes.containsKey(adjacency.getKey()) || currentNodes.get(adjacency.getKey()) > adjacency.getValue() )) {
                    currentNodes.put(adjacency.getKey(), adjacency.getValue());
                }
            }
        }
        
        return toReturn;
    }
}
