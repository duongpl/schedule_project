package com.fpt.edu.schedule.testModel;

import com.fpt.edu.schedule.ai.lib.*;
import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.model.GaParameter;
import com.fpt.edu.schedule.ai.model.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Vector;

public class TestModel {
    @Test
    public void teacherIdAfterIdMappingShouldBeContinuous() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Class> classes = DataGenerator.generateClassesWithIdRandom(subjects, slots, classNumber);
        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        for (int i = 0; i < teachers.size(); i++) {
            Assertions.assertEquals(i, model.getTeachers().get(i).getId());
        }
    }

    @Test
    public void subjectIdAfterIdMappingShouldBeContinuous() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Class> classes = DataGenerator.generateClassesWithIdRandom(subjects, slots, classNumber);
        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        for (int i = 0; i < subjects.size(); i++) {
            Assertions.assertEquals(i, model.getSubjects().get(i).getId());
        }
    }

    @Test
    public void slotIdAfterIdMappingShouldBeContinuous() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Class> classes = DataGenerator.generateClassesWithIdRandom(subjects, slots, classNumber);
        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        Vector<Slot> slotList = SlotGroup.getSlotList(model.getSlots());
        for (int i = 0; i < slotList.size(); i++) {
            Assertions.assertEquals(i, slotList.get(i).getId());
        }
    }

    @Test
    public void classIdAfterIdMappingShouldBeContinuous() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Class> classes = DataGenerator.generateClassesWithIdRandom(subjects, slots, classNumber);
        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        Vector<Slot> slotList = SlotGroup.getSlotList(model.getSlots());
        for (int i = 0; i < classes.size(); i++) {
            Assertions.assertEquals(i, model.getClasses().get(i).getId());
        }
    }

    @Test
    public void ExpectedSlotMatrixAfterIdMappingShouldBeCorrect() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Class> classes = DataGenerator.generateClassesWithIdRandom(subjects, slots, classNumber);
        Vector<ExpectedSlot> expectedSlots = new Vector<>();
        Vector<Slot> slotList = SlotGroup.getSlotList(slots);

        expectedSlots.add(new ExpectedSlot(teachers.get(0).getId(), slotList.get(4).getId(), 3));
        expectedSlots.add(new ExpectedSlot(teachers.get(1).getId(), slotList.get(7).getId(), 5));

        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        for (int i = 0; i < classes.size(); i++) {
            Assertions.assertEquals(i, model.getClasses().get(i).getId());
        }
        double [][] expectedSlotMatrix = model.getRegisteredSlots();
        Assertions.assertEquals(3, expectedSlotMatrix[0][4], 1e-8);
        Assertions.assertEquals(5, expectedSlotMatrix[1][7], 1e-8);
        Assertions.assertEquals(0, expectedSlotMatrix[0][0], 1e-8);
    }

    @Test
    public void ExpectedSubjectMatrixAfterIdMappingShouldBeCorrect() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Class> classes = DataGenerator.generateClassesWithIdRandom(subjects, slots, classNumber);
        Vector<ExpectedSubject> expectedSubjects = new Vector<>();
        Vector<Slot> slotList = SlotGroup.getSlotList(slots);
        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        expectedSubjects.add(new ExpectedSubject(teachers.get(0).getId(), subjects.get(2).getId(), 1));
        expectedSubjects.add(new ExpectedSubject(teachers.get(1).getId(), subjects.get(3).getId(), 4));
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        double [][] expectedSubjectMatrix = model.getRegisteredSubjects();
        Assertions.assertEquals(1, expectedSubjectMatrix[0][2], 1e-8);
        Assertions.assertEquals(4, expectedSubjectMatrix[1][3], 1e-8);
        Assertions.assertEquals(0, expectedSubjectMatrix[0][0], 1e-8);
    }
    @Test
    public void slotIdOfEachClassAfterIdMappingShouldBeMapped() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Slot> slotList = SlotGroup.getSlotList(slots);

        //        Vector<Class> classes = DataGenerator.generateClassesWithIdRandom(subjects, slots, classNumber);

        Vector<Class> classes = new Vector<>();
        classes.add(new Class("c1", slotList.get(3).getId(), subjects.get(2).getId(), new Room("AL123", 1, "AL"), 123, 1));
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        Assertions.assertEquals(3, model.getClasses().get(0).getSlotId());
        Assertions.assertEquals(2, model.getClasses().get(0).getSubjectId());
    }
    @Test
    public void idReverseShouldReturnOriginId() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);

        int teacherId = teachers.get(0).getId();
        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        int subjectId = subjects.get(0).getId();
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Slot> slotList = SlotGroup.getSlotList(slots);

        int slotId = slotList.get(0).getId();
        Vector<Class> classes = DataGenerator.generateClassesWithIdRandom(subjects, slots, classNumber);
        int classId = classes.get(0).getId();
        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        Assertions.assertEquals(teacherId, model.getTeacherIdReverse(0));
        Assertions.assertEquals(subjectId, model.getSubjectIdReverse(0));
        Assertions.assertEquals(slotId, model.getSlotIdReverse(0));
        Assertions.assertEquals(classId, model.getClassIdReverse(0));
    }
    @Test
    public void checkResourceShouldMarkStatusForClasses() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);

        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Slot> slotList = SlotGroup.getSlotList(slots);

        Vector<Class> classes = new Vector<>();
        classes.add(new Class("c1", slotList.get(0).getId(), subjects.get(0).getId(), new Room("aAL123", 1, "AL"), 0, 0));
        classes.add(new Class("c2", slotList.get(0).getId(), subjects.get(1).getId(), new Room("aAL123", 1, "AL"), 1, 0));
        classes.add(new Class("c3", slotList.get(0).getId(), subjects.get(2).getId(), new Room("aAL123", 1, "AL"), 2, 0));
        classes.add(new Class("c4", slotList.get(0).getId(), subjects.get(3).getId(), new Room("aAL123", 1, "AL"), 3, 0));
        classes.add(new Class("c5", slotList.get(0).getId(), subjects.get(0).getId(), new Room("aAL123", 1, "AL"), 4, 0));

        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());

        int expectedMaximumNumberOfClass = 4;
        int actualNumberOfClass = 0;
        for(Class _class:model.getClasses()) {
            if (_class.getStatus() == Class.OK) {
                actualNumberOfClass ++;
            }
        }
        Assertions.assertEquals(expectedMaximumNumberOfClass, actualNumberOfClass);
    }

    @Test
    public void checkResourceForNoResourceShouldReturnAllNotOkClass() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 10;

        Vector<Teacher> teachers = DataGenerator.generateTeachersWithIdRandom(teacherNumber);

        Vector<Subject> subjects = DataGenerator.generateSubjectWithIdRandom(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlotsWithIdRandom();
        Vector<Slot> slotList = SlotGroup.getSlotList(slots);

        Vector<Class> classes = new Vector<>();
        classes.add(new Class("c1", slotList.get(0).getId(), subjects.get(0).getId(), new Room("aAL123", 1, "AL"), 0, 0));
        classes.add(new Class("c2", slotList.get(0).getId(), subjects.get(1).getId(), new Room("aAL123", 1, "AL"), 1, 0));
        classes.add(new Class("c3", slotList.get(0).getId(), subjects.get(2).getId(), new Room("aAL123", 1, "AL"), 2, 0));
        classes.add(new Class("c4", slotList.get(0).getId(), subjects.get(3).getId(), new Room("aAL123", 1, "AL"), 3, 0));
        classes.add(new Class("c5", slotList.get(0).getId(), subjects.get(0).getId(), new Room("aAL123", 1, "AL"), 4, 0));

        Vector<ExpectedSlot> expectedSlots = DataGenerator.generateExpectedSlotWithMaximumPreference(teachers, slots);
        for(ExpectedSlot expectedSlot:expectedSlots) {
            expectedSlot.setLevelOfPreference(0);
        }
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        Model model = new Model(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());

        int expectedMaximumNumberOfClass = 0;
        int actualNumberOfClass = 0;
        for(Class _class:model.getClasses()) {
            if (_class.getStatus() == Class.OK) {
                actualNumberOfClass ++;
            }
        }
        Assertions.assertEquals(expectedMaximumNumberOfClass, actualNumberOfClass);
    }
}
