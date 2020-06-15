import java.util.ArrayList;
import java.util.Arrays;

public class graph {
	
	class subset { 
		int parent, rank; 
    }; 
	
    int V, E;    // V-> no. of vertices & E->no.of edges 
	edge edge[]; // collection of all edges 
	edge mst[];
	 private int vertices; // No. of vertices 
	 private ArrayList<Integer>[] adj; // adjacency list 
	
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
	          
	        // Print tour starting from oddv 
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
	                  
	                // This edge is used so remove it now 
	                removeEdge(u, v);  
	                printEulerUtil(v); 
	            } 
	        } 
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
        
        int[] neighbourCounterOnMST = new int[V];
        
        for (i = 0; i < e; ++i) {
        	System.out.println(result[i].src+" -- " +  
                    result[i].dest+" == " + result[i].weight);
        	
        	int src = result[i].src;
            int dest = result[i].dest;
            neighbourCounterOnMST[src]++;
            neighbourCounterOnMST[dest]++;
            // 
        }
        
        int countForOddDegree = 0;
        
        for(int i1 = 0; i1<V; i1++) {
        	if(neighbourCounterOnMST[i1] % 2 == 1) countForOddDegree++;
        }
        
        System.out.println(countForOddDegree);
        mst = result;
    } 
}
