package com.fpt.edu.schedule.testModel;

import com.fpt.edu.schedule.ai.lib.*;
import com.fpt.edu.schedule.ai.lib.Class;
import com.fpt.edu.schedule.ai.model.Chromosome;
import com.fpt.edu.schedule.ai.model.GaParameter;
import com.fpt.edu.schedule.ai.model.InputData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Vector;

public class TestChromosome {
    @Test
    public void constructorAChromosomeShouldCreateATimetable() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 4;
        InputData inputData = DataGenerator.generateModel(teacherNumber, subjectNumber, classNumber);
        inputData.getClasses().get(0).setSlotId(0);
        inputData.getClasses().get(2).setSlotId(0);
        inputData.getClasses().get(1).setSlotId(1);
        inputData.getClasses().get(3).setSlotId(1);
        Chromosome chromosome = new Chromosome(inputData);
        Collections.sort(chromosome.getGenes().get(0));
        Collections.sort(chromosome.getGenes().get(1));
        Integer expected0[] = {-1, -1, 0, 2};
        Integer expected1[] = {-1, -1, 1, 3};
        Assertions.assertArrayEquals(expected0, chromosome.getGenes().get(0).toArray());
        Assertions.assertArrayEquals(expected1, chromosome.getGenes().get(1).toArray());
    }

    @Test
    public void constructorAChromosomeShouldNotIncludeNotOkClass() {
        int teacherNumber = 4;
        int subjectNumber = 4;
        int classNumber = 5;
        Vector<Teacher> teachers = DataGenerator.generateTeachers(teacherNumber);
        Vector<Subject> subjects = DataGenerator.generateSubject(subjectNumber);
        Vector<SlotGroup> slots = DataGenerator.generateSlots();
        Vector<Class> classes = new Vector<>();
        Vector<ExpectedSlot> expectedSlots = new Vector<>();
        expectedSlots.add(new ExpectedSlot(0, 0, 5));
        expectedSlots.add(new ExpectedSlot(1, 1, 5));
        expectedSlots.add(new ExpectedSlot(2, 2, 5));
        expectedSlots.add(new ExpectedSlot(3, 0, 5));
        Vector<ExpectedSubject> expectedSubjects = DataGenerator.generateExpectedSubjectWithMaximumPreference(teachers, subjects);
        classes.add(new Class("C1", 0, 0, new Room("a", 1, "AL"), 0, 0));
        classes.add(new Class("C2", 1, 0, new Room("a", 1, "AL"), 1, 0));
        classes.add(new Class("C3", 2, 0, new Room("a", 1, "AL"), 2, 0));
        classes.add(new Class("C4", 3, 0, new Room("a", 1, "AL"), 3, 0));
        classes.add(new Class("C5", 0, 0, new Room("a", 1, "AL"), 4, 0));
        InputData inputData = new InputData(teachers, slots, subjects, classes, expectedSlots, expectedSubjects, new GaParameter());
        Chromosome chromosome = new Chromosome(inputData);
        Collections.sort(chromosome.getGenes().get(0));
        Collections.sort(chromosome.getGenes().get(1));
        Collections.sort(chromosome.getGenes().get(2));
        Collections.sort(chromosome.getGenes().get(3));

        Integer expected0[] = {-1, -1, 0, 4};
        Integer expected1[] = {-1, -1, -1, 1};
        Integer expected2[] = {-1, -1, -1, 2};
        Integer expected3[] = {-1, -1, -1, -1};
        Assertions.assertArrayEquals(expected0, chromosome.getGenes().get(0).toArray());
        Assertions.assertArrayEquals(expected1, chromosome.getGenes().get(1).toArray());
        Assertions.assertArrayEquals(expected2, chromosome.getGenes().get(2).toArray());
        Assertions.assertArrayEquals(expected3, chromosome.getGenes().get(3).toArray());

    }
}
