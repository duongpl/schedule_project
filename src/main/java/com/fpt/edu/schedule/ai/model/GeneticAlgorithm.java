package com.fpt.edu.schedule.ai.model;


import com.fpt.edu.schedule.ai.data.DataWriter;
import com.fpt.edu.schedule.ai.lib.Slot;
import com.fpt.edu.schedule.ai.lib.SlotGroup;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class GeneticAlgorithm {
    public static final int POPULATION_SIZE = 1000;
    public static final double MUTATION_RATE = 0.25;
    public static final int TOURNAMENT_SIZE = 3;
    public static final int CLASS_NUMBER = 5;
    public static final double IN_CLASS_RATE = 0.9;
//        public static final double

    Population population;
    Model model;
    private int generation;
    private Train train;
    public GeneticAlgorithm(Model model, Train train) {
        this.generation = 0;
        this.model = model;
        this.train = train;
        this.population = new Population(POPULATION_SIZE, model);
    }
    public void updateFitness() {
        this.population.updateFitness();


        this.generation++;

        System.out.println("Fitness Average: " + this.population.getAverageFitness());
        System.out.println("Best fitness: "  + this.population.getBestIndividuals().getFitness());
        System.out.println("Generation: " + this.generation);

        this.train.notify(this.population.getBestIndividuals(), this.population.getBestIndividuals().getFitness(), this.population.getAverageFitness(),
                this.population.getBestIndividuals().getNumberOfViolation());
//        if (this.generation % 100 == 0) {
//            DataWriter.writeToCsv(this.model, this.population.getBestIndividuals(), "result" + this.generation / 100 + ".csv");
//        }
    }


public Chromosome selectParent() {
    Random random = new Random();
    Vector<Chromosome> candidates = new Vector<>();
    for(int i = 0; i < TOURNAMENT_SIZE; i++) {
        int idx = random.nextInt(POPULATION_SIZE);
        candidates.add(this.population.getIndividuals().get(idx));
    }

    double best = candidates.get(0).getFitness();
    Chromosome res = candidates.get(0);
    for(Chromosome chromosome:candidates) {
        if (chromosome.getFitness() > best) {
            best = chromosome.getFitness();
            res = chromosome;
        }
    }
    return res;
}

public Chromosome selectParentRandomly() {
    Random random = new Random();
    int idx = random.nextInt(POPULATION_SIZE);
    return this.population.getIndividuals().get(idx);
}

public Chromosome selectParent(Vector<Chromosome> individuals) {
    Random random = new Random();
    Vector<Chromosome> candidates = new Vector<>();
    for(int i = 0; i < TOURNAMENT_SIZE; i++) {
        int idx = random.nextInt(individuals.size());
        candidates.add(individuals.get(idx));
    }

    double best = candidates.get(0).getFitness();
    Chromosome res = candidates.get(0);
    for(Chromosome chromosome:candidates) {
        if (chromosome.getFitness() > best) {
            best = chromosome.getFitness();
            res = chromosome;
        }
    }
    return res;
}

public void selection1() {
    Population population1 = new Population(this.model);
    for(int i = 0 ; i < this.POPULATION_SIZE / 2 ; i++) {
        Chromosome p1 = selectParent();
        Chromosome p2 = selectParent();
        Chromosome c1 = this.crossover(p1, p2);
        Chromosome c2 = this.crossover(p2, p1);
        population1.addIndividual(c1);
        population1.addIndividual(c2);
    }
    this.population = population1;
}

public void selection() {
    Population population1 = new Population(this.model);

    this.population.sortByFitnetss();
    Vector<Vector<Chromosome>> individualsByClass = new Vector();
    for(int i = 0 ; i < CLASS_NUMBER; i++) {
        individualsByClass.add(new Vector());
    }


    int classSize = POPULATION_SIZE / CLASS_NUMBER + ((POPULATION_SIZE % CLASS_NUMBER == 0) ? 0 : 1);
    for(int i = 0 ; i < POPULATION_SIZE; i++) {
        int classId = i / classSize;
        individualsByClass.get(classId).add(this.population.getIndividuals().get(i));
    }

    for(int i = 0 ; i < CLASS_NUMBER; i++) {
        Collections.shuffle(individualsByClass.get(i));
    }


    int inClassPairNumber = (int) (POPULATION_SIZE * IN_CLASS_RATE /  CLASS_NUMBER / 2);
    for(int i = 0; i < CLASS_NUMBER; i++) {
        for(int j = 0 ; j < inClassPairNumber; j++) {
            Chromosome p1 = selectParent(individualsByClass.get(i));
            Chromosome p2 = selectParent(individualsByClass.get(i));
            Chromosome c1 = this.crossover(p1, p2);
            Chromosome c2 = this.crossover(p2, p1);
            population1.addIndividual(c1);
            population1.addIndividual(c2);
        }
    }



    while (population1.getSize() < POPULATION_SIZE) {
        Chromosome p1 = selectParentRandomly();
        Chromosome p2 = selectParentRandomly();
        Chromosome c1 = this.crossover(p1, p2);
        Chromosome c2 = this.crossover(p2, p1);
        population1.addIndividual(c1);
        population1.addIndividual(c2);
    }
    this.population = population1;
}
    public Chromosome crossover(Chromosome c1, Chromosome c2) {
        Vector<Slot> slots = SlotGroup.getSlotList(this.model.getSlots());
        Vector<Vector<Integer>> genes = new Vector<>();
        Random random = new Random();
        for(Slot slot:slots) {
            Vector<Integer> p1 = c1.getGenes().get(slot.getId());
            Vector<Integer> p2 = c2.getGenes().get(slot.getId());
            Vector<Integer> p3 = (new PMX(p1, p2, random.nextInt(Integer.MAX_VALUE))).getChildren();

            genes.add(p3);
        }

        return new Chromosome(this.model, genes);
    }
    public void mutate() {
    Random random = new Random();
    for(int i = 0; i < POPULATION_SIZE; i++) {
        for(int j = 0;  j < 1; j++) {
            if (random.nextDouble() < MUTATION_RATE) {
                this.population.getIndividuals().get(i).mutate();
            }
        }
        this.population.getIndividuals().get(i).autoRepair();
    }
}
public void start() {
    while (true) {
        System.out.println(1);
        this.updateFitness();
        System.out.println(1);
        this.selection1();
        System.out.println(1);
        this.mutate();
    }
}
}
