package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

public class TravellingSalesManProblem {
	
	
	
	@Test
	public void testNaiveSuccess() {
		GraphNode[] nodes = GraphUtil.generateRandomBidirectionalGraph(5, 1.0);
		GraphNode root = nodes[0];
		GraphPrinter.printAsAdjacency(nodes);
		
		Map<GraphNode, Integer> tspNaive = getTSPNaive(root, new HashSet<GraphNode>());
		
		for(Entry<GraphNode, Integer> entry: tspNaive.entrySet()) {
			System.out.println(entry.getKey().value + " tsp cost:" + entry.getValue());
		}
	}
	
	public Map<GraphNode, Integer> getTSPNaive(GraphNode root, Set<GraphNode> visited) {
		if(root == null) return new HashMap<GraphNode, Integer>();
		
		int minCost = Integer.MAX_VALUE;
		Map<GraphNode, Integer> toReturn = new LinkedHashMap<GraphNode, Integer>();
		for (Entry<GraphNode, Integer> edge : root.adjacencies.entrySet()) {
			// only consider if not visited.
			if(!visited.contains(edge.getKey())) {
				int cost = edge.getValue();
				visited.add(edge.getKey());
				Map<GraphNode, Integer> currentTSP = getTSPNaive(edge.getKey(), visited);
				visited.remove(edge.getKey());
				for(Integer currentTSPEdgeCost: currentTSP.values()) {
					cost += currentTSPEdgeCost;
				}
				
				if(cost < minCost) {
					minCost = cost;
					toReturn.clear();
					toReturn.put(edge.getKey(), edge.getValue());
					for(Entry<GraphNode, Integer> entry: currentTSP.entrySet()) {
						toReturn.put(entry.getKey(), entry.getValue() + edge.getValue());
					}
				}
			}
		}
		
		return toReturn;
	}
}
