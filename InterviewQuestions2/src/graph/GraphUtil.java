package graph;

import java.util.Random;

import math.GenerateRandom;


public class GraphUtil {

	
	public static GraphNode[] generateRandomBidirectionalGraph(int numberOfNodes) {
		return generateRandomBidirectionalGraph(numberOfNodes, .7);
	}
	
	public static GraphNode[] generateRandomBidirectionalGraph(int numberOfNodes, double adjacencyPercent) {
	    GraphNode[] nodes = new GraphNode[numberOfNodes];
	    int maxDistance = numberOfNodes * 3;
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

}
