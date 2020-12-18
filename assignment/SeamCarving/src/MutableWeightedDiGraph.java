import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;

public class MutableWeightedDiGraph extends EdgeWeightedDigraph {
    public MutableWeightedDiGraph(int V) {
        super(V);
    }

    public MutableWeightedDiGraph(int V, int E) {
        super(V, E);
    }

    public MutableWeightedDiGraph(In in) {
        super(in);
    }

    public MutableWeightedDiGraph(EdgeWeightedDigraph G) {
        super(G);
    }


}
