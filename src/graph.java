
public class graph {
	
	class subset { 
		int parent, rank; 
    }; 
	
    int V, E;    // V-> no. of vertices & E->no.of edges 
	edge edge[]; // collection of all edges 
	 
	graph(int v, int e) 
    { 
        V = v; 
        E = e; 
        edge = new edge[E]; 
        for (int i=0; i<e; ++i) 
            edge[i] = new edge(); 
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
}
