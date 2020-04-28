package com.fpt.edu.schedule.ai.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dinic {
    public static final int INF = (int) 1e9;
    int n, s, t, flow[][], cap[][], lv[];
    int matx[];

    public void reset() {
        this.flow = new int[n][n];
        this.lv = new int[n];
    }

    Set<Integer> prioritizedNodes;

    public Dinic(int n, int s, int t) {
        this.n = n;
        this.s = s;
        this.t = t;
        this.cap = new int[n][n];
        this.matx = new int[n];
        this.prioritizedNodes = new TreeSet<>();
        Arrays.fill(matx, -1);
        reset();
    }

    public void match(int u, int v) {
        this.matx[u] = v;
        this.prioritizedNodes.add(u);
    }

    public void add(int u, int v, int c) {
        this.cap[u][v] += c;
        this.cap[u][v] = Math.min(this.cap[u][v], INF);
    }

    public void add(int u, int v, int d, int c) {
        this.add(s, v, d);
        this.add(u, t, d);
        this.add(u, v, c - d);
    }

    public boolean bfs() {
        LinkedList queue = new LinkedList<Integer>();
        Arrays.fill(lv, -1);
        queue.add(s);
        lv[s] = 0;
        while (!queue.isEmpty()) {
            int u = (int) queue.getFirst();
            for (int v = 0; v < this.n; v++) {
                if (lv[v] == -1 && cap[u][v] > flow[u][v]) {
                    lv[v] = lv[u] + 1;
                    queue.add(v);
                }
            }
            queue.removeFirst();
        }
        return lv[t] != -1;
    }

    public int argument1(int u, int mx, boolean ok) {
        if (u == t) if (ok) return mx; else return 0;
        if (matx[u] != -1) {
            int v = matx[u];
            if (lv[v] == lv[u] + 1 && cap[u][v] > flow[u][v]) {
                int tmp = argument1(v, Math.min(mx, cap[u][v] - flow[u][v]), true);
                if (tmp > 0) {
                    flow[u][v] += tmp;
                    flow[v][u] -= tmp;
                    return tmp;
                }
            }
        } else {
            for(int v = 0 ; v < n; v ++) {
                if (lv[v] == lv[u] + 1 && cap[u][v] > flow[u][v]) {
                    int tmp = argument1(v, Math.min(mx, cap[u][v] - flow[u][v]), ok);
                    if (tmp > 0) {
                        flow[u][v] += tmp;
                        flow[v][u] -= tmp;
                        return tmp;
                    }
                }
            }
        }
        return 0;
    }
    public int argument(int u, int mx) {
        if (u == t) return mx;
        if (matx[u] != -1) {
            int v = matx[u];
            if (lv[v] == lv[u] + 1 && cap[u][v] > flow[u][v]) {
                int tmp = argument(v, Math.min(mx, cap[u][v] - flow[u][v]));
                if (tmp != 0) {
                    flow[u][v] += tmp;
                    flow[v][u] -= tmp;
                    return tmp;
                }
            }
        }

        Vector<Integer> vt = new Vector<>();
        for (int i = 0; i < n; i++) vt.add(i);
        for (int v : vt) {
            if (lv[v] == lv[u] + 1 && cap[u][v] > flow[u][v] && this.prioritizedNodes.contains(v)) {
                int tmp = argument(v, Math.min(mx, cap[u][v] - flow[u][v]));
                if (tmp != 0) {
                    flow[u][v] += tmp;
                    flow[v][u] -= tmp;
                    return tmp;
                }
            }
        }
        Collections.shuffle(vt);

        for (int v : vt) {
            if (lv[v] == lv[u] + 1 && cap[u][v] > flow[u][v]) {
                int tmp = argument(v, Math.min(mx, cap[u][v] - flow[u][v]));
                if (tmp != 0) {
                    flow[u][v] += tmp;
                    flow[v][u] -= tmp;
                    return tmp;
                }
            }
        }
        return 0;
    }


    public int maxflow() {
        if (s == t) return -1;
        this.reset();
        int flow = 0;
        while (bfs()) {
            int fl;
            while ((fl = argument1(s, INF, false)) > 0) {
                flow += fl;
            }
            while ((fl = argument(s, INF)) > 0) {
                flow += fl;
            }
        }
        return flow;
    }
}

