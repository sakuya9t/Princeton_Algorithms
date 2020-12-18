package Unit4.UndirectedGraphs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeDiameterTest {
    @Test
    public void testTreeDiameter1() {
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {1, 4}, {4, 5}};
        assertEquals(4, new TreeDiameter().treeDiameter(edges));
        assertEquals(1, new TreeDiameter().treeCenter(edges));
    }

    @Test
    public void testTreeDiameter2() {
        int[][] edges = {{0, 1}, {0, 2}};
        assertEquals(2, new TreeDiameter().treeDiameter(edges));
        assertEquals(0, new TreeDiameter().treeCenter(edges));
    }

    @Test
    public void testTreeDiameter3() {
        assertEquals(1, new TreeDiameter().treeDiameter(new int[][]{{0, 1}}));
        assertEquals(0, new TreeDiameter().treeCenter(new int[][]{{0, 1}}));
        assertEquals(3, new TreeDiameter().treeDiameter(new int[][]{{0, 3}, {1, 2}, {2, 3}, {4, 3}}));
        assertEquals(3, new TreeDiameter().treeCenter(new int[][]{{0, 3}, {1, 2}, {2, 3}, {4, 3}}));
        assertEquals(2, new TreeDiameter().treeDiameter(new int[][]{{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}}));
        assertEquals(0, new TreeDiameter().treeCenter(new int[][]{{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}}));
    }

    @Test
    public void testTreeDiameter4() {
        int[][] edges = {{14, 15}, {15, 13}, {15, 12}, {15, 1}, {12, 4}, {12, 3}, {12, 2}, {4, 5}, {4, 11}, {2, 7}, {7, 6}, {7, 8}, {7, 9}, {9, 10}};
        assertEquals(6, new TreeDiameter().treeDiameter(edges));
        assertEquals(2, new TreeDiameter().treeCenter(edges));
    }
}