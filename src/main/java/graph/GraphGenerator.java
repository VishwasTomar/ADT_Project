package graph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphGenerator {

    public static class Node {
        public double x, y; // Cartesian coordinates of the node
        public int id; // Unique ID for the node

        public Node(int id, double x, double y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    public static class Edge {
        public int from, to; // Directed edge from 'from' to 'to'
        public int capacity; // Capacity of the edge
        public int cost; // Unit cost of the edge

        public Edge(int from, int to, int capacity, int cost) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return from + " " + to + " " + capacity + " " + cost;
        }
    }

    public static class Graph {
        public List<Node> nodes = new ArrayList<>();
        public List<Edge> edges = new ArrayList<>();
    }

    public static Graph generateGraph(int n, double r, int upperCap, int upperCost) {
        Random random = new Random();
        Graph graph = new Graph();

        // Step 1: Generate nodes with random (x, y) coordinates
        for (int i = 0; i < n; i++) {
            double x = random.nextDouble(); // Random x-coordinate in [0, 1]
            double y = random.nextDouble(); // Random y-coordinate in [0, 1]
            graph.nodes.add(new Node(i, x, y));
        }

        // Step 2: Generate edges based on the distance threshold 'r'
        for (Node u : graph.nodes) {
            for (Node v : graph.nodes) {
                if (u.id != v.id) { // Avoid self-loops
                    double distance = Math.sqrt(Math.pow(u.x - v.x, 2) + Math.pow(u.y - v.y, 2));
                    if (distance <= r) {
                        double randProb = random.nextDouble(); // Random probability
                        if (randProb < 0.3 && !edgeExists(graph, u.id, v.id) && !edgeExists(graph, v.id, u.id)) {
                            // Add edge (u -> v) with a probability of 30%
                            graph.edges.add(new Edge(u.id, v.id, random.nextInt(upperCap) + 1,
                                    random.nextInt(upperCost) + 1));
                        } else if (randProb < 0.6 && !edgeExists(graph, v.id, u.id) && !edgeExists(graph, u.id, v.id)) {
                            // Add edge (v -> u) with a probability of 30%
                            graph.edges.add(new Edge(v.id, u.id, random.nextInt(upperCap) + 1,
                                    random.nextInt(upperCost) + 1));
                        }
                    }
                }
            }
        }

        return graph;
    }

    private static boolean edgeExists(Graph graph, int from, int to) {
        // Check if an edge already exists in the graph
        return graph.edges.stream().anyMatch(edge -> edge.from == from && edge.to == to);
    }

    public static void saveGraphToFile(Graph graph, String fileName) {
        try {
            File file = new File(fileName);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileWriter writer = new FileWriter(fileName)) {
                for (Edge edge : graph.edges) {
                    writer.write(edge.toString() + "\n");
                }
                System.out.println("Graph saved to file: " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Error saving graph to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Example: Generate a graph and save it to a file
        int n = 100;          // Number of nodes
        double r = 0.2;       // Maximum distance for edges
        int upperCap = 8;     // Maximum edge capacity
        int upperCost = 5;    // Maximum edge cost

        Graph graph1 = generateGraph(n, r, upperCap, upperCost);
        saveGraphToFile(graph1, "output/graph1.txt");

        n = 200;          // Number of nodes
        r = 0.2;       // Maximum distance for edges
        upperCap = 8;     // Maximum edge capacity
        upperCost = 5;    // Maximum edge cost

        Graph graph2 = generateGraph(n, r, upperCap, upperCost);
        saveGraphToFile(graph2, "output/graph2.txt");


        n = 100;          // Number of nodes
        r = 0.3;       // Maximum distance for edges
        upperCap = 8;     // Maximum edge capacity
        upperCost = 5;    // Maximum edge cost

        Graph graph3 = generateGraph(n, r, upperCap, upperCost);
        saveGraphToFile(graph3, "output/graph3.txt");

        n = 200;          // Number of nodes
        r = 0.3;       // Maximum distance for edges
        upperCap = 8;     // Maximum edge capacity
        upperCost = 5;    // Maximum edge cost

        Graph graph4 = generateGraph(n, r, upperCap, upperCost);
        saveGraphToFile(graph4, "output/graph4.txt");


        n = 100;          // Number of nodes
        r = 0.2;       // Maximum distance for edges
        upperCap = 64;     // Maximum edge capacity
        upperCost = 20;    // Maximum edge cost

        Graph graph5 = generateGraph(n, r, upperCap, upperCost);
        saveGraphToFile(graph5, "output/graph5.txt");

        n = 200;          // Number of nodes
        r = 0.2;       // Maximum distance for edges
        upperCap = 64;     // Maximum edge capacity
        upperCost = 20;    // Maximum edge cost

        Graph graph6 = generateGraph(n, r, upperCap, upperCost);
        saveGraphToFile(graph6, "output/graph6.txt");


        n = 100;          // Number of nodes
        r = 0.3;       // Maximum distance for edges
        upperCap = 64;     // Maximum edge capacity
        upperCost = 20;    // Maximum edge cost

        Graph graph7 = generateGraph(n, r, upperCap, upperCost);
        saveGraphToFile(graph7, "output/graph7.txt");

        n = 200;          // Number of nodes
        r = 0.3;       // Maximum distance for edges
        upperCap = 64;     // Maximum edge capacity
        upperCost = 20;    // Maximum edge cost

        Graph graph8 = generateGraph(n, r, upperCap, upperCost);
        saveGraphToFile(graph8, "output/graph8.txt");
    }
}
