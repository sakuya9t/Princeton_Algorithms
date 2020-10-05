/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordNet {
    private final Digraph g;
    private final Map<String, Set<Integer>> nounsMap = new HashMap<>();
    private final String[] nounsArray;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Parameter cannot be null");
        List<String> synsetFileContent = readFile(synsets), hypernymsContent = readFile(hypernyms);
        if (synsetFileContent.size() != hypernymsContent.size())
            throw new IllegalArgumentException("Input file has different vertices size.");
        int n = synsetFileContent.size();
        g = new Digraph(n);
        nounsArray = new String[n];

        for (String line : synsetFileContent) {
            String[] args = line.split(",");
            int id = Integer.parseInt(args[0]);
            nounsArray[id] = args[1];
            String[] words = args[1].split(" ");
            for (String word : words) {
                if (word.length() == 0) continue;
                nounsMap.putIfAbsent(word, new HashSet<>());
                nounsMap.get(word).add(id);
            }
        }

        for (String line : hypernymsContent) {
            String[] args = line.split(",");
            int id = Integer.parseInt(args[0]);
            for (int i = 1; i < args.length; i++) {
                g.addEdge(id, Integer.parseInt(args[i]));
            }
        }
        if (!isDAG(g)) throw new IllegalArgumentException("Input graph is not DAG.");
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.nounsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("Parameter cannot be null");
        return this.nounsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("Parameter cannot be null");
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Parameter not exist in noun list.");
        Set<Integer> id1 = this.nounsMap.get(nounA), id2 = this.nounsMap.get(nounB);
        return new SAP(this.g).length(id1, id2);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException("Parameter cannot be null");
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("Parameter not exist in noun list.");
        Set<Integer> id1 = this.nounsMap.get(nounA), id2 = this.nounsMap.get(nounB);
        int ancestorId = new SAP(this.g).ancestor(id1, id2);
        return ancestorId == -1 ? "" : this.nounsArray[ancestorId];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        System.out.println("Hello World");
    }

    private List<String> readFile(String filename) {
        In in = new In(filename);
        List<String> res = new LinkedList<>();
        while (in.hasNextLine()) res.add(in.readLine());
        return res;
    }

    private boolean isDAG(Digraph graph) {
        int n = graph.V();
        int[] indeg = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            indeg[i] = graph.indegree(i);
            if (indeg[i] == 0) queue.offer(i);
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : graph.adj(curr)) {
                indeg[next]--;
                if (indeg[next] == 0) queue.offer(next);
            }
        }

        for (int i : indeg) {
            if (i > 0) return false;
        }
        return true;
    }
}