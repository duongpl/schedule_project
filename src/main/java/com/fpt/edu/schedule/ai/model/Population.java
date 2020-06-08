package com.fpt.edu.schedule.ai.model;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
@Component
public class Population {
    public Vector<Chromosome> getIndividuals() {
        return individuals;
    }

    public Population() {
    }


    private Vector<Chromosome> individuals;
    private int size;

    private InputData inputData;

    public Population(InputData inputData) {
        this.individuals = new Vector<>();
        this.size = 0;
        this.inputData = inputData;
    }

    public void addIndividual(Chromosome chromosome) {
        this.individuals.add(chromosome);
        this.size ++;
    }

    public void addIndividual() {
        this.individuals.add(new Chromosome(this.inputData));
        this.size ++;
    }

    public int getSize() {
        return size;
    }

    public Population(int size, InputData inputData) {
        this.size = size;
        this.inputData = inputData;
        this.individuals = new Vector<>();
        for(int i = 0; i < size; i++) {
            this.individuals.add(new Chromosome(inputData));
        }
    }

    public void updateFitness() {
        for(Chromosome chro: this.individuals) {
            chro.updateFitness();
        }
    }

    public Chromosome getBestIndividuals() {
        double best = this.individuals.get(0).getFitness();
        Chromosome res = this.individuals.get(0);
        for(Chromosome chro: this.individuals) {
            if (chro.getFitness() > best) {
                best = chro.getFitness();
                res = chro;
            }
        }
        return res;
    }

    public double getAverageFitness() {
        double res = 0;
        if (this.size == 0) return -1;
        for(Chromosome chromosome:this.individuals) {
            res += chromosome.getFitness();
        }
        return res / this.size;
    }

    public void sortByFitnetss() {
        Collections.sort(this.individuals, new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                if (o1.getFitness() < o2.getFitness()) return -1;
                if (o1.getFitness() == o2.getFitness()) return 0;
                return 1;
            }
        });
    }

}
