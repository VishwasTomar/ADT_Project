package graph;

import java.util.*;

public class GraphMetrics {

    // Method to calculate the number of nodes in the largest connected component (LCC)
    public static int calculateVLCC(Set<Integer> lcc) {
        return lcc.size();
    }

    // Method to calculate the maximum out-degree in the LCC
    public static int calculateMaxOutDegree(Set<Integer> lcc, Map<Integer, List<GraphGenerator.Edge>> adjList) {
        int maxOutDegree = 0;

        for (Integer node : lcc) {
            int outDegree = adjList.getOrDefault(node, new ArrayList<>()).size();
            maxOutDegree = Math.max(maxOutDegree, outDegree);
        }

        return maxOutDegree;
    }

    // Method to calculate the maximum in-degree in the LCC
    public static int calculateMaxInDegree(Set<Integer> lcc, Map<Integer, List<GraphGenerator.Edge>> adjList) {
        int maxInDegree = 0;

        // Calculate in-degree for each node in LCC
        for (Integer node : lcc) {
            int inDegree = 0;
            for (Map.Entry<Integer, List<GraphGenerator.Edge>> entry : adjList.entrySet()) {
                for (GraphGenerator.Edge edge : entry.getValue()) {
                    if (edge.to == node) {
                        inDegree++;
                    }
                }
            }
            maxInDegree = Math.max(maxInDegree, inDegree);
        }

        return maxInDegree;
    }

    // Method to calculate the average degree in the LCC
    public static double calculateAverageDegree(Set<Integer> lcc, Map<Integer, List<GraphGenerator.Edge>> adjList) {
        int totalEdges = 0;
        for (Integer node : lcc) {
            totalEdges += adjList.getOrDefault(node, new ArrayList<>()).size();
        }

        // Count the in-degree as well
        for (Integer node : lcc) {
            for (Map.Entry<Integer, List<GraphGenerator.Edge>> entry : adjList.entrySet()) {
                for (GraphGenerator.Edge edge : entry.getValue()) {
                    if (edge.to == node) {
                        totalEdges++;
                    }
                }
            }
        }

        return (double) totalEdges / lcc.size();
    }

    // Method to print the results in a structured table format
    public static void printMetrics(String graphName, Set<Integer> lcc, Map<Integer, List<GraphGenerator.Edge>> adjList) {
        int vlcc = calculateVLCC(lcc);
        int maxOutDegree = calculateMaxOutDegree(lcc, adjList);
        int maxInDegree = calculateMaxInDegree(lcc, adjList);
        double avgDegree = calculateAverageDegree(lcc, adjList);

        System.out.println("Metrics for " + graphName + ":");
        System.out.println("n (Total nodes): " + adjList.size());
        System.out.println("|VLCC| (LCC size): " + vlcc);
        System.out.println("∆out(LCC) (Max out-degree): " + maxOutDegree);
        System.out.println("∆in(LCC) (Max in-degree): " + maxInDegree);
        System.out.println("k(LCC) (Avg degree): " + avgDegree);
    }
}
