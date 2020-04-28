package com.fpt.edu.schedule.testModel;

import com.fpt.edu.schedule.ai.lib.*;
import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.model.GaParameter;
import com.fpt.edu.schedule.ai.model.Model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class DataGenerator {
    public static Vector<Teacher> generateTeachers(int teacherNumber) {
        Random random = new Random();
        Vector<Teacher> res = new Vector<>();
        for (int i = 0; i < teacherNumber; i++) {
            String name = "teacher" + i;
            String email = "teacher" + i + "@gmail.com";
            int type = random.nextInt(2);
            Teacher teacher = new Teacher(email, name, i, type);
            res.add(teacher);
        }
        return res;
    }

    public static Vector<Teacher> generateTeachersWithIdRandom(int teacherNumber) {
        Random random = new Random();
        Vector<Teacher> res = new Vector<>();
        Set<Integer> used = new HashSet<>();
        Vector<Integer> ids = new Vector<>();
        for (int i = 0; i < teacherNumber; i++) {
            int x = random.nextInt(Integer.MAX_VALUE);
            while (used.contains(x)) {
                x = random.nextInt(Integer.MAX_VALUE);
            }
            used.add(x);
            ids.add(x);
        }
        for (int i = 0; i < teacherNumber; i++) {
            String name = "teacher" + i;
            String email = "teacher" + i + "@gmail.com";
            int type = random.nextInt(2);
            Teacher teacher = new Teacher(email, name, ids.get(i), type);
            res.add(teacher);
        }
        return res;
    }

    public static Vector<Subject> generateSubject(int subjectNumber) {
        Vector<Subject> res = new Vector<>();
        for (int i = 0; i < subjectNumber; i++) {
            String name = "subject" + i;
            Subject subject = new Subject(name, i);
            res.add(subject);
        }
        return res;
    }

    public static Vector<Subject> generateSubjectWithIdRandom(int subjectNumber) {
        Vector<Subject> res = new Vector<>();
        Random random = new Random();
        Set<Integer> used = new HashSet<>();
        Vector<Integer> ids = new Vector<>();
        for (int i = 0; i < subjectNumber; i++) {
            int x = random.nextInt(Integer.MAX_VALUE);
            while (used.contains(x)) {
                x = random.nextInt(Integer.MAX_VALUE);
            }
            used.add(x);
            ids.add(x);
        }
        for (int i = 0; i < subjectNumber; i++) {
            String name = "subject" + i;
            Subject subject = new Subject(name, ids.get(i));
            res.add(subject);
        }
        return res;
    }

    public static Vector<SlotGroup> generateSlots() {
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
        return slots;
    }

    public static Vector<SlotGroup> generateSlotsWithIdRandom() {

        Set<Integer> used = new HashSet<>();
        Vector<Integer> ids = new Vector<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(Integer.MAX_VALUE);
            while (used.contains(x)) {
                x = random.nextInt(Integer.MAX_VALUE);
            }
            used.add(x);
            ids.add(x);
        }

        Vector<SlotGroup> slots = new Vector<>();
        SlotGroup m246 = new SlotGroup(3);
        m246.addSlot(new Slot("M1", ids.get(0)));
        m246.addSlot(new Slot("M2", ids.get(1)));
        m246.addSlot(new Slot("M3", ids.get(2)));
        SlotGroup e246 = new SlotGroup(3);

        e246.addSlot(new Slot("E1", ids.get(3)));
        e246.addSlot(new Slot("E2", ids.get(4)));
        e246.addSlot(new Slot("E3", ids.get(5)));

        SlotGroup m35 = new SlotGroup(1);

        m35.addSlot(new Slot("M4", ids.get(6)));
        m35.addSlot(new Slot("M5", ids.get(7)));

        SlotGroup e35 = new SlotGroup(1);

        e35.addSlot(new Slot("E4", ids.get(8)));
        e35.addSlot(new Slot("E5", ids.get(9)));
        slots.add(m246);
        slots.add(e246);
        slots.add(m35);
        slots.add(e35);
        return slots;
    }

    public static Vector<Class> generateClasses(Vector<Subject> subjects, Vector<SlotGroup> slots, int classNumber) {
        Vector<Class> res = new Vector<>();
        Vector<Slot> slotlist = SlotGroup.getSlotList(slots);
        Random random = new Random();
        for (int i = 0; i < classNumber; i++) {
            String studentGroup = "c" + i;
            int slotId = slotlist.get(random.nextInt(slotlist.size())).getId();
            int subjectId = subjects.get(random.nextInt(subjects.size())).getId();
            Class _class = new Class(studentGroup, slotId, subjectId, new Room("AL-123", 1, "AL"), i, Class.OK);
            res.add(_class);
        }
        return res;
    }

    public static Vector<Class> generateClassesWithIdRandom(Vector<Subject> subjects, Vector<SlotGroup> slots, int classNumber) {
        Vector<Class> res = new Vector<>();
        Vector<Slot> slotlist = SlotGroup.getSlotList(slots);
        Random random = new Random();

        Set<Integer> used = new HashSet<>();
        Vector<Integer> ids = new Vector<>();
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(Integer.MAX_VALUE);
            while (used.contains(x)) {
                x = random.nextInt(Integer.MAX_VALUE);
            }
            used.add(x);
            ids.add(x);
        }
        for (int i = 0; i < classNumber; i++) {
            String studentGroup = "c" + i;
            int slotId = slotlist.get(random.nextInt(slotlist.size())).getId();
            int subjectId = subjects.get(random.nextInt(subjects.size())).getId();
            Class _class = new Class(studentGroup, slotId, subjectId, new Room("AL-123", 1, "AL"), ids.get(i), Class.OK);
            res.add(_class);
        }
        return res;
    }

    public static Vector<ExpectedSlot> generateExpectedSlotWithMaximumPreference(Vector<Teacher> teachers, Vector<SlotGroup> slots) {
        Vector<Slot> slotlist = SlotGroup.getSlotList(slots);
        Vector<ExpectedSlot> expectedSlots = new Vector<>();
        for (int i = 0; i < teachers.size(); i++) {
            for (int j = 0; j < slotlist.size(); j++) {
                ExpectedSlot expectedSlot = new ExpectedSlot(teachers.get(i).getId(), slotlist.get(j).getId(), 5);
                expectedSlots.add(expectedSlot);
            }
        }
        return expectedSlots;
    }
    public static Vector<ExpectedSubject> generateExpectedSubjectWithMaximumPreference(Vector<Teacher> teachers, Vector<Subject> subjects) {
        Vector<ExpectedSubject> expectedSubjects = new Vector<>();
        for (int i = 0; i < teachers.size(); i++) {
            for (int j = 0; j < subjects.size(); j++) {
                ExpectedSubject expectedSubject = new ExpectedSubject(teachers.get(i).getId(), subjects.get(j).getId(), 5);
                expectedSubjects.add(expectedSubject);
            }
        }
        return expectedSubjects;
    }

    public static Model generateModel(int teacherNumber, int subjectNumber, int classNumber) {
        Vector<Teacher> teachers = DataGenerator.generateTeachers(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubject(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlots();
        Vector<Class> classes = DataGenerator.generateClasses(subjects, slots, classNumber);
        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        return model;
    }
}
