package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class proj3 {
	
	private static AdjacencyList adjacencyList = new AdjacencyList();
	private static ArrayList<Edge> minSpanList = new ArrayList<Edge>();
	
	public static void main(String[] args) {
		
        Scanner console = new Scanner(System.in);
        Scanner input = getInputScanner(console);
        PrintStream output = getOutputPrintStream(console);        
        ArrayListMinHeap<Edge> heap = new ArrayListMinHeap<Edge>();           
		String line;
		
		while (input.hasNextLine()) {
			line = input.nextLine();
			if (!line.equals("-1")) {
				Scanner lineScanner = new Scanner(line);
				int origin = lineScanner.nextInt();
				int destination = lineScanner.nextInt();
				double weight = lineScanner.nextDouble();
				adjacencyList.insertEdge(origin, destination, weight);
				Edge edge = new Edge(origin, destination, weight);
				heap.insert(edge);				
			}
		}
		
		minSpanList = kruskalMST(heap);
		
		output.println(heap.toString());
		output.println(toStringMST());
		output.print(adjacencyList.toString());
		
	}
		
	
	public static ArrayList<Edge> kruskalMST(ArrayListMinHeap<Edge> heap) {
		ArrayList<Edge> minSpanList = new ArrayList<Edge>();
		UpTree upTree = new UpTree();
		for (Integer key : adjacencyList.getVertices()) {
			upTree.makeSet(key);
		}
		int components = adjacencyList.numVertices();
		while (components > 1) {
			Edge edge = (Edge) heap.removeMin();
			int u = upTree.find(edge.getOrigin());
			int v = upTree.find(edge.getDestination());
			if (u != v) {
				upTree.union(u, v);
				minSpanList.add(edge);
				components--;
			}
		}		
		return minSpanList;
	}
	
	public static String toStringMST() {
    	String list = "";
    	if (minSpanList.size() > 0) {
    		list = minSpanList.get(0).toString();
    	}
    	if (minSpanList.size() > 1) {
    		for (int k = 1; k < minSpanList.size(); k++) {
    			list += "\n" + minSpanList.get(k).toString();
    		}
    	}
    	return list;
	}
	
	
	
	
    /**
     * Returns a Scanner for input from a file.
     *
     * @param console Scanner for console
     * @return Scanner for input from a file
     */
    public static Scanner getInputScanner(Scanner console) {
        Scanner input = null;
        while (input == null) {
            System.out.print("input file name? ");
            String name = console.nextLine();
            try {
                input = new Scanner(new File(name));
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            }
        }
        return input;
    }

    /**
     * Returns a PrintStream for output to a file. NOTE: If file exists, this
     * code will overwrite the existing file. It is likely that you want to add
     * additional tests.
     *
     * @param console Scanner for console.
     * @return PrintStream for output to a file
     */
    public static PrintStream getOutputPrintStream(Scanner console) {
        PrintStream outputFile = null;
        while (outputFile == null) {
            System.out.print("output file name? ");
            String name = console.nextLine();
            try {
                outputFile = new PrintStream(new File(name));
            } catch (FileNotFoundException e) {
                System.out.println("File unable to be written. Please try again.");
            }
        }
        return outputFile;
    }
    
    

}
