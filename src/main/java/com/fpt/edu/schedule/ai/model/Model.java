package com.fpt.edu.schedule.ai.model;

import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.lib.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Data
@Component
public class Model {
    private Vector<Teacher> teachers;
    private Vector<SlotGroup> slots;
    private Vector<Subject> subjects;
    private Vector<Class> classes;
    private double registeredSlots[][];
    private double registeredSubjects[][];
    GaParameter gaParameter;
    public Model(Vector<Teacher> teachers, Vector<SlotGroup> slots, Vector<Subject> subjects, Vector<Class> classes,
                 double[][] registeredSlots, double[][] registeredSubjects) {
        this.teachers = teachers;
        this.slots = slots;
        this.subjects = subjects;
        this.classes = classes;
        this.registeredSlots = registeredSlots;
        this.registeredSubjects = registeredSubjects;

        mappingId();
    }

    public Model(Vector<Teacher> teachers, Vector<SlotGroup> slots, Vector<Subject> subjects, Vector<Class> classes,
                 Vector<ExpectedSlot> registeredSlots, Vector<ExpectedSubject> registeredSubjects, GaParameter gaParameter) {
        this.teachers = teachers;
        this.slots = slots;
        this.subjects = subjects;
        this.classes = classes;
        this.registeredSlots = new double[teachers.size()][10];
        this.registeredSubjects = new double[teachers.size()][subjects.size()];
        this.gaParameter = gaParameter;

        mappingId();


        for (ExpectedSlot es : registeredSlots) {
            int teacherId = teacherIdMapping.get(es.getTeacherId());
            int slotId = slotIdMapping.get(es.getSlotId());
            this.registeredSlots[teacherId][slotId] = es.getLevelOfPreference();
        }

        for (ExpectedSubject es : registeredSubjects) {
            int teacherId = teacherIdMapping.get(es.getTeacherId());
            int subjectId = subjectIdMapping.get(es.getSubjectId());
            this.registeredSubjects[teacherId][subjectId] = es.getLevelOfPreference();
        }

//        for(int i=0 ;i < teachers.size();i ++) {
//            System.out.print(this.teachers.get(i).getEmail() + " ");
//            for(int j =0 ; j < 10;j ++) {
//                System.out.print(this.registeredSlots[i][j] + " ");
//            }
//            System.out.println();
//        }

//
    }

    Map<Integer, Integer> teacherIdMapping;
    Map<Integer, Integer> classIdMapping;
    Map<Integer, Integer> subjectIdMapping;
    Map<Integer, Integer> slotIdMapping;


    Map<Integer, Integer> teacherIdMappingReverse;
    Map<Integer, Integer> classIdMappingReverse;
    Map<Integer, Integer> subjectIdMappingReverse;
    Map<Integer, Integer> slotIdMappingReverse;


    public void mappingId() {
        teacherIdMapping = new HashMap<>();
        classIdMapping = new HashMap<>();
        subjectIdMapping = new HashMap<>();
        teacherIdMappingReverse = new HashMap<>();
        classIdMappingReverse = new HashMap<>();
        subjectIdMappingReverse = new HashMap<>();
        slotIdMapping = new HashMap<>();
        slotIdMappingReverse = new HashMap<>();
        for (int i = 0; i < teachers.size(); i++) {
            teacherIdMapping.put(teachers.get(i).getId(), i);
            teacherIdMappingReverse.put(i, teachers.get(i).getId());
        }

        for (int i = 0; i < classes.size(); i++) {
            classIdMapping.put(classes.get(i).getId(), i);
            classIdMappingReverse.put(i, classes.get(i).getId());
        }

        for (int i = 0; i < subjects.size(); i++) {
            subjectIdMapping.put(subjects.get(i).getId(), i);
            subjectIdMappingReverse.put(i, subjects.get(i).getId());
        }

        Vector<Slot> slots = SlotGroup.getSlotList(this.slots);
        for(int i = 0; i < slots.size(); i++) {
            slotIdMapping.put(slots.get(i).getId(), i);
            slotIdMappingReverse.put(i, slots.get(i).getId());
        }


        for (int i = 0; i < teachers.size(); i++) {
            teachers.get(i).setId(teacherIdMapping.get(teachers.get(i).getId()));
        }

        for (int i = 0; i < subjects.size(); i++) {
            subjects.get(i).setId(subjectIdMapping.get(subjects.get(i).getId()));
        }

        for (int i = 0; i < classes.size(); i++) {
            classes.get(i).setId(classIdMapping.get(classes.get(i).getId()));
            classes.get(i).setSubjectId(subjectIdMapping.get(classes.get(i).getSubjectId()));
            classes.get(i).setSlotId(slotIdMapping.get(classes.get(i).getSlotId()));

        }

        int id = 0;
        for(SlotGroup sg:this.slots) {
            for(Slot sl:sg.getSlots()) {
                sl.setId(id++);
            }
        }
    }

    public int getTeacherIdReverse(int teacherID) {
        return teacherIdMappingReverse.get(teacherID);
    }

    public int getClassIdReverse(int classId) {
        return classIdMappingReverse.get(classId);
    }

    public int getSubjectIdReverse(int subjectId) {
        return subjectIdMappingReverse.get(subjectId);
    }

    public int getSlotIdReverse(int slotId) {
        return slotIdMappingReverse.get(slotId);
    }


    public Model() {
    }

    public static void main(String[] args) {
        Vector<Teacher> teachers = new Vector<>();
        teachers.add(new Teacher("asdf", "asdf", 3, Teacher.FULL_TIME));
        teachers.add(new Teacher("asdf", "asdf", 5, Teacher.FULL_TIME));

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
        Vector<ExpectedSlot> registerSlot = new Vector<>();
        registerSlot.add(new ExpectedSlot(3, 1, 5));
        registerSlot.add(new ExpectedSlot(5, 2, 3));
        Vector<Subject> subjects = new Vector<>();
        subjects.add(new Subject("asdf", 7));
        subjects.add(new Subject("asdf", 10));
        Vector<ExpectedSubject> registerSubject = new Vector<>();
        registerSubject.add(new ExpectedSubject(3, 7, 5));
        registerSubject.add(new ExpectedSubject(5, 10, 2));
        Vector<Class> classes = new Vector<>();

        classes.add(new Class("s1", 1, 7, new Room("asdf"), 0));
        classes.add(new Class("s1", 2, 10, new Room("asdf"), 1));
        classes.add(new Class("s1", 1, 7, new Room("asdf"), 2));
        GaParameter gaParameter = new GaParameter();
        Model model = new Model(teachers, slots, subjects, classes, registerSlot, registerSubject, gaParameter);

    }
}
