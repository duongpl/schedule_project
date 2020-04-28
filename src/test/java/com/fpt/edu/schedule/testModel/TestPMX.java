package com.fpt.edu.schedule.testModel;


import com.fpt.edu.schedule.ai.model.PMX;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Vector;

public class TestPMX {
    @Test
    public void ApplyFillEmptyFunctionToEmptyListShouldReturnEmptyList() {
        Vector<Integer> vt = new Vector<>();
        PMX pmx = new PMX(new Vector<Integer>(), new Vector<Integer>(), 0);
        Vector<Integer> res = pmx.fillEmpty(vt);
        Assertions.assertEquals(0, res.size());
    }

    @Test
    public void ApplyFillEmptyFunctionToListWithoutNegativeOneShouldReturnItself() {
        Vector<Integer> vt = new Vector<>();
        PMX pmx = new PMX(new Vector<Integer>(), new Vector<Integer>(), 0);

        int n = 10;
        for (int i = 0; i < n; i++) vt.add(i);
        Vector<Integer> res = pmx.fillEmpty(vt);

        Vector<Integer> expected = new Vector<>();
        for (int i = 0; i < n; i++) expected.add(i);
        Assertions.assertArrayEquals(expected.toArray(), res.toArray());
    }

    @Test
    public void testFillEmptyFunctionForRandomList() {
        Integer a[] = {-1, 2, 3, -1, 5, 8};

        Vector<Integer> vt = new Vector<>();
        Collections.addAll(vt, a);
        PMX pmx = new PMX(new Vector<Integer>(), new Vector<Integer>(), 0);

        Vector<Integer> res = pmx.fillEmpty(vt);

        Integer expectedArray[] = {9, 2, 3, 10, 5, 8};

        Assertions.assertArrayEquals(expectedArray, res.toArray());
    }

    @Test
    public void testFillEmptyForAllNegativeList() {
        int n = 10;
        Vector<Integer> vt = new Vector<>();
        for (int i = 0; i < n; i++) vt.add(-1);
        PMX pmx = new PMX(new Vector<Integer>(), new Vector<Integer>(), 0);

        Vector<Integer> res = pmx.fillEmpty(vt);

//        Collections.sort(res);

        Vector<Integer> expected = new Vector<>();
        for (int i = 0; i < n; i++) expected.add(i);
        Assertions.assertArrayEquals(expected.toArray(), res.toArray());
    }

    @Test
    public void isPermutationFunctionForTwoDiffentSizeListShouldReturnFalse() {
        Vector<Integer> vt = new Vector<>();
        Vector<Integer> vt1 = new Vector<>();
        PMX pmx = new PMX(new Vector<Integer>(), new Vector<Integer>(), 0);

        int n = 5;
        int m = 6;
        for (int i = 0; i < n; i++) vt.add(i);
        for (int j = 0; j < m; j++) vt1.add(j);
        Assertions.assertTrue(!pmx.isPermutationOfEachOther(vt, vt1));
    }

    @Test
    public void isPermutationFunctionForTwoSameSizeListButNotPermutationOfEachOtherShouldReturnFalse() {
        Vector<Integer> vt = new Vector<>();
        Vector<Integer> vt1 = new Vector<>();
        PMX pmx = new PMX(new Vector<Integer>(), new Vector<Integer>(), 0);

        int n = 5;
        for (int i = 0; i < n; i++) vt.add(i);
        for (int j = 0; j < n; j++) vt1.add(j + 1);
        Assertions.assertTrue(!pmx.isPermutationOfEachOther(vt, vt1));
    }

    @Test
    public void isPermutationFunctionForTwoSameSizeListAndPermutationOfEachOtherShouldReturnTrue() {
        Vector<Integer> vt = new Vector<>();
        Vector<Integer> vt1 = new Vector<>();
        PMX pmx = new PMX(new Vector<Integer>(), new Vector<Integer>(), 0);

        int n = 5;
        for (int i = 0; i < n; i++) vt.add(i);
        for (int j = n - 1; j >= 0; j--) vt1.add(j);
        Collections.shuffle(vt1);
        Assertions.assertTrue(pmx.isPermutationOfEachOther(vt, vt1));
    }

    @Test
    public void getChildrenShouldReturnAPermutationOfParent() {
        Vector<Integer> p1 = new Vector<>();
        Vector<Integer> p2 = new Vector<>();

        int n = 10;
        int m = 10;
        for (int i = 0; i < n; i++) {
            p1.add(i * 2 + 3);
        }
        for (int j = 0; j < m; j++) p1.add(-1);

        for (int i = 0; i < n + m; i++) p2.add(p1.get(i));
        PMX pmx = new PMX(p1, p2, 0);
        Vector<Integer> c = pmx.getChildren();

        Collections.sort(c);
        Collections.sort(p1);
        Assertions.assertArrayEquals(p1.toArray(), c.toArray());
    }

    @Test
    public void getChildrenShouldReturnNullIfParentIsNotPermutationOfEachOther() {
        Vector<Integer> p1 = new Vector<>();
        Vector<Integer> p2 = new Vector<>();
        int n = 10;
        for(int i = 0; i < n; i++) {
            p1.add(i);
            p2.add(i + 1);
        }

        PMX pmx = new PMX(p1, p2, 0);
        Vector<Integer> c = pmx.getChildren();
        Assertions.assertTrue(c == null);
    }

    @Test
    public void getChildrenShouldFromParentWithoutNegativeNumberShouldReturnCorrectAnswer() {
        Integer a[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer b[] = {3, 6, 1, 10, 8, 4, 9, 7, 2, 5};
        Vector<Integer> p1 = new Vector<>(); Collections.addAll(p1, a);
        Vector<Integer> p2 = new Vector<>(); Collections.addAll(p2, b);
        PMX pmx = new PMX(p1, p2, 0);
        int l = 2; int r = 6;

        System.out.println(pmx.getChildren(l, r));
        Integer expected [] = {1, 10, 3, 4, 5, 6, 9, 7, 2, 8};
        Assertions.assertArrayEquals(expected, pmx.getChildren(l, r).toArray());
    }
    @Test
    public void getChildrenShouldFromParentInGeneralCaseShouldReturnCorrectAnswer() {
        Integer a[] = {-1, 2, 3, -1, -1, 6, 7, 8, -1, 10};
        Integer b[] = {3, 6, -1, 10, 8, -1, -1, 7, 2, -1};
        //6, 0, 1, 7, 8, 2, 3, 4, 9, 5
        //1, 2, 6, 5, 4, 7, 8, 3, 0, 9
        //6, 5, 1, 7, 8, 2, 4, 3, 0, 9
        //-1, 10, 3, -1, -1, 6, 8, 7, 2, -1
        Vector<Integer> p1 = new Vector<>(); Collections.addAll(p1, a);
        Vector<Integer> p2 = new Vector<>(); Collections.addAll(p2, b);
        PMX pmx = new PMX(p1, p2, 0);
        int l = 2; int r = 6;

        Integer expected [] = {-1, 10, 3, -1, -1, 6, 8, 7, 2, -1};
        Assertions.assertArrayEquals(expected, pmx.getChildren(l, r).toArray());
    }
    @Test
    public void testGetChildrenFromEmptyParent() {
        Vector<Integer> p1 = new Vector<>();
        Vector<Integer> p2 = new Vector<>();

        PMX pmx = new PMX(p1, p2, 0);
        Vector<Integer> c = pmx.getChildren();
        Assertions.assertEquals(0, c.size());
    }
    @Test
    public void testGetChildrenFromParentWithSizeOne() {
        Vector<Integer> p1 = new Vector<>();
        Vector<Integer> p2 = new Vector<>();
        p1.add(1);
        p2.add(1);
        PMX pmx = new PMX(p1, p2, 0);
        Vector<Integer> c = pmx.getChildren();

        Integer [] expected = {1};
        Assertions.assertArrayEquals(expected, c.toArray());
    }


}
