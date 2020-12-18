import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.Iterator;

public class SeamCarver {
    private final Picture picture;
    private Graph horizonGraph, verticalGraph;
    private final double[][] energy;
    int width;
    int height;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.height = picture.height();
        this.width = picture.width();
        this.energy = initEnergy();
        this.horizonGraph = initHorizonGraph(this.energy);
        this.verticalGraph = initVerticalGraph(this.energy);
    }

    private double[][] initEnergy() {
        double[][] energy = new double[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                energy[i][j] = getEnergy(j, i);
            }
        }
        return energy;
    }
    private Graph initHorizonGraph(double[][] energy) {
        // id = h*w: dummy start; id = h*w+1: dummy goal
        Graph graph = new Graph();

        // col 0
        for(int i = 0; i < height; i++) graph.addEdge(new DirectedEdge(height*width, i*width, energy[i][0]));

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width-1; j++) {
                if(i > 0) graph.addEdge(new DirectedEdge(i*width+j, (i-1)*width+(j+1), energy[i-1][j+1]));
                graph.addEdge(new DirectedEdge(i*width+j, i*width+j+1, energy[i][j+1]));
                if(i < height-1) graph.addEdge(new DirectedEdge(i*width+j, (i+1)*width+(j+1), energy[i+1][j+1]));
            }
        }

        // last col
        for(int i = 0; i < height; i++) graph.addEdge(new DirectedEdge(i*width+width-1, height*width+1, 0));

        return graph;
    }

    private Graph initVerticalGraph(double[][] energy) {
        // id = h*w: dummy start; id = h*w+1: dummy goal
        Graph graph = new Graph();

        // row 0
        for(int i = 0; i < width; i++) graph.addEdge(new DirectedEdge(height*width, i, energy[0][i]));

        for(int i = 0; i < height-1; i++) {
            for(int j = 0; j < width; j++) {
                if(j > 0) graph.addEdge(new DirectedEdge(i*width+j, (i+1)*width+(j-1), energy[i+1][j-1]));
                graph.addEdge(new DirectedEdge(i*width+j, (i+1)*width+j, energy[i+1][j]));
                if(j < width-1) graph.addEdge(new DirectedEdge(i*width+j, (i+1)*width+(j+1), energy[i+1][j+1]));
            }
        }

        // last row
        for(int i = 0; i < width; i++) graph.addEdge(new DirectedEdge((height-1)*width+i, height*width+1, 0));

        return graph;
    }

    // current picture
    public Picture picture() {
        return this.picture;
    }

    // width of current picture
    public int width() {
        return this.width;
    }

    // height of current picture
    public int height() {
        return this.height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if(x < 0 || x >= width || y < 0 || y >= height) throw new IllegalArgumentException("x or y is out of range");
        return this.energy[y][x];
    }

    private double getEnergy(int x, int y) {
        if(x == 0 || x == this.width() - 1 || y == 0 || y == this.height() - 1) return 1000.0;
        Color r = picture.get(x+1, y), l = picture.get(x-1, y);
        Color t = picture.get(x, y-1), b = picture.get(x, y+1);
        double dx = (r.getRed() - l.getRed()) * (r.getRed() - l.getRed()) + (r.getGreen() - l.getGreen()) * (r.getGreen() - l.getGreen()) + (r.getBlue() - l.getBlue()) * (r.getBlue() - l.getBlue());
        double dy = (t.getRed() - b.getRed()) * (t.getRed() - b.getRed()) + (t.getGreen() - b.getGreen()) * (t.getGreen() - b.getGreen()) + (t.getBlue() - b.getBlue()) * (t.getBlue() - b.getBlue());
        return Math.sqrt(dx + dy);
    }

    protected Graph getHorizonGraph() {
        return this.horizonGraph;
    }

    protected Graph getVerticalGraph() {
        return this.verticalGraph;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        Iterator<DirectedEdge> edges = new ShortestPath(horizonGraph, height*width).pathTo(height*width+1).iterator();
        int[] res = new int[width];
        for(int i = 0; i < width; i++) {
            res[i] = edges.next().to() / width;
        }
        return res;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        Iterator<DirectedEdge> edges = new ShortestPath(verticalGraph, height*width).pathTo(height*width+1).iterator();
        int[] res = new int[height];
        for(int i = 0; i < height; i++) {
            res[i] = edges.next().to() % width;
        }
        return res;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if(seam == null) throw new IllegalArgumentException("seam is null");
        if(this.height <= 1) throw new IllegalArgumentException("height is 1, cannot remove horizontal seam");

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if(seam == null) throw new IllegalArgumentException("seam is null");
        if(this.width <= 1) throw new IllegalArgumentException("Width is 1, cannot remove vertical seam");

    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}