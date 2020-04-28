package com.fpt.edu.schedule.testModel;

import com.fpt.edu.schedule.ai.model.HopcroftKarp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestHopcroftKarp {
    @Test
    public void maxMatchingShouldReturnZeroForEmptyGraph() {
        HopcroftKarp hk = new HopcroftKarp(0, 0);
        Assertions.assertEquals(0, hk.maxmat());
    }

    @Test
    public void maxMatchingShouldReturnCorrectInCaseNxGreaterNy() {
        HopcroftKarp hk = new HopcroftKarp(5, 4);
        hk.add(4, 1);
        hk.add(0, 1);
        hk.add(3, 2);
        hk.add(2, 0);
        hk.add(1, 1);
        hk.add(3, 3);
        int expectedMatching = 3;
        Assertions.assertEquals(expectedMatching, hk.maxmat());
    }

    @Test
    public void maxMatchingShouldReturnCorrectInCaseNxLowerThanNy() {
        HopcroftKarp hk = new HopcroftKarp(4, 5);
        hk.add(1, 4);
        hk.add(1, 0);
        hk.add(2, 3);
        hk.add(0, 2);
        hk.add(1, 1);
        hk.add(3, 3);
        int expectedMatching = 3;
        Assertions.assertEquals(expectedMatching, hk.maxmat());
    }
}
