package com.fpt.edu.schedule.ai.model;

import java.util.*;

public class PMX {
    Vector<Integer> p1;
    Vector<Integer> p2;
    private int seed;

    public PMX(Vector<Integer> p1, Vector<Integer> p2, int seed) {
        this.p1 = p1;
        this.p2 = p2;
        this.seed = seed;
    }

    public Vector<Integer> fillEmpty(Vector<Integer> p) {

        Vector<Integer> res = new Vector<>();
        int sz = p.size();
        for (int i = 0; i < sz; i++) {
            res.add(p.get(i));
        }
        int max = -1;
        for (int i = 0; i < sz; i++) {
            max = Math.max(max, p.get(i));
        }

        Vector<Integer> newNumbers = new Vector<>();
        for (int i = 0; i < sz; i++) {
            if (p.get(i) == -1) {
                newNumbers.add(++max);
            }
        }

        int cur = 0;
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i) == -1) {
                res.set(i, newNumbers.get(cur++));
            }
        }
        return res;
    }

    ;

    public Vector<Integer> getChildren() {
        int sz = p1.size();
        Random random = new Random(this.seed);
        int swathSize = (sz <= 2) ? sz - 1 : random.nextInt(sz - 2) + 2;
        int l = (sz <= 2) ? 0 : random.nextInt(sz - swathSize + 1);
        int r = l + swathSize;
        return this.getChildren(l, r);
    }

    public boolean isPermutationOfEachOther(Vector<Integer> a, Vector<Integer> b) {

        if (a.size() != b.size()) return false;
        Vector<Integer> aclone = new Vector<>();
        Vector<Integer> bclone = new Vector<>();
        for (int i = 0; i < a.size(); i++) {
            aclone.add(a.get(i));
            bclone.add(b.get(i));
        }
        Collections.sort(aclone);
        Collections.sort(bclone);
        for (int i = 0; i < a.size(); i++) {
            if (!aclone.get(i).equals(bclone.get(i))) return false;
        }
        return true;
    }

    public Vector<Integer> getChildren(int l, int r) {
        if (!isPermutationOfEachOther(p1, p2)) {
            return null;
        }
        int sz = p1.size();
        Map<Integer, Integer> map = new TreeMap<>();
        Vector<Integer> ids = new Vector<Integer>();
        int id = 0;
        for (int i = 0; i < sz; i++) {
            if (p1.get(i) != -1) {
                map.put(p1.get(i), id++);
                ids.add(p1.get(i));
            }
        }
        Vector<Integer> rp1 = new Vector<>();
        Vector<Integer> rp2 = new Vector<>();
        for (int i = 0; i < sz; i++) {
            if (p1.get(i) != -1) {
                rp1.add(map.get(p1.get(i)));
            } else rp1.add(-1);
            if (p2.get(i) != -1) {
                rp2.add(map.get(p2.get(i)));
            } else rp2.add(-1);
        }
        int max = -1;
        for (int i = 0; i < sz; i++) {
            max = Math.max(max, rp1.get(i));
        }
        Vector<Integer> cp1 = this.fillEmpty(rp1);
        Vector<Integer> cp2 = this.fillEmpty(rp2);

        int pos[] = new int[sz];
        for (int i = 0; i < sz; i++) {
            pos[cp2.get(i)] = i;
        }
        boolean mark[] = new boolean[sz];
        Vector<Integer> res = new Vector<>();
        for (int i = 0; i < sz; i++) res.add(-1);

        Set<Integer> bag = new TreeSet<>();
        for (int i = l; i < r; i++) {
            res.set(i, cp1.get(i));
            bag.add(cp1.get(i));
            mark[i] = true;
        }

        for (int i = l; i < r; i++) {
            int x = cp2.get(i);
            if (!bag.contains(x)) {
                int p = i;
                while (mark[p]) {
                    int v = cp1.get(p);
                    p = pos[v];
                }
                res.set(p, x);
                bag.add(x);
                mark[p] = true;
            }
        }

        for (int i = 0; i < sz; i++) {
            if (res.get(i) == -1) {
                res.set(i, cp2.get(i));
            }
        }

        for (int i = 0; i < sz; i++) {
            if (res.get(i) > max) {
                res.set(i, -1);
            } else {
                res.set(i, ids.get(res.get(i)));
            }
        }
        return res;
    }
}
