/**
 * README:
 * Name:	 Olle Gunnemyr
 * Date:	 2020-10-05
 * Program:	 DFP.java
 * 
 * DFP.java runs a depth first search on an undirected graph.
 * The program is copied and modified from DepthFirstPaths.java in https://algs4.cs.princeton.edu.
 */


public class DFP {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int[] edgeTo;        // edgeTo[v] = last edge on s-v path
    private final int s;         // source vertex

   //computes a path between the source vertex and every other vertex in graph
    public DFP(Graph G, int s) {
        this.s = s;
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    // depth first search from v
    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

 
    //returns true if there is a path to vertex v, else false
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    
    //returns a path between the source vertex and v, or null if no such path
    public Stack<Integer> pathTo(int v) {
      
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return  path;
    }
}
