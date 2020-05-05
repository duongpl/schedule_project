package com.fpt.edu.schedule.ai.model;

import java.util.Arrays;

public class HopcroftKarp {
    private int nx, ny, E, nmatches;
    int adj[], nxt[];
    int lst[], ptr[], lev[], que[], matx[], maty[];


    public HopcroftKarp(int nx, int ny) {
        this.nx = nx;
        this.ny = ny;
        this.lst = new int[nx];
        this.matx = new int[nx];
        this.maty = new int[ny];
        this.adj = new int[nx * ny];
        this.nxt = new int[nx * ny];
        this.lev = new int[nx];
        this.que = new int[nx];
        this.ptr = new int[nx];
        E = nmatches = 0;
        Arrays.fill(lst, -1);
        Arrays.fill(matx, -1);
        Arrays.fill(maty, -1);
    }

    public void add(int x, int y) {
        adj[E] = y;
        nxt[E] = lst[x];
        lst[x] = E++;
    }

    public void match(int x, int y) {
        this.matx[x] = y;
        this.maty[y] = x;
    }

    public int bfs() {
        int qh = 0, qe = 0;
        for (int x = 0; x < nx; x++) {
            if (matx[x] != -1) {
                lev[x] = 0;
            } else {
                lev[x] = 1;
                que[qe++] = x;
            }
        }
        int res = 0;
        while (qh < qe) {
            int x = que[qh++];
            for (int e = lst[x]; e != -1; e = nxt[e]) {
                int y = adj[e];
                if (maty[y] == -1) {
                    res = 1;
                } else if (lev[maty[y]] == 0) {
                    lev[maty[y]] = lev[x] + 1;
                    que[qe++] = maty[y];
                }
            }
        }
        return res;
    }

    public int dfs(int x) {
        for (int e = ptr[x]; e != -1; e = nxt[e]) {
            int y = adj[e];
            if (maty[y] == -1) {
                matx[x] = y;
                maty[y] = x;
                return 1;
            }
        }
        for (int e = ptr[x]; e != -1; e = nxt[e]) {
            int y = adj[e];
            if (maty[y] == -1 || (lev[maty[y]] == lev[x] + 1 && dfs(maty[y]) != 0)) {
                matx[x] = y;
                maty[y] = x;
                return 1;
            }
        }
        return 0;
    }

    public int maxmat() {
        while (bfs() != 0) {
            for (int x = 0; x < nx; x++) {
                ptr[x] = lst[x];
            }
            for (int x = 0; x < nx; x++) {
                if (matx[x] == -1) {
                    nmatches += dfs(x);
                }
            }
        }
        return nmatches;
    }

    int[] getMatching() {
        this.maxmat();
        return this.matx;
    }

    public static void main(String[] args) {
        HopcroftKarp hp = new HopcroftKarp(4, 4);
        hp.add(0, 0);
        hp.add(1, 1);
        hp.add(2, 2);
        hp.add(3, 3);
        hp.add(0, 1);
        hp.add(1, 0);
        hp.match(0, 0);


        for (int i = 0; i < 4; i++) {

        }
    }

}
