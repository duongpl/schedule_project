package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.repository.base.ExpectedRepository;
import com.fpt.edu.schedule.repository.base.TimetableRepository;
import com.fpt.edu.schedule.service.base.SemesterService;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    SemesterService semesterService;
    TimetableRepository timetableRepository;
    ExpectedRepository expectedRepository;


    @Override
    public Timetable save(Timetable timeTable) {
        return null;
    }

    @Override
    public Timetable findBySemester(Semester semester) {
        Timetable timetable =timetableRepository.findBySemester(semester);
        if(timetable == null){
            throw new InvalidRequestException("Don't have data for this timetable");
        }
        return timetable;
    }

    @Async
    @Override
    public void autoArrange(int semesterId,String hodGoogleId) {

//        Train train = new Train();
//        Model model = DataReader.getData();
//
//        GeneticAlgorithm ga = new GeneticAlgorithm(model, train);
//        ga.start();
        try {
            for(int i=0;i<100;i++){
                Thread.sleep(2000);
                i++;
                System.out.println("My Name " + Thread.currentThread().getName()+" Id :"+Thread.currentThread().getId()+" value i :"+i);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        }
    }

    @Override
    public void stop(int threadId) {
        Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
        System.out.println(setOfThread);

        for(Thread thread : setOfThread){
            if(thread.getId()==threadId){
                thread.interrupt();
                System.out.println("ThreadId "+thread.getId()+" stop");
                System.out.println(thread.isAlive());
                System.out.println(thread.isInterrupted());
                System.out.println(thread.isDaemon());

            }
        }
    }


}
