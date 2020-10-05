/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SAPTestU {
    @Test
    public void testSinglePointPair25() {
        Digraph digraph = new Digraph(new In("digraph25.txt"));
        SAP sap = new SAP(digraph);
        assertEquals(4, sap.length(13, 16));
        assertEquals(3, sap.ancestor(13, 16));
        assertEquals(5, sap.length(17, 24));
        assertEquals(5, sap.ancestor(17, 24));
        assertEquals(7, sap.length(16, 11));
        assertEquals(0, sap.ancestor(16, 11));
        assertEquals(2, sap.length(3, 0));
        assertEquals(0, sap.ancestor(0, 3));
        assertEquals(0, sap.length(1, 1));
        assertEquals(1, sap.ancestor(1, 1));
    }

    @Test
    public void testSinglePointPair2() {
        Digraph digraph = new Digraph(new In("digraph2.txt"));
        SAP sap = new SAP(digraph);
        assertEquals(2, sap.length(1, 5));
        assertEquals(0, sap.ancestor(1, 5));
    }

    @Test
    public void testSinglePointPair3() {
        Digraph digraph = new Digraph(new In("digraph3.txt"));
        SAP sap = new SAP(digraph);
        assertEquals(3, sap.length(10, 7));
        assertEquals(10, sap.ancestor(10, 7));
    }

    @Test
    public void testSinglePointPair4() {
        Digraph digraph = new Digraph(new In("digraph4.txt"));
        SAP sap = new SAP(digraph);
        assertEquals(3, sap.length(1, 4));
        assertEquals(4, sap.ancestor(1, 4));
    }

    @Test
    public void testSinglePointPair5() {
        Digraph digraph = new Digraph(new In("digraph5.txt"));
        SAP sap = new SAP(digraph);
        assertEquals(5, sap.length(17, 21));
        assertEquals(21, sap.ancestor(17, 21));
    }

    @Test
    public void testSinglePointPair6() {
        Digraph digraph = new Digraph(new In("digraph6.txt"));
        SAP sap = new SAP(digraph);
        assertEquals(4, sap.length(5, 1));
        assertEquals(4, sap.ancestor(5, 1));
    }
}
