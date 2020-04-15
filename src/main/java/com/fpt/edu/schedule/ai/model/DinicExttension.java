package com.fpt.edu.schedule.ai.model;


import com.fpt.edu.schedule.ai.lib.Graph;

import java.util.Random;
import java.util.Vector;

public class DinicExttension {
    static
    int n , source, sink, superSource1, superSource, superSink;
    int totalDemand;

    Vector<Graph.Edge> edges;
    public DinicExttension(int n,int source, int sink, Vector<Graph.Edge> edges) {
        this.n = n;
        this.source = source;
        this.sink = sink;
        this.superSource1 = n;
        this.superSource = n + 1;
        this.superSink = n + 2;
        this.edges = edges;
        this.totalDemand = 0;
    }


    public int maxflow() {
        int L = 0;
        int R = Dinic2.INF;
        int res = -1;
        while (L <= R) {
            int g = (L + R) >>> 1;
            if (hasFeseableFlow(g)) {
                res = g;
                L = g + 1;
            } else {
                R = g - 1;
            }
        }
        return res;
    }

    public int[][] maxflow1() {
        Dinic dinic = new Dinic(n + 3, superSource, superSink);
        totalDemand = 0;
        for(Graph.Edge edge:edges) {
            dinic.add(edge.u, edge.v, edge.d, edge.c);
            totalDemand += edge.d;
        }
        dinic.add(superSource1, source, dinic.INF, dinic.INF);
        dinic.add(sink, superSource1, dinic.INF);
        dinic.maxflow();
        return dinic.flow;
    }

    public boolean hasFeseableFlow(int g) {
        Dinic dinic = new Dinic(n + 3, superSource, superSink);
        totalDemand = 0;
        for(Graph.Edge edge:edges) {
            dinic.add(edge.u, edge.v, edge.d, edge.c);
            totalDemand += edge.d;
        }
        dinic.add(superSource1, source, g, dinic.INF);
        dinic.add(sink, superSource1, dinic.INF);
        int fl = dinic.maxflow();
        return fl == totalDemand + g;
    }

    public static void main(String[] args) {
        int n = 100;
        int maxv = 20;
        int d[][] = new int [n][n];
        int c[][] = new int [n][n];
        Random random = new Random(0);

        int totalDemand = 0;
        for(int i = 0 ; i < n ; i ++) {
            for(int j = i + 1; j < n ; j ++) {
                d[i][j] = random.nextInt(maxv) + 1;
                totalDemand += d[i][j];
                c[i][j] = d[i][j] + random.nextInt(maxv * n) + maxv * n;
            }
        }

        //

//        d[0][1] = 2;
//        d[0][2] = 1;
//        c[0][1] = 100;
//        c[0][2] = 100;
//        c[1][5] = 1;
//        c[1][3] = 1;
//        c[1][4] = 1;
//        c[2][4] = 1;
//        c[3][5] = 1;
//        c[4][5] = 1;
//        c[2][5] = 1;

        Vector<Graph.Edge> edges = new Vector<>();
        for(int i = 0;  i < n ; i ++) {
            for(int j = i + 1; j < n ; j ++) {
                edges.add(new Graph.Edge(i, j, d[i][j], c[i][j]));
            }
        }
        DinicExttension de = new DinicExttension(n, 0, n-1, edges);
        System.out.println( de.maxflow1());
//        for(int i = 0 ; i < 10;i ++) {
//            System.out.println(de.hasFeseableFlow(i));
//        }
    }
}
