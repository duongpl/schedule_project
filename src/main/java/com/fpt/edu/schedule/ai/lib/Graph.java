package com.fpt.edu.schedule.ai.lib;

import java.util.Vector;


public class Graph {
    public static class Node {
        public static final int NORMAL_NODE = 0;
        public static final int TEACHER_NODE = 1;
        public static final int TEACHER_SLOT_NODE = 2;
        public static final int SUBJECT_NODE = 3;
        public static final int SUBJECT_FAKE_NODE = 4;
        public int id, type;

        public Node(int id, int type) {
            this.id = id;
            this.type = type;
        }
    }

    public static class Edge {
        public int u, v, d, c;
        public Edge(int u,int v,int d,int c) {
            this.u = u;
            this.v = v;
            this.c = c;
            this.d = d;
        }
    }


    Vector<Node> nodes;
    Vector<Edge> edges;

    public Graph(Vector<Node> nodes, Vector<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }
}
