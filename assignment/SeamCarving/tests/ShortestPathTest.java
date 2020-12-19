import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ShortestPathTest {
    private ShortestPath sp;
    private final List<DirectedEdge> edges = Arrays.asList(
            new DirectedEdge(0, 1, 5.0),
            new DirectedEdge(0, 4, 9.0),
            new DirectedEdge(0, 7, 8.0),
            new DirectedEdge(1, 2, 12.0),
            new DirectedEdge(1, 3, 15.0),
            new DirectedEdge(1, 7, 4.0),
            new DirectedEdge(2, 3, 3.0),
            new DirectedEdge(2, 6, 11.0),
            new DirectedEdge(3, 6, 9.0),
            new DirectedEdge(4, 5, 4.0),
            new DirectedEdge(4, 6, 20.0),
            new DirectedEdge(4, 7, 5.0),
            new DirectedEdge(5, 2, 1.0),
            new DirectedEdge(5, 6, 13.0),
            new DirectedEdge(7, 5, 6.0),
            new DirectedEdge(7, 2, 7.0)
    );

    @Before
    public void setUp() {
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(8);
        edges.forEach(g::addEdge);
        sp = new ShortestPath(g, 0);
    }

    @Test
    public void verifyDistTo() {
        assertEquals(5.0, sp.distTo(1), 1e-6);
        assertEquals(14.0, sp.distTo(2), 1e-6);
        assertEquals(17.0, sp.distTo(3), 1e-6);
        assertEquals(9.0, sp.distTo(4), 1e-6);
        assertEquals(13.0, sp.distTo(5), 1e-6);
        assertEquals(25.0, sp.distTo(6), 1e-6);
        assertEquals(8.0, sp.distTo(7), 1e-6);
    }

    @Test
    public void verifyPath() {
        List<List<Integer>> order = Arrays.asList(
                Collections.singletonList(1),
                Arrays.asList(4, 5, 2),
                Arrays.asList(4, 5, 2, 3),
                Collections.singletonList(4),
                Arrays.asList(4, 5),
                Arrays.asList(4, 5, 2, 6),
                Collections.singletonList(7)
        );

        for (int i = 1; i < 8; i++) {
            Iterator<DirectedEdge> path = sp.pathTo(i).iterator();
            Iterator<Integer> expected = order.get(i - 1).iterator();

            while (path.hasNext()) {
                if (!expected.hasNext()) {
                    fail();
                }
                assertEquals(expected.next(), Integer.valueOf(path.next().to()));
            }
        }
    }
}