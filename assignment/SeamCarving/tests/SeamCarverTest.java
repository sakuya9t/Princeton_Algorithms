import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SeamCarverTest {

    @Test
    public void testEnergy() {
        Picture picture = new Picture("3x4.png");
        SeamCarver carver = new SeamCarver(picture);
        assertEquals(1000.0, carver.energy(0, 0), 1e-6);
        assertEquals(Math.sqrt(52225.0), carver.energy(1, 1), 1e-6);
        assertEquals(Math.sqrt(52024.0), carver.energy(1, 2), 1e-6);
        assertEquals(1000.0, carver.energy(2, 3), 1e-6);
    }

    @Test
    public void testInitVerticalGraph() {
        Picture picture = new Picture("3x4.png");
        SeamCarver carver = new SeamCarver(picture);
    }

    @Test
    public void testInitHorizontalGraph() {
        Picture picture = new Picture("3x4.png");
        SeamCarver carver = new SeamCarver(picture);
    }

    @Test
    public void testFindHorizontalSeam() {
        Picture picture = new Picture("6x5.png");
        SeamCarver carver = new SeamCarver(picture);
        int[] seam = carver.findHorizontalSeam();
        assertArrayEquals(new int[]{3, 2, 1, 2, 1, 2}, seam);
    }

    @Test
    public void testFindVerticalSeam() {
        Picture picture = new Picture("6x5.png");
        SeamCarver carver = new SeamCarver(picture);
        int[] seam = carver.findVerticalSeam();
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, seam);
    }

    @Test
    public void testRemoveHorizontalSeam() {
        Picture picture = new Picture("6x5.png");
        SeamCarver carver = new SeamCarver(picture);
        carver.removeHorizontalSeam(new int[]{2, 3, 2, 1, 0, 1});

        assertEquals(1000.00, carver.energy(1, 3), 1e-2);
        assertEquals(237.35, carver.energy(1, 1), 1e-2);
        assertEquals(211.51, carver.energy(4, 1), 1e-2);
    }

    @Test
    public void testRemoveVerticalSeam() {
        Picture picture = new Picture("6x5.png");
        SeamCarver carver = new SeamCarver(picture);
        carver.removeVerticalSeam(new int[]{3, 4, 3, 2, 2});

        assertEquals(151.28, carver.energy(3, 1), 0.01);
        assertEquals(1000.00, carver.energy(4, 1), 0.01);
        assertEquals(133.47, carver.energy(1, 3), 0.01);
        assertEquals(103.24, carver.energy(2, 3), 0.01);
    }
}