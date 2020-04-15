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
//        System.out.println(p);

        Vector<Integer> res = new Vector<>();
        int sz = p.size();
        for(int i = 0; i < sz; i++) {
            res.add(p.get(i));
        }
        int max = -1;
        for(int i = 0 ; i < sz; i++) {
//            System.out.println(p.get(i));
            max = Math.max(max, p.get(i));
        }

        Vector<Integer> newNumbers = new Vector<>();
        for(int i = 0; i < sz; i++) {
            if (p.get(i) == -1) {
                newNumbers.add(++max);
            }
        }

        Collections.shuffle(newNumbers);

        int cur = 0;
        for(int i = 0; i < p.size(); i++) {
            if (p.get(i) == -1) {
                res.set(i, newNumbers.get(cur++));
            }
        }
        return res;
    };
    public Vector<Integer> getChildren() {
//        System.out.println(p1);
//        System.out.println(p2);
        int sz = p1.size();
//        System.out.println(rp1.size() + " " + rp2.size());
//        for(int x:p1) System.out.print(x + " ");
//        System.out.println();
//        System.out.println("....");
//        for(int x:p2) System.out.print(x + " ");
//        System.out.println();

        Map<Integer, Integer> map = new TreeMap<>();

        Vector<Integer> ids = new Vector<Integer>();
        int id = 0;
        for(int i = 0; i < sz; i++) {
            if (p1.get(i) != -1) {
                map.put(p1.get(i), id ++);
                ids.add(p1.get(i));
            }
        }
        Vector<Integer> rp1 = new Vector<>();
        Vector<Integer> rp2 = new Vector<>();
        for(int i =0 ; i < sz; i++) {
            if (p1.get(i) != -1) {
                rp1.add(map.get(p1.get(i)));
            } else rp1.add(-1);
            if (p2.get(i) != -1) {
//                if (!map.containsKey(p2.get(i))) {
//                    System.out.println("asdfasdfasdf");
//                }
                rp2.add(map.get(p2.get(i)));
            } else rp2.add(-1);
        }
//        System.out.println(rp1.size() + " " + rp2.size());


        int max = -1;
        for(int i = 0 ; i < sz; i++) {
            max = Math.max(max, rp1.get(i));
        }
        Vector<Integer> cp1 = this.fillEmpty(rp1);
        Vector<Integer> cp2 = this.fillEmpty(rp2);
//        for(int x:cp1) System.out.print(x + " ");
//        System.out.println();
//        System.out.println("....");
//        for(int x:cp2) System.out.print(x + " ");
//        System.out.println();
//        System.out.println("xxxxxxxxx");
//        int min = sz + 1;
//        for(int i = 0 ; i < sz; i++) {
//            min = Math.min(min, cp1.get(i));
//        }
//        for(int i = 0 ; i < sz; i++) {
//            cp1.set(i, cp1.get(i) - min);
//            cp2.set(i, cp2.get(i) - min);
//        }

        Random random = new Random(this.seed);

        int pos[] = new int[sz];

        for(int i = 0; i < sz; i++) {
            pos[cp2.get(i)] = i;
        }

        boolean mark[] = new boolean[sz];


        int swathSize = random.nextInt(sz - 2) + 2;
        int l = random.nextInt(sz - swathSize + 1);
        int r = l + swathSize;

//        l = 3;
//        r = 8;
        Vector<Integer> res = new Vector<>();
        for(int i = 0; i < sz; i++) res.add(-1);

        Set<Integer> bag = new TreeSet<>();
        for(int i = l; i < r; i++) {
            res.set(i, cp1.get(i));
            bag.add(cp1.get(i));
            mark[i] = true;
        }

        for(int i = l; i < r; i ++) {
            int x = cp2.get(i);
            if (!bag.contains(x)) {
                int p = i;
                while (mark[p]) {
//                    System.out.println(p);
                    int v = cp1.get(p);
                    p = pos[v];
                }
                res.set(p, x);
                bag.add(x);
                mark[p] = true;
            }
        }

        for(int i = 0; i < sz; i++) {
            if (res.get(i) == -1) {
                res.set(i, cp2.get(i));
            }
        }

//        for(int i = 0; i < sz; i++) System.out.println(res.get(i));
////        for(int i = 0; i < sz; i++) {
////            res.set(i, res.get(i) + min);
//        }

//        for(int i = 0; i < sz; i++) System.out.println(res.get(i));
        for(int i = 0; i < sz; i++) {
            if (res.get(i) > max) {
                res.set(i, -1);
            } else {
                res.set(i, ids.get(res.get(i)));
            }
        }

//        System.out.println(l + " " + r);
//        for(int i = 0; i < sz; i++) System.out.println(res.get(i));

//        System.out.println("============");
        return res;
    }

    public static void main(String[] args) {
        Vector<Integer> p = new Vector<>();
        Vector<Integer> p1 = new Vector<>();
        int n = 9;
        for(int i = 0; i < n ; i ++) {
            p.add(i);
            p1.add(i);
        }

        while (true) {
            Collections.shuffle(p);
            Collections.shuffle(p1);
            PMX pmx = new PMX(p, p1, 0);
            pmx.getChildren();
        }

    }
}
