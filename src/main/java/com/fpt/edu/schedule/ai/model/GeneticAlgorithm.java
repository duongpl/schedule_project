package com.fpt.edu.schedule.ai.model;


import com.fpt.edu.schedule.ai.lib.Record;
import com.fpt.edu.schedule.ai.lib.Slot;
import com.fpt.edu.schedule.ai.lib.SlotGroup;
import com.fpt.edu.schedule.dto.Runs;
import com.fpt.edu.schedule.dto.TimetableDetailDTO;
import com.fpt.edu.schedule.dto.TimetableEdit;
import com.fpt.edu.schedule.model.TimetableDetail;
import com.fpt.edu.schedule.repository.base.LecturerRepository;
import com.fpt.edu.schedule.repository.base.TimetableDetailRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Setter
@Getter
public class GeneticAlgorithm {
    public static final int POPULATION_SIZE = 150;
    public static final double MUTATION_RATE = 0.25;
    public static final int TOURNAMENT_SIZE = 3;
    public static final int CLASS_NUMBER = 5;
    public static final double IN_CLASS_RATE = 0.9;
    @Autowired
    TimetableDetailRepository timetableDetailRepo;
    @Autowired
    LecturerRepository lecturerRepo;
    @Autowired
    ApplicationEventPublisher publisher;
    @Autowired
    Population population;
    @Autowired
    InputData inputData;
    public final int RESULT_RANGE = 29;
    private int generation;
    private boolean isRunning = true;
    private double lastFitness = -1;
    private int count = 0;
    private String lecturerId;
    private int stepGen;
    Queue genInfos =  new PriorityQueue();



    public GeneticAlgorithm(InputData inputData) {
        this.generation = 0;
        this.inputData = inputData;
        this.population = new Population(inputData.getGaParameter().getPopulationSize(), inputData);

    }

    public GeneticAlgorithm() {

    }


    @Async
    public void updateFitness() {
        this.population.updateFitness();
        this.generation++;

        System.out.println("Fitness Average: " + this.population.getAverageFitness());
        System.out.println("Best fitness: " + this.population.getBestIndividuals());
        System.out.println("Generation: " + this.generation);

        if (this.generation % this.stepGen  == 0 || this.generation == 1) {
            generateTimetable();
        }
    }

    public Chromosome selectParent() {
        Random random = new Random();
        Vector<Chromosome> candidates = new Vector<>();
        for (int i = 0; i < this.inputData.getGaParameter().getTournamentSize(); i++) {
            int idx = random.nextInt(this.inputData.getGaParameter().getPopulationSize());
            candidates.add(this.population.getIndividuals().get(idx));
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

    public Chromosome selectParentRandomly() {
        Random random = new Random();
        int idx = random.nextInt(this.inputData.getGaParameter().getPopulationSize());
        return this.population.getIndividuals().get(idx);
    }

    public Chromosome selectParent(Vector<Chromosome> individuals) {
        Random random = new Random();
        Vector<Chromosome> candidates = new Vector<>();
        for (int i = 0; i < this.inputData.getGaParameter().getTournamentSize(); i++) {
            int idx = random.nextInt(individuals.size());
            candidates.add(individuals.get(idx));
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

    public void selection1() {
        Population population1 = new Population(this.inputData);
        while (population1.getSize() < this.inputData.getGaParameter().getPopulationSize()) {
            Chromosome p1 = selectParent();
            Chromosome p2 = selectParent();
            Chromosome c1 = this.crossover(p1, p2);
            Chromosome c2 = this.crossover(p2, p1);
            population1.addIndividual(c1);
            if (population1.getSize() < this.inputData.getGaParameter().getPopulationSize()) population1.addIndividual(c2);
        }
        this.population = population1;
    }

    public void selection() {
        Population population1 = new Population(this.inputData);

        this.population.sortByFitnetss();
        Vector<Vector<Chromosome>> individualsByClass = new Vector();
        for (int i = 0; i < CLASS_NUMBER; i++) {
            individualsByClass.add(new Vector());
        }


        int classSize = POPULATION_SIZE / CLASS_NUMBER + ((POPULATION_SIZE % CLASS_NUMBER == 0) ? 0 : 1);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            int classId = i / classSize;
            individualsByClass.get(classId).add(this.population.getIndividuals().get(i));
        }

        for (int i = 0; i < CLASS_NUMBER; i++) {
            Collections.shuffle(individualsByClass.get(i));
        }


        int inClassPairNumber = (int) (POPULATION_SIZE * IN_CLASS_RATE / CLASS_NUMBER / 2);
        for (int i = 0; i < CLASS_NUMBER; i++) {
            for (int j = 0; j < inClassPairNumber; j++) {
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
        Vector<Slot> slots = SlotGroup.getSlotList(this.inputData.getSlots());
        Vector<Vector<Integer>> genes = new Vector<>();
        Random random = new Random();
        int seed = random.nextInt(Integer.MAX_VALUE);
        for (Slot slot : slots) {
            Vector<Integer> p1 = c1.getGenes().get(slot.getId());
            Vector<Integer> p2 = c2.getGenes().get(slot.getId());
            Vector<Integer> p3 = (new PMX(p1, p2, seed)).getChildren();
            genes.add(p3);
        }

        return new Chromosome(this.inputData, genes);
    }

    public void mutate() {
        Random random = new Random();
        for (int i = 0; i < this.inputData.getGaParameter().getPopulationSize(); i++) {
            for (int j = 0; j < 1; j++) {
                if (random.nextDouble() < this.inputData.getGaParameter().getMutationRate()) {
                    this.population.getIndividuals().get(i).mutate();
                }
            }
            this.population.getIndividuals().get(i).autoRepair();
        }
    }

    boolean isConverged() {
        double bestFitness = this.population.getBestIndividuals().getFitness();
        if (bestFitness == lastFitness) {
            count ++;
        } else count = 0;
        this.lastFitness =  bestFitness;
        if (count >= this.inputData.getGaParameter().getConvergenceCheckRange()) {
            generateTimetable();
            this.isRunning =false;
            return true;
        }
        return false;
    }


    @Async
    public void start() {
        if (this.inputData.getGaParameter().getPopulationSize() % 2 == 1) {
            this.inputData.getGaParameter().setPopulationSize(this.inputData.getGaParameter().getPopulationSize() + 1);
        }
        while (this.isRunning && !isConverged()) {
            this.updateFitness();
            this.selection1();
            this.mutate();
        }
    }
    public void stop() {
        this.isRunning = false;
    }


    public void generateTimetable() {
        List<TimetableDetail> timetableDetails = new ArrayList<>();
        Vector<Record> records = population.getBestIndividuals().getSchedule();
        records.stream().forEach(i -> {
            TimetableDetail timetableDetail = timetableDetailRepo.findById(i.getClassId());
            timetableDetail.setLecturer(lecturerRepo.findById(i.getTeacherId()));
            timetableDetails.add(timetableDetail);
        });
        List<TimetableDetailDTO> timetableDetailDTOS = timetableDetails.stream()
                .distinct()
                .map(i -> new TimetableDetailDTO(i.getId(), i.getLecturer() != null ? i.getLecturer().getShortName() : " NOT_ASSIGN", i.getRoom() != null ? i.getRoom().getName() : "NOT_ASSIGN",
                i.getClassName().getName(), i.getSlot().getName(), i.getSubject().getCode(),i.getLineId())).collect(Collectors.toList());
        Map<String, List<TimetableDetailDTO>> collect = timetableDetailDTOS
                .stream()
                .collect(Collectors.groupingBy(TimetableDetailDTO::getLecturerShortName));
        List<TimetableEdit> timetableEdits = collect
                .entrySet().stream()
                .map(i -> new TimetableEdit(i.getKey(), i.getValue()))
                .collect(Collectors.toList());
        timetableEdits.sort(Comparator.comparing(TimetableEdit::getRoom).reversed());
        Runs run = new Runs(this.population.getBestIndividuals().getFitness(), this.population.getAverageFitness(), this.population.getBestIndividuals().getNumberOfViolation(), 0, this.generation, this.generation, timetableEdits);

        //poll if exceed range
        if (genInfos.size() > RESULT_RANGE) {
            genInfos.poll();
        }
        genInfos.add(run);

    }
}
