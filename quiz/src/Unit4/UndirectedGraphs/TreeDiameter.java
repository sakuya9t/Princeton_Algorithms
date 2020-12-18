package Unit4.UndirectedGraphs;

import java.util.*;

class TreeDiameter {
    static class Point {
        int id;
        List<Integer> path = new LinkedList<>();

        Point(int id) {
            this.id = id;
        }

        public String toString() {
            return String.format("%s: %s", id, path);
        }
    }
    int treeDiameter(int[][] edges) {
        return calcDiameter(edges).path.size() - 1;
    }

    int treeCenter(int[][] edges) {
        List<Integer> path = calcDiameter(edges).path;
        return path.get(path.size() / 2);
    }

    private Point calcDiameter(int[][] edges) {
        Map<Integer, List<Point>> graph = new HashMap<>();
        for (int[] e : edges) {
            graph.putIfAbsent(e[0], new LinkedList<>());
            graph.putIfAbsent(e[1], new LinkedList<>());
            graph.get(e[0]).add(new Point(e[1]));
            graph.get(e[1]).add(new Point(e[0]));
        }

        Point s = bfs(new Point(edges[0][0]), graph);
        return bfs(new Point(s.id), graph);
    }

    private Point bfs(Point p, Map<Integer, List<Point>> graph) {
        p.path.add(p.id);
        Set<Integer> visited = new HashSet<>();
        Queue<Point> q = new LinkedList<>();
        visited.add(p.id);
        q.offer(p);
        Point res = p;

        while (!q.isEmpty()) {
            Point curr = q.poll();
            res = curr;
            for (Point next : graph.getOrDefault(curr.id, new LinkedList<>())) {
                if (!visited.contains(next.id)) {
                    visited.add(next.id);
                    next.path = new LinkedList<>(curr.path);
                    next.path.add(next.id);
                    q.offer(next);
                }
            }
        }
        return res;
    }
}
