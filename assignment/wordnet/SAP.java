/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SAP {
    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(Collections.singletonList(v), Collections.singletonList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(Collections.singletonList(v), Collections.singletonList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        Map<Integer, Integer> vMap = search(v), wMap = search(w);
        int dist = Integer.MAX_VALUE;
        for (int i : vMap.keySet()) {
            if (wMap.containsKey(i)) {
                int d = vMap.get(i) + wMap.get(i);
                if (d < dist) {
                    dist = d;
                }
            }
        }
        return dist == Integer.MAX_VALUE ? -1 : dist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        Map<Integer, Integer> vMap = search(v), wMap = search(w);
        int dist = Integer.MAX_VALUE, ancestor = -1;
        for (int i : vMap.keySet()) {
            if (wMap.containsKey(i)) {
                int d = vMap.get(i) + wMap.get(i);
                if (d < dist) {
                    dist = d;
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    private Map<Integer, Integer> search(Iterable<Integer> s) {
        Map<Integer, Integer> res = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        for (int i : s) {
            queue.offer(i);
            res.put(i, 0);
        }
        int level = 0, size = queue.size();

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            size--;
            for (int next : graph.adj(curr)) {
                if (!res.containsKey(next)) {
                    res.put(next, level + 1);
                    queue.offer(next);
                }
            }
            if (size == 0) {
                size = queue.size();
                level++;
            }
        }
        return res;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
