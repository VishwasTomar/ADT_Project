package graph;

import java.io.IOException;
import java.util.List;

public class GraphController {

    // Constructor
    public GraphController() {
        // You can initialize any necessary components here
    }

    // Method to load multiple graphs from a directory
    public void loadGraphsFromDirectory(String dirPath) {
        try {
            // Parse multiple graph files using FileUtils
            List<FileUtils.Graph> graphs = FileUtils.parseMultipleGraphFiles(dirPath);
            System.out.println("Loaded " + graphs.size() + " graph(s).");

            // Process each graph
            for (int i = 0; i < graphs.size(); i++) {
                FileUtils.Graph graph = graphs.get(i);
                System.out.println("Processing Graph " + (i + 1));

                // Find SCCs for each graph using SCCFinder
                SCCFinder.SCCResult sccResult = SCCFinder.findSCCs(graph);
//                System.out.println("SCCs for Graph " + (i + 1) + ":");
//                for (List<Integer> scc : sccResult.sccs) {
//                    System.out.println(scc);
//                }
                System.out.println("Largest SCC for Graph " + (i + 1) + ": " + sccResult.largestSCC);
            }

        } catch (IOException e) {
            System.err.println("Error reading graph files: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GraphController controller = new GraphController();
        String dirPath = "output"; // Replace with your directory containing graph files
        controller.loadGraphsFromDirectory(dirPath);
    }
}
