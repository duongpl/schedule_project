package com.fpt.edu.schedule.testModel;

import com.fpt.edu.schedule.ai.model.Dinic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestDinic {
    @Test
    public void maxflowShouldReturnMaximumflowOfFlowNetwork() {
        Dinic dinic = new Dinic(8, 0, 6);
        dinic.add(0, 1, 3);
        dinic.add(1, 3, 3);
        dinic.add(3, 6, 2);
        dinic.add(0, 2, 1);
        dinic.add(2, 4, 4);
        dinic.add(2, 3, 5);
        dinic.add(4, 5, 2);
        dinic.add(5, 6, 3);
        int expectedFlow = 3;
        Assertions.assertEquals(expectedFlow, dinic.maxflow());
    }
    @Test
    public void maxflowOfEmptyGraphShouldReturnZero() {
        Dinic dinic = new Dinic(2, 0, 1);
        int expectedFlow = 0;
        Assertions.assertEquals(expectedFlow, dinic.maxflow());
    }
    @Test
    public void maxflowOfSameSourceSinkNetworkShouldReturnNegativeOne() {
        Dinic dinic = new Dinic(2, 0, 0);
        int expectedFlow = -1;
        Assertions.assertEquals(expectedFlow, dinic.maxflow());
    }
}
