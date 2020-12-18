import edu.princeton.cs.algs4.DirectedEdge;

import java.util.*;

public class Graph {
    private Map<Integer, List<DirectedEdge>> edgeMap = new HashMap<>();
    private Set<Integer> vertices = new HashSet<>();

    public void addEdge(DirectedEdge e) {
        edgeMap.putIfAbsent(e.from(), new LinkedList<>());
        edgeMap.get(e.from()).add(e);
        vertices.add(e.from());
        vertices.add(e.to());
    }

    public int V() {
        return vertices.size();
    }

    public List<DirectedEdge> adj(int v) {
        return edgeMap.getOrDefault(v, Collections.emptyList());
    }
}
