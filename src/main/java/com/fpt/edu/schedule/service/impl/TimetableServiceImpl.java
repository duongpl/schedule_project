package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.repository.base.ExpectedRepository;
import com.fpt.edu.schedule.repository.base.TimetableRepository;
import com.fpt.edu.schedule.service.base.SemesterService;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    SemesterService semesterService;
    TimetableRepository timetableRepository;
    ExpectedRepository expectedRepository;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

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
    public String autoArrange(int semesterId,String googleId) {

        Thread.currentThread().setName(googleId);

        try {
            for(int i=0;i<10000000;i++){
                Thread.sleep(1000);
                Thread.currentThread().setName(googleId);
                if(i%10==0){
                    hello();
                }
                i++;
                System.out.println("Thread of user " + Thread.currentThread().getName()+" value i :"+i);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        }
        return "abc";
    }
    private void hello(){
        System.out.println("hello");
    }

    @Override
    public void stop(String lecturerId) {
        Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
        for(Thread thread : setOfThread){
            if(thread.getName().equals(lecturerId)){
                thread.interrupt();
                System.out.println("Thead of user "+thread.getName()+" stop");


               // xu ly su kien trong nay

            }
        }
    }


}
