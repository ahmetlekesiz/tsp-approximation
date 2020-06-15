import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class graph {
	
	class subset { 
		int parent, rank; 
    }; 
	
    static int V, E;    // V-> no. of vertices & E->no.of edges 
	static edge edge[]; // collection of all edges 
	static edge perfectMatchResult[];
	static edge kruskalResult[];
	private int vertices; // No. of vertices 
	private ArrayList<Integer>[] adj; // adjacency list
	ArrayList<Integer> euclidianCircuit = new ArrayList<Integer>();
	
	graph(int v, int e) 
    { 
        V = v; 
        E = e; 
        
        edge = new edge[E]; 
        for (int i=0; i<e; ++i) {
            edge[i] = new edge(); 
        }
    }
	
	graph(int numOfVertices) 
    { 
        // initialise vertex count 
        this.vertices = numOfVertices; 
  
        // initialise adjacency list 
        initGraph(); 
    } 
	
	 private void initGraph() 
	    { 
	        adj = new ArrayList[vertices]; 
	        for (int i = 0; i < vertices; i++)  
	        { 
	            adj[i] = new ArrayList<>(); 
	        } 
	    } 

	 // add edge u-v 
	    void addEdge(Integer u, Integer v) 
	    { 
	        adj[u].add(v); 
	        adj[v].add(u); 
	    } 
	  
	    // This function removes edge u-v from graph. 
	    private void removeEdge(Integer u, Integer v) 
	    { 
	        adj[u].remove(v); 
	        adj[v].remove(u); 
	    } 

	    /* The main function that print Eulerian Trail.  
	       It first finds an odd degree vertex (if there  
	       is any) and then calls printEulerUtil() to 
	       print the path */
	    void printEulerTour() 
	    { 
	        // Find a vertex with odd degree 
	        Integer u = 0; 
	        for (int i = 0; i < vertices; i++) 
	        { 
	            if (adj[i].size() % 2 == 1) 
	            { 
	                u = i; 
	                break; 
	            } 
	        } 
	          
	        // Print tour starting from odd v 
	        printEulerUtil(u); 
	        System.out.println(); 
	    } 
	  
	    // Print Euler tour starting from vertex u 
	    private void printEulerUtil(Integer u) 
	    { 
	        // Recur for all the vertices adjacent to this vertex 
	        for (int i = 0; i < adj[u].size(); i++) 
	        { 
	            Integer v = adj[u].get(i); 
	            // If edge u-v is a valid next edge 
	            if (isValidNextEdge(u, v))  
	            { 
	                System.out.print(u + "-" + v + " "); 
	                euclidianCircuit.add(u);
	                euclidianCircuit.add(v);
	                // This edge is used so remove it now 
	                removeEdge(u, v);  
	                printEulerUtil(v); 
	            } 
	        } 
	    } 
	  
	    public ArrayList<Integer> clearRepeatedCities(ArrayList<Integer> cities) {
	    	// Find exist cities
	    	int[] citiesArray = new int[V];
	    	ArrayList<Integer> resultCircuit = new ArrayList<Integer>();
	    	for(int i=0; i<cities.size(); i++) {
	    		citiesArray[cities.get(i)]++;
	    		if(citiesArray[cities.get(i)] == 1) {
	    			resultCircuit.add(cities.get(i));
	    		}
	    	}
	    	resultCircuit.add(resultCircuit.get(0));
	    	return resultCircuit;
	    }
	    
	    // The function to check if edge u-v can be 
	    // considered as next edge in Euler Tout 
	    private boolean isValidNextEdge(Integer u, Integer v) 
	    { 
	        // The edge u-v is valid in one of the 
	        // following two cases: 
	  
	        // 1) If v is the only adjacent vertex of u  
	        // ie size of adjacent vertex list is 1 
	        if (adj[u].size() == 1) { 
	            return true; 
	        } 
	  
	        // 2) If there are multiple adjacents, then 
	        // u-v is not a bridge Do following steps  
	        // to check if u-v is a bridge 
	        // 2.a) count of vertices reachable from u 
	        boolean[] isVisited = new boolean[this.vertices]; 
	        int count1 = dfsCount(u, isVisited); 
	  
	        // 2.b) Remove edge (u, v) and after removing 
	        //  the edge, count vertices reachable from u 
	        removeEdge(u, v); 
	        isVisited = new boolean[this.vertices]; 
	        int count2 = dfsCount(u, isVisited); 
	  
	        // 2.c) Add the edge back to the graph 
	        addEdge(u, v); 
	        return (count1 > count2) ? false : true; 
	    } 
	  
	    // A DFS based function to count reachable 
	    // vertices from v 
	    private int dfsCount(Integer v, boolean[] isVisited) 
	    { 
	        // Mark the current node as visited 
	        isVisited[v] = true; 
	        int count = 1; 
	        // Recur for all vertices adjacent to this vertex 
	        for (int adj : adj[v]) 
	        { 
	            if (!isVisited[adj]) 
	            { 
	                count = count + dfsCount(adj, isVisited); 
	            } 
	        } 
	        return count; 
	    } 
	
	// A utility function to find set of an element i 
    // (uses path compression technique) 
    int find(subset subsets[], int i) 
    { 
        // find root and make root as parent of i (path compression) 
        if (subsets[i].parent != i) 
            subsets[i].parent = find(subsets, subsets[i].parent); 
  
        return subsets[i].parent; 
    }
    
    // A function that does union of two sets of x and y 
    // (uses union by rank) 
    void Union(subset subsets[], int x, int y) 
    { 
        int xroot = find(subsets, x); 
        int yroot = find(subsets, y); 
  
        // Attach smaller rank tree under root of high rank tree 
        // (Union by Rank) 
        if (subsets[xroot].rank < subsets[yroot].rank) 
            subsets[xroot].parent = yroot; 
        else if (subsets[xroot].rank > subsets[yroot].rank) 
            subsets[yroot].parent = xroot; 
  
        // If ranks are same, then make one as root and increment 
        // its rank by one 
        else
        { 
            subsets[yroot].parent = xroot; 
            subsets[xroot].rank++; 
        } 
    }
    
 // The main function to construct MST using Kruskal's algorithm 
    void KruskalMST() 
    { 
        edge result[] = new edge[V];  // This will store the resultant MST 
        int e = 0;  // An index variable, used for result[] 
        int i = 0;  // An index variable, used for sorted edges 
        for (i=0; i<V; ++i) 
            result[i] = new edge(); 
  
        // Step 1:  Sort all the edges in non-decreasing order of their 
        // weight.  If we are not allowed to change the given graph, we 
        // can create a copy of array of edges 
        Arrays.sort(edge); 
  
        // Allocate memory for creating V ssubsets 
        subset subsets[] = new subset[V]; 
        for(i=0; i<V; ++i) 
            subsets[i]=new subset(); 
  
        // Create V subsets with single elements 
        for (int v = 0; v < V; ++v) 
        { 
            subsets[v].parent = v; 
            subsets[v].rank = 0; 
        } 
  
        i = 0;  // Index used to pick next edge 
  
        // Number of edges to be taken is equal to V-1 
        while (e < V - 1) 
        { 
            // Step 2: Pick the smallest edge. And increment  
            // the index for next iteration 
            edge next_edge = new edge(); 
            next_edge = edge[i++]; 
  
            int x = find(subsets, next_edge.src); 
            int y = find(subsets, next_edge.dest); 
  
            // If including this edge does't cause cycle, 
            // include it in result and increment the index  
            // of result for next edge 
            if (x != y) 
            { 
                result[e++] = next_edge; 
                Union(subsets, x, y); 
            } 
            // Else discard the next_edge 
        } 
  
        // print the contents of result[] to display 
        // the built MST 
        System.out.println("Following are the edges in " +  
                                     "the constructed MST"); 
        
       //int[] neighbourCounterOnMST = new int[V];
        
        for (i = 0; i < e; ++i) {
        	System.out.println(result[i].src+" -- " +  
                    result[i].dest+" == " + result[i].weight);
        	
        	/*int src = result[i].src;
            int dest = result[i].dest;
            neighbourCounterOnMST[src]++;
            neighbourCounterOnMST[dest]++;*/
             
        }/*
        
        int countForOddDegree = 0;
        
        for(int i1 = 0; i1<V; i1++) {
        	if(neighbourCounterOnMST[i1] % 2 == 1) countForOddDegree++;
        }
        
        System.out.println(countForOddDegree);*/
        kruskalResult = result;
    }
    
    void findAndAddPerfectMatches(edge[] mst,List<int[]> citylist){
    	int[] neighbourCounterOnMST = new int[V];
    	
    	for(int i = 0 ; i < mst.length ; i++) {
    		int src = mst[i].src;
            int dest = mst[i].dest;
            neighbourCounterOnMST[src]++;
            neighbourCounterOnMST[dest]++;
    	}
    	
    	ArrayList<edge> newEdgesForOddVertexs = new ArrayList<edge>();
    	List<int[]> oddDegreVertex = new ArrayList<int[]>();
    	
    	for(int i = 0 ; i < neighbourCounterOnMST.length ; i++) {
    		if(neighbourCounterOnMST[i] % 2 == 1) {
    			oddDegreVertex.add(citylist.get(i));
    		}
    	}
    	findMatchesWithNearestNeighbour(oddDegreVertex,newEdgesForOddVertexs);
    	
    	//merging new edges into mst so all nodes have even number edge now
    	edge[] newEdges = newEdgesForOddVertexs.toArray(new edge[0]);
    	int fal = mst.length-1;        //determines length of firstArray  
    	int sal = newEdges.length;   //determines length of secondArray  
    	edge[] result = new edge[fal + sal];  //resultant array of size first array and second array  
    	System.arraycopy(mst, 0, result, 0, fal);  
    	System.arraycopy(newEdges, 0, result, fal, sal);  
    	System.out.println("Following are the edges within new edgest into " +  
                "the constructed MST"); 

    	for (int i = 0; i < result.length; ++i)
    			System.out.println(result[i].src+" -- " +  
    			result[i].dest+" == " + result[i].weight);
    	//checking is there any odd edge vertex
    	int[] neighbourCounterOnMST2 = new int[V];
        
        for (int i = 0; i < fal+sal; ++i) {
        	System.out.println(result[i].src+" -- " +  
                    result[i].dest+" == " + result[i].weight);
        	
        	int src = result[i].src;
            int dest = result[i].dest;
            neighbourCounterOnMST2[src]++;
            neighbourCounterOnMST2[dest]++;
             
        }
        int countForOddDegree = 0;
        
        for(int i1 = 0; i1<V; i1++) {
        	if(neighbourCounterOnMST2[i1] % 2 == 1) countForOddDegree++;
        }
        this.perfectMatchResult = result;
        System.out.println(countForOddDegree);//prints zero so its ok
    }

	void findMatchesWithNearestNeighbour(List<int[]> oddDegreVertex, ArrayList<edge> newEdgesForOddVertexs) {
		// TODO Auto-generated method stub
		int distance=0,min=Integer.MAX_VALUE,nextcityIndex=0,indexForRemove=0;
		edge tempEdge;
		
		int[] temp,temp2;
		for(int i=0 ;  i < oddDegreVertex.size() ;i=nextcityIndex) {
			
			temp=oddDegreVertex.get(i);
			
			oddDegreVertex.remove(i);
			
			for (int k = 0; k < oddDegreVertex.size(); k++) {
				temp2 = oddDegreVertex.get(k);
				
				distance = (int) Math.round(Math.sqrt(Math.pow(temp[1] - temp2[1], 2) + Math.pow(temp[2] - temp2[2], 2)));
				
				if(distance<min ) {
					min=distance;
					nextcityIndex=0;
					indexForRemove=k;
				}
			}
			
			temp2=oddDegreVertex.get(indexForRemove);
			tempEdge = new edge();
			tempEdge.src = temp[0];
			tempEdge.dest = temp2[0];
			tempEdge.weight = min;
			newEdgesForOddVertexs.add(tempEdge);
			
			min=Integer.MAX_VALUE;
			oddDegreVertex.remove(indexForRemove);
			
			if(oddDegreVertex.size()==2){
				tempEdge = new edge();
				tempEdge.src = oddDegreVertex.get(0)[0];
				tempEdge.dest = oddDegreVertex.get(1)[0];
				tempEdge.weight = (int) Math.round(Math.sqrt(Math.pow(oddDegreVertex.get(0)[1] - oddDegreVertex.get(1)[1], 2) + Math.pow(oddDegreVertex.get(0)[2] - oddDegreVertex.get(1)[2], 2)));
				newEdgesForOddVertexs.add(tempEdge);
				break;
			}

		}
		
	}
}
