package com.fpt.edu.schedule.ai.model;


import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.lib.Graph;
import com.fpt.edu.schedule.ai.lib.Slot;
import com.fpt.edu.schedule.ai.lib.SlotGroup;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Vector;

@Data
@AllArgsConstructor
public class ResourceChecker {

    //return true if current have enough resource for all class
    private InputData inputData;

    private static Vector<Integer> getClassBySlot(Vector<Class> classes, int slotId) {
        Vector<Integer> res = new Vector<>();
        for (Class c : classes) {
            if (c.getSlotId() == slotId) {
                res.add(c.getId());
            }
        }
        return res;
    }

    private boolean check(int numberOfClass) {
        Vector<Graph.Edge> edges = new Vector<>();

        Vector<Slot> slots = SlotGroup.getSlotList(inputData.getSlots());
        int source = inputData.getTeachers().size() * (slots.size() + 1) + inputData.getClasses().size();
        int sink = source + 1;
        int superSource1 = sink + 1;
        int superSource = superSource1 + 1;
        int superSink = superSource + 1;

        Dinic dinic = new Dinic(superSink + 1, superSource, superSink);
        for (int i = 0; i < inputData.getTeachers().size(); i++) {
            edges.add(new Graph.Edge(source, i, inputData.getTeachers().get(i).getQuota(), Dinic.INF));
        }

        for (int i = 0; i < inputData.getClasses().size(); i++) {
            edges.add(new Graph.Edge(inputData.getTeachers().size() * (1 + slots.size()) + i, sink, 0, 1));
        }

        for (int i = 0; i < inputData.getTeachers().size(); i++) {
            for (int j = 0; j < slots.size(); j++) {
                if (inputData.getRegisteredSlots()[i][j] > 0)
                    dinic.add(i, inputData.getTeachers().size() + i * slots.size() + j, 1);
            }
        }

        for (int j = 0; j < slots.size(); j++) {
            for (int i = 0; i < inputData.getTeachers().size(); i++) {
                Vector<Integer> classes = getClassBySlot(inputData.getClasses(), j);
                for (int classId : classes) {
                    int subjectId = inputData.getClasses().get(classId).getSubjectId();
                    if (inputData.getRegisteredSubjects()[i][subjectId] > 0) {
                        edges.add(new Graph.Edge(inputData.getTeachers().size() + i * slots.size() + j,
                                inputData.getTeachers().size() * (1 + slots.size()) + classId, 0, 1));
                    }
                }
            }
        }

        for (Graph.Edge edge : edges) {
            dinic.add(edge.u, edge.v, edge.d, edge.c);
        }
        dinic.add(sink, superSource1, Dinic.INF);

        int totalDemand = 0;
        for (int i = 0; i < inputData.getTeachers().size(); i++) {
            totalDemand += inputData.getTeachers().get(i).getQuota();
        }
        dinic.add(superSource1, source, numberOfClass, Dinic.INF);

        int fl = dinic.maxflow();
        if (fl == numberOfClass + totalDemand) {
            return true;
        } else return false;
    }

    public Vector<Class> getPossibleClasses(int numberOfClass) {
        Vector<Graph.Edge> edges = new Vector<>();

        Vector<Slot> slots = SlotGroup.getSlotList(inputData.getSlots());
        int source = inputData.getTeachers().size() * (slots.size() + 1) + inputData.getClasses().size();
        int sink = source + 1;
        int superSource1 = sink + 1;
        int superSource = superSource1 + 1;
        int superSink = superSource + 1;

        Dinic dinic = new Dinic(superSink + 1, superSource, superSink);
        for (int i = 0; i < inputData.getTeachers().size(); i++) {
            edges.add(new Graph.Edge(source, i, inputData.getTeachers().get(i).getQuota(), Dinic.INF));
        }

        for (int i = 0; i < inputData.getClasses().size(); i++) {
            edges.add(new Graph.Edge(inputData.getTeachers().size() * (1 + slots.size()) + i, sink, 0, 1));
        }

        for (int i = 0; i < inputData.getTeachers().size(); i++) {
            for (int j = 0; j < slots.size(); j++) {
                if (inputData.getRegisteredSlots()[i][j] > 0)
                    dinic.add(i, inputData.getTeachers().size() + i * slots.size() + j, 1);
            }
        }

        for (int j = 0; j < slots.size(); j++) {
            for (int i = 0; i < inputData.getTeachers().size(); i++) {
                Vector<Integer> classes = getClassBySlot(inputData.getClasses(), j);
                for (int classId : classes) {
                    int subjectId = inputData.getClasses().get(classId).getSubjectId();
                    if (inputData.getRegisteredSubjects()[i][subjectId] > 0) {
                        edges.add(new Graph.Edge(inputData.getTeachers().size() + i * slots.size() + j,
                                inputData.getTeachers().size() * (1 + slots.size()) + classId, 0, 1));
                    }
                }
            }
        }

        for (int j = 0; j < slots.size(); j++) {
            for (int i = 0; i < inputData.getTeachers().size(); i++) {
                Vector<Integer> classes = getClassBySlot(inputData.getClasses(), j);
                for (int classId : classes) {
                    int subjectId = inputData.getClasses().get(classId).getSubjectId();
                    if (inputData.getRegisteredSubjects()[i][subjectId] > 0) {
                        edges.add(new Graph.Edge(inputData.getTeachers().size() + i * slots.size() + j,
                                inputData.getTeachers().size() * (1 + slots.size()) + classId, 0, 1));
                    }
                }
            }
        }

        for (Graph.Edge edge : edges) {
            dinic.add(edge.u, edge.v, edge.d, edge.c);
        }
        dinic.add(sink, superSource1, Dinic.INF);

        int totalDemand = 0;
        for (int i = 0; i < inputData.getTeachers().size(); i++) {
            totalDemand += inputData.getTeachers().get(i).getQuota();
        }
        dinic.add(superSource1, source, numberOfClass, Dinic.INF);

        int fl = dinic.maxflow();


        Vector<Class> res = new Vector<>();

        if (fl == numberOfClass + totalDemand) {
            int[][] flow = dinic.flow;

            for (int j = 0; j < slots.size(); j++) {

                int cnt = 0;

                for (int i = 0; i < this.inputData.getTeachers().size(); i++) {
                    Vector<Integer> classes = this.getClassBySlot(this.inputData.getClasses(), j);
//                int cnt = 0;
                    for (int classId : classes) {
                        int u = this.inputData.getTeachers().size() + i * slots.size() + j;
                        int v = this.inputData.getTeachers().size() * (slots.size() + 1) + classId;
                        if (flow[u][v] > 0) {
                            res.add(this.inputData.getClasses().get(classId));
                            cnt++;
                        }
                    }
                }
            }
        }
        return res; //ok
    }

    public Vector<Class> getMaximumClass() {

        Vector<Class> res = new Vector<>();
        int l = 0;
        int r = 2000; //maximum class

        int maxNumberOfClass = 0;
        while (l <= r) {
            int g = (l + r) / 2;
              if (check(g)) {
                maxNumberOfClass = g;
                l = g + 1;
            } else {
                r = g - 1;
            }
        }
        res = getPossibleClasses(maxNumberOfClass);
        return res;
    }
}
