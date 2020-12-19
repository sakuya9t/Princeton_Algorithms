import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Iterator;

public class SeamCarver {
    private Picture picture;
    private double[][] energyTable;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
        this.energyTable = initEnergy();
    }

    private double[][] initEnergy() {
        double[][] energy = new double[height()][width()];
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                energy[i][j] = calcEnergy(j, i);
            }
        }
        return energy;
    }

    private EdgeWeightedDigraph buildHorizonGraph() {
        final int height = height(), width = width();

        // id = h*w: dummy start; id = h*w+1: dummy goal
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(height * width + 2);

        // col 0
        for (int i = 0; i < height; i++) graph.addEdge(new DirectedEdge(height * width, i * width, energyTable[i][0]));

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width - 1; j++) {
                if (i > 0)
                    graph.addEdge(new DirectedEdge(i * width + j, (i - 1) * width + (j + 1), energyTable[i - 1][j + 1]));
                graph.addEdge(new DirectedEdge(i * width + j, i * width + j + 1, energyTable[i][j + 1]));
                if (i < height - 1)
                    graph.addEdge(new DirectedEdge(i * width + j, (i + 1) * width + (j + 1), energyTable[i + 1][j + 1]));
            }
        }

        // last col
        for (int i = 0; i < height; i++) graph.addEdge(new DirectedEdge(i * width + width - 1, height * width + 1, 0));

        return graph;
    }

    private EdgeWeightedDigraph buildVerticalGraph() {
        final int height = height(), width = width();

        // id = h*w: dummy start; id = h*w+1: dummy goal
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(height * width + 2);

        // row 0
        for (int i = 0; i < width; i++) graph.addEdge(new DirectedEdge(height * width, i, energyTable[0][i]));

        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < width; j++) {
                if (j > 0)
                    graph.addEdge(new DirectedEdge(i * width + j, (i + 1) * width + (j - 1), energyTable[i + 1][j - 1]));
                graph.addEdge(new DirectedEdge(i * width + j, (i + 1) * width + j, energyTable[i + 1][j]));
                if (j < width - 1)
                    graph.addEdge(new DirectedEdge(i * width + j, (i + 1) * width + (j + 1), energyTable[i + 1][j + 1]));
            }
        }

        // last row
        for (int i = 0; i < width; i++)
            graph.addEdge(new DirectedEdge((height - 1) * width + i, height * width + 1, 0));

        return graph;
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return this.picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new IllegalArgumentException("x or y is out of range");
        return this.energyTable[y][x];
    }

    private double calcEnergy(int x, int y) {
        if (x == 0 || x == this.width() - 1 || y == 0 || y == this.height() - 1) return 1000.0;
        Color r = picture.get(x + 1, y), l = picture.get(x - 1, y);
        Color t = picture.get(x, y - 1), b = picture.get(x, y + 1);
        double dx = (r.getRed() - l.getRed()) * (r.getRed() - l.getRed()) + (r.getGreen() - l.getGreen()) * (r.getGreen() - l.getGreen()) + (r.getBlue() - l.getBlue()) * (r.getBlue() - l.getBlue());
        double dy = (t.getRed() - b.getRed()) * (t.getRed() - b.getRed()) + (t.getGreen() - b.getGreen()) * (t.getGreen() - b.getGreen()) + (t.getBlue() - b.getBlue()) * (t.getBlue() - b.getBlue());
        return Math.sqrt(dx + dy);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        EdgeWeightedDigraph horizonGraph = buildHorizonGraph();
        Iterator<DirectedEdge> edges = new ShortestPath(horizonGraph, height() * width()).pathTo(height() * width() + 1).iterator();
        int[] res = new int[width()];
        for (int i = 0; i < width(); i++) {
            res[i] = edges.next().to() / width();
        }
        return res;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        EdgeWeightedDigraph verticalGraph = buildVerticalGraph();
        Iterator<DirectedEdge> edges = new ShortestPath(verticalGraph, height() * width()).pathTo(height() * width() + 1).iterator();
        int[] res = new int[height()];
        for (int i = 0; i < height(); i++) {
            res[i] = edges.next().to() % width();
        }
        return res;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("seam is null");
        if (seam.length != width()) throw new IllegalArgumentException("Input seam has wrong length");
        if (this.height() <= 1) throw new IllegalArgumentException("height is 1, cannot remove horizontal seam");
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i + 1] - seam[i]) > 1)
                throw new IllegalArgumentException("Adjacent item in seam differs over 1");
        }
        for (int k : seam) {
            if (k < 0 || k >= height()) throw new IllegalArgumentException("Trying to seam a grid out of picture");
        }

        Picture newPicture = new Picture(width(), height() - 1);
        for (int i = 0; i < height() - 1; i++) {
            for (int j = 0; j < width(); j++) {
                if (i < seam[j]) newPicture.set(j, i, picture.get(j, i));
                else newPicture.set(j, i, picture.get(j, i + 1));
            }
        }

        this.picture = newPicture;
        this.energyTable = initEnergy();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("seam is null");
        if (seam.length != height()) throw new IllegalArgumentException("Input seam has wrong length");
        if (this.width() <= 1) throw new IllegalArgumentException("Width is 1, cannot remove vertical seam");
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i + 1] - seam[i]) > 1)
                throw new IllegalArgumentException("Adjacent item in seam differs over 1");
        }
        for (int k : seam) {
            if (k < 0 || k >= width()) throw new IllegalArgumentException("Trying to seam a grid out of picture");
        }

        Picture newPicture = new Picture(width() - 1, height());
        double[][] newEnergy = new double[height()][width() - 1];

        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width() - 1; j++) {
                if (j < seam[i]) newPicture.set(j, i, picture.get(j, i));
                else newPicture.set(j, i, picture.get(j + 1, i));
            }
        }

        this.picture = newPicture;
        this.energyTable = initEnergy();
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        System.out.println("Nothing.");
    }

}