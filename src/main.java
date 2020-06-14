import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<int[]> cityList = new ArrayList<int[]>();
	
		int V = 0;  // Number of vertices in graph 
	    int E = 0;  // Number of edges in graph 
		
		// Read input file line by line by using scanner
		try {
			Scanner scanner = new Scanner(new File("./inputs/example-input-3.txt"));
			while (scanner.hasNextLine()) {
				String[] arr = scanner.nextLine().split(" ");
				// Adding to linked list
				cityList.add(new int[] {Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2])});
				// Adding to graph
				V++; // Increment the vertices 
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		E = V*V;
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
	    
	 
	    graph.edge[1].src = 0; 
	    graph.edge[1].dest = 2; 
	    graph.edge[1].weight = 6; 
	  
	    System.out.println();
	}

}
