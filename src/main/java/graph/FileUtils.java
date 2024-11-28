    package graph;

    import java.io.*;
    import java.util.*;

    public class FileUtils {

        public static class Graph {
            public List<GraphGenerator.Node> nodes = new ArrayList<>();
            public List<GraphGenerator.Edge> edges = new ArrayList<>();
        }

        // Method to parse a graph file in the 'EDGES' format
        public static Graph parseGraphFile(String filePath) throws IOException {
            Graph graph = new Graph();
            Map<Integer, GraphGenerator.Node> nodeMap = new HashMap<>();

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 4) {
                    int from = Integer.parseInt(parts[0]);
                    int to = Integer.parseInt(parts[1]);
                    int capacity = Integer.parseInt(parts[2]);
                    int cost = Integer.parseInt(parts[3]);

                    // Create nodes if they don't already exist
                    if (!nodeMap.containsKey(from)) {
                        nodeMap.put(from, new GraphGenerator.Node(from, Math.random(), Math.random()));
                    }
                    if (!nodeMap.containsKey(to)) {
                        nodeMap.put(to, new GraphGenerator.Node(to, Math.random(), Math.random()));
                    }

                    // Add the edge to the graph
                    graph.edges.add(new GraphGenerator.Edge(from, to, capacity, cost));
                }
            }

            // Convert node map to node list
            graph.nodes.addAll(nodeMap.values());
            reader.close();
            return graph;
        }

        // Method to convert graph to adjacency list format
        public static Map<Integer, List<GraphGenerator.Edge>> getAdjacencyList(Graph graph) {
            Map<Integer, List<GraphGenerator.Edge>> adjList = new HashMap<>();

            for (GraphGenerator.Edge edge : graph.edges) {
                adjList.computeIfAbsent(edge.from, k -> new ArrayList<>()).add(edge);
            }

            return adjList;
        }

        // Method to convert graph to adjacency matrix format
        public static int[][] getAdjacencyMatrix(Graph graph) {
            int n = graph.nodes.size();
            int[][] adjMatrix = new int[n][n];

            for (GraphGenerator.Edge edge : graph.edges) {
                adjMatrix[edge.from][edge.to] = edge.capacity; // We can store capacity or cost
            }

            return adjMatrix;
        }

        // Method to parse and return multiple graphs from files in a directory
        public static List<Graph> parseMultipleGraphFiles(String dirPath) throws IOException {
            File dir = new File(dirPath);
            File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));  // Filtering only .txt files
            List<Graph> graphs = new ArrayList<>();

            if (files != null) {
                for (File file : files) {
                    graphs.add(parseGraphFile(file.getAbsolutePath()));
                }
            }

            return graphs;
        }

        public static void main(String[] args) {
            try {
                // Example usage: Parse multiple graph files from a directory
                String dirPath = "output"; // Replace with your directory containing graph files
                List<Graph> graphs = parseMultipleGraphFiles(dirPath);

                for (int i = 0; i < graphs.size(); i++) {
                    Graph graph = graphs.get(i);
                    System.out.println("Graph " + (i + 1));

                    // Convert to adjacency list
                    Map<Integer, List<GraphGenerator.Edge>> adjList = getAdjacencyList(graph);
                    System.out.println("Adjacency List for Graph " + (i + 1) + ":");
                    for (Map.Entry<Integer, List<GraphGenerator.Edge>> entry : adjList.entrySet()) {
                        System.out.print(entry.getKey() + " -> ");
                        for (GraphGenerator.Edge edge : entry.getValue()) {
                            System.out.print("(" + edge.to + ", " + edge.capacity + ") ");
                        }
                        System.out.println();
                    }

    //                // Convert to adjacency matrix
    //                int[][] adjMatrix = getAdjacencyMatrix(graph);
    //                System.out.println("Adjacency Matrix for Graph " + (i + 1) + ":");
    //                for (int j = 0; j < adjMatrix.length; j++) {
    //                    for (int k = 0; k < adjMatrix[j].length; k++) {
    //                        System.out.print(adjMatrix[j][k] + " ");
    //                    }
    //                    System.out.println();
    //                }
                }
            } catch (IOException e) {
                System.err.println("Error reading graph files: " + e.getMessage());
            }
        }
    }
