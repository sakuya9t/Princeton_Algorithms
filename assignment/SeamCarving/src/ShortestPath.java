import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ShortestPath {
    private final DirectedEdge[] edgeTo;
    private final double[] distTo;

    public ShortestPath(EdgeWeightedDigraph G, int s) {
        this.edgeTo = new DirectedEdge[G.V()];
        this.distTo = new double[G.V()];
        doCalc(G, s);
    }

    private void doCalc(EdgeWeightedDigraph G, int s) {
        IndexMinPQ<Double> pq = new IndexMinPQ<>(G.V());
        Arrays.fill(this.distTo, Double.POSITIVE_INFINITY);
        this.distTo[s] = 0;

        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v)) {
                double dist = e.weight() + distTo[v];
                if (dist < distTo[e.to()]) {
                    distTo[e.to()] = dist;
                    edgeTo[e.to()] = e;
                    if (pq.contains(e.to())) pq.changeKey(e.to(), dist);
                    else pq.insert(e.to(), dist);
                }
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        List<DirectedEdge> edges = new LinkedList<>();
        while (edgeTo[v] != null) {
            edges.add(0, edgeTo[v]);
            v = edgeTo[v].from();
        }

        return edges;
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
}
