package graph;

import java.util.*;

public class SCCFinder {

    public static class SCCResult {
        public List<List<Integer>> sccs = new ArrayList<>();
        public List<Integer> largestSCC = new ArrayList<>();
        public GraphGenerator.Graph largestSCCGraph = new GraphGenerator.Graph();
    }

    // Method to find strongly connected components (SCCs) in the graph
    public static SCCResult findSCCs(FileUtils.Graph graph) {
        int n = graph.nodes.size();
        Map<Integer, List<GraphGenerator.Edge>> adj = FileUtils.getAdjacencyList(graph);
        Map<Integer, List<GraphGenerator.Edge>> reverseAdj = new HashMap<>();

        // Reverse the graph
        for (GraphGenerator.Edge edge : graph.edges) {
            reverseAdj.computeIfAbsent(edge.to, k -> new ArrayList<>()).add(edge);
        }

        boolean[] visited = new boolean[n];
        Stack<Integer> finishOrder = new Stack<>();

        // Step 1: Perform DFS to get finishing order
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, adj, visited, finishOrder);
            }
        }

        // Step 2: Reset visited array for the second DFS
        visited = new boolean[n];
        SCCResult result = new SCCResult();

        // Step 3: Process nodes in reverse finishing order
        while (!finishOrder.isEmpty()) {
            int node = finishOrder.pop();
            if (!visited[node]) {
                List<Integer> scc = new ArrayList<>();
                reverseDFS(node, reverseAdj, visited, scc);
                result.sccs.add(scc);
                if (scc.size() > result.largestSCC.size()) {
                    result.largestSCC = scc;
                }
            }
        }

        // Build the graph for the largest SCC (LCC)
        buildLCCGraph(result.largestSCC, graph, result);

        // Call GraphMetrics to calculate and print the metrics for the LCC
        Set<Integer> lccSet = new HashSet<>(result.largestSCC);
        GraphMetrics.printMetrics("Largest SCC", lccSet, adj);

        return result;
    }

    // DFS for topological sort
    private static void dfs(int node, Map<Integer, List<GraphGenerator.Edge>> adj, boolean[] visited, Stack<Integer> finishOrder) {
        visited[node] = true;
        for (GraphGenerator.Edge edge : adj.getOrDefault(node, Collections.emptyList())) {
            if (!visited[edge.to]) {
                dfs(edge.to, adj, visited, finishOrder);
            }
        }
        finishOrder.push(node);
    }

    // Reverse DFS to find SCC
    private static void reverseDFS(int node, Map<Integer, List<GraphGenerator.Edge>> reverseAdj, boolean[] visited, List<Integer> scc) {
        visited[node] = true;
        scc.add(node);
        for (GraphGenerator.Edge edge : reverseAdj.getOrDefault(node, Collections.emptyList())) {
            if (!visited[edge.from]) {
                reverseDFS(edge.from, reverseAdj, visited, scc);
            }
        }
    }

    // Build the graph for the largest SCC (LCC)
    private static void buildLCCGraph(List<Integer> largestSCC, FileUtils.Graph graph, SCCResult result) {
        Set<Integer> sccSet = new HashSet<>(largestSCC);
        for (GraphGenerator.Edge edge : graph.edges) {
            if (sccSet.contains(edge.from) && sccSet.contains(edge.to)) {
                result.largestSCCGraph.edges.add(edge);
            }
        }

        // Add the nodes in the largest SCC to the LCC graph
        for (Integer nodeId : largestSCC) {
            result.largestSCCGraph.nodes.add(graph.nodes.get(nodeId));
        }
    }
}
