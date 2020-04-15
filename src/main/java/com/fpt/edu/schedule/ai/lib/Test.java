package com.fpt.edu.schedule.ai.lib;

import org.jscience.mathematics.number.Real;
import org.jscience.mathematics.vector.DenseMatrix;
import org.jscience.mathematics.vector.DenseVector;

import java.util.Random;
import java.util.Vector;

public class Test {
    public static void main(String[] args) {
        Vector a = new Vector<>();
        Random random = new Random();
        a.add(Real.valueOf(random.nextInt(10)));
        a.add(Real.valueOf(random.nextInt(10)));
        a.add(Real.valueOf(random.nextInt(10)));
        DenseVector dv = DenseVector.valueOf(a);
        DenseMatrix matrix = DenseMatrix.valueOf(dv, dv, dv);
        DenseMatrix matrix1 = DenseMatrix.valueOf(matrix);
        DenseMatrix res = matrix.times(matrix1);
        for(int i = 0; i < 3; i++) {
            for(int j =0 ; j < 3;j ++) {
                System.out.println(res.get(i, j));
            }
        }
    }
}
