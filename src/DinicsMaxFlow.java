/* *****************************************************************************
 *  Name:    Luchitha Pasqual
 *  UowID:   w1790040
 *  iitID:   20191236
 *
 *  Description:  Dinic's Algorithm to find the maximum flow of a flow network
 *                with a parser to input text files.
 **************************************************************************** */

import java.util.*;

public class DinicsMaxFlow {


    static class Edge {
        int t, rev, cap, f;
        // Residual Graph constructor
        public Edge(int t, int rev, int cap) {
            this.t = t;
            this.rev = rev;
            this.cap = cap;
        }
    }

    //creating graph
    public static List<Edge>[] createGraph(int nodes) {
        List<Edge>[] graph = new List[nodes];
        for (int i = 0; i < nodes; i++)
            graph[i] = new ArrayList<>();
        return graph;
    }
    //Method to add edges to the graph
    public static void addEdge(List<Edge>[] graph, int s, int t, int cap) {
        graph[s].add(new Edge(t, graph[t].size(), cap));
        graph[t].add(new Edge(s, graph[s].size() - 1, 0));
    }

    // Breadth first search implemented in a loop to check if more flow is possible
    static boolean dinicBfs(List<Edge>[] graph, int src, int dest, int[] dist) {
        Arrays.fill(dist, -1);
        dist[src] = 0;
        int[] Q = new int[graph.length];
        int sizeQ = 0;
        Q[sizeQ++] = src;
        for (int i = 0; i < sizeQ; i++) {
            int u = Q[i];
            for (Edge e : graph[u]) {
                if (dist[e.t] < 0 && e.f < e.cap) {
                    dist[e.t] = dist[u] + 1;
                    Q[sizeQ++] = e.t;
                }
            }
        }
        return dist[dest] >= 0;
    }

    /*A DFS based function to send flow after BFS has
    figured out that there is a possible flow and
     constructed levels. This function called multiple
    times for a single call of BFS.*/

    static int dinicDfs(List<Edge>[] graph, int[] ptr, int[] dist, int dest, int u, int f) {
        if (u == dest)
            return f;
        for (; ptr[u] < graph[u].size(); ++ptr[u]) {
            Edge e = graph[u].get(ptr[u]);
            if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
                int df = dinicDfs(graph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
                if (df > 0) {
                    e.f += df;
                    graph[e.t].get(e.rev).f -= df;
                    return df;
                }
            }
        }
        return 0;
    }

    //Method to calculate the maximum flow
    public static int maxFlow(List<Edge>[] graph, int src, int dest) {
        int flow = 0;
        int[] dist = new int[graph.length];
        while (dinicBfs(graph, src, dest, dist)) {
            int[] ptr = new int[graph.length];
            while (true) {
                int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
                if (df == 0)
                    break;
                flow += df;
            }
        }
        return flow;
    }

    // Usage example
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            //add the text file path
            parser.run("C:\\Users\\Luchitha Pasqual\\Documents\\new (2).txt");

            int NOF_Nodes = parser.getNOF_Nodes();
            ArrayList<Integer> from = parser.getFrom();
            ArrayList<Integer> to = parser.getTo();
            ArrayList<Integer> capacity = parser.getCapacity();

            int n = NOF_Nodes;
//        int s = n - 2;
//        int t = n - 1;


            List<Edge>[] graph = createGraph(n);

            System.out.println(from.size());
            for (int i = 0; i < from.size(); i++) {
                addEdge(graph, from.get(i), to.get(i), capacity.get(i));
                System.out.println("Edge added: From: " + from.get(i) + " To: " + to.get(i) + " Capacity: " + capacity.get(i));
            }
            long start = System.currentTimeMillis();
            System.out.println("Maximum flow for the network is :" + maxFlow(graph, 0, n - 1));


            long now = System.currentTimeMillis();
            double elapsed = (now - start) / 1000.0;

            System.out.println("Time taken to run the FordFulkerson Algorithm : " + elapsed);
        }catch (IndexOutOfBoundsException e){
            System.out.println("Enter the file path at parser.run");
        }
    }
}