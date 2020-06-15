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
			Scanner scanner = new Scanner(new File("./inputs/example-input-1.txt"));
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
	    /*
	     * int distance=0,min=Integer.MAX_VALUE,nextcityIndex=0,totalDistance=0,pathIndex=0;
		int[] path= new int[V];
		int[] starting_city=cityList.get(0);
		int[] temp;
		for(int i=nextcityIndex; i<V;i=nextcityIndex) {
			temp=cityList.get(i);
			cityList.remove(i);
			for (int k = 0; k < cityList.size(); k++) {
				distance = (int) Math.round(Math.sqrt(Math.pow(temp[1] - cityList.get(k)[1], 2) + Math.pow(temp[2] - cityList.get(k)[2], 2)));
				if(distance<min ) {
					min=distance;
					nextcityIndex=k;
				}
			}
			totalDistance += min;
			min=Integer.MAX_VALUE;
			path[pathIndex]=temp[0];
			pathIndex++;
			if(cityList.size()==1){
				path[pathIndex]=cityList.get(0)[0];
				totalDistance +=(int) Math.round(Math.sqrt(Math.pow(cityList.get(0)[1] - starting_city[1], 2) + Math.pow(cityList.get(0)[2] - starting_city[2], 2)));
				break;
			}

		}
	     */
	    System.out.println(edgeCounter);
	  
        graph.KruskalMST(); 
	    System.out.println();
	    
	    graph g1 = new graph(V); 
	    edge mst[] = graph.mst;
	    for(int i=0; i<mst.length; i++) {
	    	g1.addEdge(mst[i].src, mst[i].dest);
	    }
        g1.printEulerTour(); 
	}

}
