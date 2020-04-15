package com.fpt.edu.schedule.ai.data;


import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.lib.*;
import com.fpt.edu.schedule.ai.model.Model;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

public class DataGenerator {
    public static Model Gendata(int m, int k, int n) {
        Vector<Teacher> teachers = new Vector<>();
        Vector<Subject> subjects = new Vector();
        Random random = new Random(5);

        for(int i = 0 ; i < m ; i ++) {
            teachers.add(new Teacher("t" + i, "t" + i, i, (random.nextInt(3) >= 1) ? Teacher.FULL_TIME : Teacher.PART_TIME));
        }
        for(int i = 0 ; i < k; i++) {
            subjects.add(new Subject("sj" + i, i));
        }
        Vector<SlotGroup> slots = new Vector<>();
        SlotGroup m246 = new SlotGroup(3);
        m246.addSlot(new Slot("M1", 0));
        m246.addSlot(new Slot("M2", 1));
        m246.addSlot(new Slot("M3", 2));
        SlotGroup e246 = new SlotGroup(3);

        e246.addSlot(new Slot("E1", 3));
        e246.addSlot(new Slot("E2", 4));
        e246.addSlot(new Slot("E3", 5));

        SlotGroup m35 = new SlotGroup(1);

        m35.addSlot(new Slot("M4", 6));
        m35.addSlot(new Slot("M5", 7));

        SlotGroup e35 = new SlotGroup(1);

        e35.addSlot(new Slot("E4", 8));
        e35.addSlot(new Slot("E5", 9));
        slots.add(m246);
        slots.add(e246);
        slots.add(m35);
        slots.add(e35);


        Vector<Slot> slotList = SlotGroup.getSlotList(slots);
        int ssCnt = slotList.size();


        Vector<Class> classes = new Vector<Class>();
        int slotId = 0;
        for(int i = 0; i < n; i++) {
            String building = (random.nextInt(2) == 0) ? "A" :"B";
            classes.add(new Class("cl" + i, slotList.get(slotId++), subjects.get(random.nextInt(subjects.size())), new Room(building+"-"+i / 10, 0, building), i));
            slotId %= 10;
        }
//        for(int i = 0; i < 100; i++) {
//            int x = random.nextInt(n);
//            int y = random.nextInt(n);
//            Slot tmp = classes.get(x).getSlot();
//            classes.get(x).setSlot(classes.get(y).getSlot());
//            classes.get(y).setSlot(tmp);
//        }

        double registerdSlot[][] = new double[teachers.size()][slotList.size()];
        for(double a[]:registerdSlot) {
            Arrays.fill(a, 5);
//            for(int i = 0 ; i < 10; i++) {
//                a[i] = random.nextInt(5) + 1;
//            }
        }

        for(int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getType() == Teacher.PART_TIME) {
                Arrays.fill(registerdSlot[i], 0);
                int type = random.nextInt(4);
                if (type == 0) {
                    registerdSlot[i][0] = registerdSlot[i][1] = registerdSlot[i][2] = 5;
                }
                if (type == 1) {
                    registerdSlot[i][4] = registerdSlot[i][5] = registerdSlot[i][3] = 5;
                }
                if (type == 2) {
                    registerdSlot[i][7] = registerdSlot[i][6] = 5;
                }
                if (type == 3) {
                    registerdSlot[i][8] = registerdSlot[i][9]= 5;
                }
            }
        }

        for(int i = 0; i < teachers.size(); i++) {
            for(int j =0 ; j < 10; j++) {
                System.out.print(registerdSlot[i][j] + " ");
            }
            System.out.println();
        }

        double registerdSubject[][] = new double[teachers.size()][subjects.size()];
        for(double a[]:registerdSubject) {
            Arrays.fill(a, 5);
//            for(int i = 0; i < subjects.size(); i++) {
//                a[i] = random.nextInt(5) + 1;
//            }
        }


        int expectedNumberOfClass[] = new int[teachers.size()];
        int consecutiveSlotLimit[] = new int[teachers.size()];
        Arrays.fill(consecutiveSlotLimit, 10);
        for(int i =0 ;i  <teachers.size(); i++) {
            if (teachers.get(i).getType() == Teacher.FULL_TIME) expectedNumberOfClass[i] = 7 + random.nextInt(4);
            else expectedNumberOfClass[i] = random.nextInt(3) + 1;
        }

        return new Model(teachers, slots, subjects, classes, registerdSlot, registerdSubject, expectedNumberOfClass, consecutiveSlotLimit, new int[teachers.size()]);
    };
}
