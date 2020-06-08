package com.fpt.edu.schedule.ai.model;

import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.lib.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Data
@Component
@NoArgsConstructor
public class InputData {
    private Vector<Teacher> teachers;
    private Vector<SlotGroup> slots;
    private Vector<Subject> subjects;
    private Vector<Class> classes;
    private double registeredSlots[][];
    private double registeredSubjects[][];
    GaParameter gaParameter;

//    public Model(Vector<Teacher> teachers, Vector<SlotGroup> slots, Vector<Subject> subjects, Vector<Class> classes,
//                 double[][] registeredSlots, double[][] registeredSubjects) {
//        this.teachers = teachers;
//        this.slots = slots;
//        this.subjects = subjects;
//        this.classes = classes;
//        this.registeredSlots = registeredSlots;
//        this.registeredSubjects = registeredSubjects;
//
//        mappingId();
//    }

    public InputData(Vector<Teacher> teachers, Vector<SlotGroup> slots, Vector<Subject> subjects, Vector<Class> classes,
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
            try {
                int teacherId = teacherIdMapping.get(es.getTeacherId());
                int subjectId = subjectIdMapping.get(es.getSubjectId());
                this.registeredSubjects[teacherId][subjectId] = es.getLevelOfPreference();
            } catch (Exception e) {
                // online subjects have been excluded
            }
        }

        checkResource();
    }

    public void checkResource() {
        ResourceChecker resourceChecker = new ResourceChecker(this);
        Vector<Class> possibleClasses = resourceChecker.getMaximumClass();
        for (Class _class : this.getClasses()) {
            _class.setStatus(Class.NOT_OK);
        }
        for (Class _class : possibleClasses) {
            this.getClasses().get(_class.getId()).setStatus(Class.OK);
        }
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
        for (int i = 0; i < slots.size(); i++) {
//            slots.get(i).setId(i);
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
        for (SlotGroup sg : this.slots) {
            for (Slot sl : sg.getSlots()) {
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

}
