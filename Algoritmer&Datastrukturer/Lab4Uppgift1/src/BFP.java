/**
 * README:
 * Name:	 Olle Gunnemyr
 * Date:	 2020-10-05
 * Program:	 BFP.java
 * 
 * BFP.java runs a breadth first search on an undirected graph.
 * The program is copied and modified from BreadthFirstPaths.java in https://algs4.cs.princeton.edu.
 */

public class BFP {
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

   //computes the shortest path between the source vertex
    //and every other vertex in graph.
    public BFP(Graph G, int s) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
       
        bfs(G, s);

       
    }

    // breadth-first search from a single source
    private void bfs(Graph G, int s) {
        Queue<Integer> q = new Queue<Integer>();
        
        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

  

   //returns true if there is a path to vertex v, else false
    public boolean hasPathTo(int v) {
        return marked[v];
    }

   //returns the number of edges in a shortest path between the source vertex and v
    public int distTo(int v) {
        return distTo[v];
    }

    //returns a shortest path between the source vertex and v, or null if no such path
    public Stack<Integer> pathTo(int v) {
      
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }


 


   
}