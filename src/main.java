import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

	public static String cleanInput(String input) {
		int counter = 0;
		while(input.charAt(counter) == ' ') {
			counter++;
		}
		return input.substring(counter);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<int[]> cityList = new ArrayList<int[]>();
	
		int V = 0;  // Number of vertices in graph 
	    int E = 0;  // Number of edges in graph 
		
	    
		// Read input file line by line by using scanner
		try {
			Scanner scanner = new Scanner(new File("./inputs/example-input-2.txt"));
			while (scanner.hasNextLine()) {
				// Clean white spaces
				String cleanResult = cleanInput(scanner.nextLine());
				String[] arr = cleanResult.split("\\s+");
				// Adding to linked list
				cityList.add(new int[] {Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2])});
				V++; // Increment the vertices 
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		E = (V*V)-V;
	    graph graph = new graph(V, E);  

	    int edgeCounter = 0;
	    int distance = 0;
	    for(int i=0; i<V; i++) {
		    for(int k=0; k<V; k++) {
		    	// Create and edge if the vertices are different
		    	if(i!=k) {
			    	// Distance between i and k
			    	distance = (int)Math.sqrt(Math.pow(cityList.get(i)[1]-cityList.get(k)[1], 2)+Math.pow(cityList.get(i)[2]-cityList.get(k)[2], 2));
				    // Add edge to graph
			    	graph.edge[edgeCounter].src = i; 
					graph.edge[edgeCounter].dest = k; 
					graph.edge[edgeCounter].weight = distance; 
					// Increment edgeCounter for next edge
					edgeCounter++;
		    	}
		    }
		}
	    
	    System.out.println(edgeCounter);
	  
        graph.KruskalMST(); 
	    System.out.println();
	}

}
