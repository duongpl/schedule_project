package com.fpt.edu.schedule.ai.model;

import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.data.DataReader;
import com.fpt.edu.schedule.ai.lib.Slot;
import com.fpt.edu.schedule.ai.lib.SlotGroup;
import com.fpt.edu.schedule.ai.lib.Subject;
import com.fpt.edu.schedule.ai.lib.Teacher;

import java.util.Vector;

public class Model {

    public Vector<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Vector<Teacher> teachers) {
        this.teachers = teachers;
    }

    public Vector<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Vector<Subject> subjects) {
        this.subjects = subjects;
    }

    public Vector<Class> getClasses() {
        return classes;
    }

    public void setClasses(Vector<Class> classes) {
        this.classes = classes;
    }

    public double[][] getRegisteredSlots() {
        return registeredSlots;
    }

    public void setRegisteredSlots(double[][] registeredSlots) {
        this.registeredSlots = registeredSlots;
    }

    public double[][] getRegisteredSubjects() {
        return registeredSubjects;
    }

    public void setRegisteredSubjects(double[][] registeredSubjects) {
        this.registeredSubjects = registeredSubjects;
    }

    private Vector<Teacher> teachers;

    public Vector<SlotGroup> getSlots() {
        return slots;
    }

    public void setSlots(Vector<SlotGroup> slots) {
        this.slots = slots;
    }

    public int[] getExpectedNumberOfClass() {
        return expectedNumberOfClass;
    }

    public void setExpectedNumberOfClass(int[] expectedNumberOfClass) {
        this.expectedNumberOfClass = expectedNumberOfClass;
    }

    public int[] getConsecutiveSlotLimit() {
        return consecutiveSlotLimit;
    }

    public void setConsecutiveSlotLimit(int[] consecutiveSlotLimit) {
        this.consecutiveSlotLimit = consecutiveSlotLimit;
    }

    private Vector<SlotGroup> slots;
    private Vector<Subject> subjects;
    private Vector<Class> classes;
    private double registeredSlots[][];
    private double registeredSubjects[][];
    private int expectedNumberOfClass[];
    private int consecutiveSlotLimit[];
    private int quota[];

    public int[] getQuota() {
        return quota;
    }

    public void setQuota(int[] quota) {
        this.quota = quota;
    }

    public Model(Vector<Teacher> teachers, Vector<SlotGroup> slots, Vector<Subject> subjects, Vector<Class> classes,
                 double[][] registeredSlots, double[][] registeredSubjects, int[] expectedNumberOfClass, int[] consecutiveSlotLimit, int[] quota) {
        this.teachers = teachers;
        this.slots = slots;
        this.subjects = subjects;
        this.classes = classes;
        this.registeredSlots = registeredSlots;
        this.registeredSubjects = registeredSubjects;
        this.expectedNumberOfClass = expectedNumberOfClass;
        this.consecutiveSlotLimit = consecutiveSlotLimit;
        this.quota = quota;
    }
//    public Model(Vector<Teacher> teachers, Vector<SlotGroup> slots, Vector<Subject> subjects, Vector<Class> classes,
//                 double[][] registeredSlots, double[][] registeredSubjects) {
//        this.teachers = teachers;
//        this.slots = slots;
//        this.subjects = subjects;
//        this.classes = classes;
//        this.registeredSlots = registeredSlots;
//        this.registeredSubjects = registeredSubjects;
//
//    }

    public Model() {
    }

    public static void main(String[] args) {
        Vector<Teacher> teachers = new Vector<>();
//        teachers.add(new Teacher("E1", "E1", 0));
//        teachers.add(new Teacher("E2", "E1", 1));
//        teachers.add(new Teacher("E3", "E1", 2));
//        teachers.add(new Teacher("E4", "E1", 3));

        String registerPath = "C:\\Users\\ahcl\\Documents\\python\\test_keras\\schedule\\register1.csv";
        String sumaryPath = "C:\\Users\\ahcl\\Documents\\python\\test_keras\\schedule\\summary.csv";
        teachers = DataReader.getTeachersData(registerPath);
        System.out.println(teachers.size());
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
        double [][] registeredSlots = DataReader.getRegisteredSlot(teachers, slotList, registerPath);
        Vector<Subject> subjects = new Vector<>();
//        subjects.add(new Subject("sub0", 0));
//        subjects.add(new Subject("sub1", 1));
        subjects = DataReader.getSubjects(registerPath);
        System.out.println(subjects.size());

        double [][] registeredSubjects = DataReader.getRegisteredSubject(teachers, subjects, registerPath);
//        registeredSubjects[1][0] = registeredSubjects[2][1] = registeredSubjects[3][0] = registeredSubjects[3][1] = true;
        Vector<Class> classes = new Vector<>();

//        classes.add(new Class("s1", m246.getSlots().get(0), subjects.get(0), new Room("asdf"), 0));
//        classes.add(new Class("s1", m246.getSlots().get(0), subjects.get(1), new Room("asdf"), 1));
//        classes.add(new Class("s1", m246.getSlots().get(0), subjects.get(0), new Room("asdf"), 2));
        classes = DataReader.getClasses(slotList, subjects, sumaryPath);
        System.out.println(classes.size());
        System.out.println(classes.get(0).getStudentGroup() + " " + classes.get(0).getSlot().getName() + " " + classes.get(0).getSubject().getName());


//        Model model = new Model(teachers, slots, subjects, classes, registeredSlots, registeredSubjects);
//        GeneticAlgorithm ga = new GeneticAlgorithm(model);
//        ga.start();

    }
}
