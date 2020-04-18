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

    public void setIndividuals(Vector<Chromosome> individuals) {
        this.individuals = individuals;
    }

    private Vector<Chromosome> individuals;
    private int size;

    private Model model;

    public Population(Model model) {
        this.individuals = new Vector<>();
        this.size = 0;
        this.model = model;
    }

    public void addIndividual(Chromosome chromosome) {
        this.individuals.add(chromosome);
        this.size ++;
    }

    public void addIndividual() {
        this.individuals.add(new Chromosome(this.model));
        this.size ++;
    }

    public int getSize() {
        return size;
    }

    public Population(int size, Model model) {
        this.size = size;
        this.model = model;
        this.individuals = new Vector<>();
        for(int i = 0; i < size; i++) {
            this.individuals.add(new Chromosome(model));
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
