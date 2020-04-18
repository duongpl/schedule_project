package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.ai.lib.Slot;
import com.fpt.edu.schedule.ai.lib.SlotGroup;
import com.fpt.edu.schedule.ai.model.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Vector;

@Component
@Scope(scopeName = "prototype")
public class StartService {
    public static final int POPULATION_SIZE = 1000;
    public static final double MUTATION_RATE = 0.25;
    public static final int TOURNAMENT_SIZE = 3;
    public static final int CLASS_NUMBER = 5;
    public static final double IN_CLASS_RATE = 0.9;
    public void updateFitness(Train train, int generation, Population population) {
        population.updateFitness();
        generation++;

        System.out.println("Fitness Average: " + population.getAverageFitness());
        System.out.println("Best fitness: " + population.getBestIndividuals().getFitness());
        System.out.println("Generation: " + generation);

        train.notify(population.getBestIndividuals(), population.getBestIndividuals().getFitness(), population.getAverageFitness(),
                population.getBestIndividuals().getNumberOfViolation());
    }

    public Population selection1(Model model, Population population) {
        Population population1 = new Population(model);
        for (int i = 0; i < population.getSize() / 2; i++) {
            Chromosome p1 = selectParent(population);
            Chromosome p2 = selectParent(population);
            Chromosome c1 = this.crossover(p1, p2, model);
            Chromosome c2 = this.crossover(p2, p1, model);
            population1.addIndividual(c1);
            population1.addIndividual(c2);
        }
        return population;
    }

    public Chromosome crossover(Chromosome c1, Chromosome c2, Model model) {
        Vector<Slot> slots = SlotGroup.getSlotList(model.getSlots());
        Vector<Vector<Integer>> genes = new Vector<>();
        Random random = new Random();
        for (Slot slot : slots) {
            Vector<Integer> p1 = c1.getGenes().get(slot.getId());
            Vector<Integer> p2 = c2.getGenes().get(slot.getId());
            Vector<Integer> p3 = (new PMX(p1, p2, random.nextInt(Integer.MAX_VALUE))).getChildren();

            genes.add(p3);
        }

        return new Chromosome(model, genes);
    }

    public Chromosome selectParent(Population population) {
        Random random = new Random();
        Vector<Chromosome> candidates = new Vector<>();
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int idx = random.nextInt(population.getSize());
            candidates.add(population.getIndividuals().get(idx));
        }

        double best = candidates.get(0).getFitness();
        Chromosome res = candidates.get(0);
        for (Chromosome chromosome : candidates) {
            if (chromosome.getFitness() > best) {
                best = chromosome.getFitness();
                res = chromosome;
            }
        }
        return res;
    }

    public void mutate(Population population) {
        Random random = new Random();
        for (int i = 0; i < population.getSize(); i++) {
            for (int j = 0; j < 1; j++) {
                if (random.nextDouble() < MUTATION_RATE) {
                    population.getIndividuals().get(i).mutate();
                }
            }
            population.getIndividuals().get(i).autoRepair();
        }
    }

    public void start(Model model) {
        Population population = new Population(POPULATION_SIZE, model);
        Train train = new Train();
        int generation =0;
        while (true) {
            updateFitness(train, generation, population);
            population = selection1(model, population);
            mutate(population);
        }
    }

}
