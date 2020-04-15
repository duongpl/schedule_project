package com.fpt.edu.schedule.ai.model;


import com.fpt.edu.schedule.ai.data.DataReader;

public class Train {
    public static final int M = 5; //teacher size
    public static final int K = 20; //subject size
    public static final int N = 37; //class size



    public Train() {

    }

    public void notify(Chromosome c, double bestFitness, double avgFitness, int violation) {

    }

    public static void main(String[] args) {

//        Model model = DataGenerator.Gendata(M, K, N);

        Train train = new Train();
        Model model = DataReader.getData();

//        for(Subject subject:model.getSubjects()) {
//            System.out.println(subject.getName());
//        }
        GeneticAlgorithm ga = new GeneticAlgorithm(model, train);
        ga.start();
    }
}

