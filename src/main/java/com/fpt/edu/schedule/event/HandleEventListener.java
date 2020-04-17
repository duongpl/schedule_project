package com.fpt.edu.schedule.event;

import com.fpt.edu.schedule.ai.lib.Record;
import com.fpt.edu.schedule.ai.model.Chromosome;
import com.fpt.edu.schedule.repository.base.LecturerRepository;
import com.fpt.edu.schedule.repository.base.TimetableDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
@AllArgsConstructor
public class HandleEventListener {
    TimetableDetailRepository timetableDetailRepository;
    LecturerRepository lecturerRepository;


    @Async
    @EventListener
    public void dataEventListener(DataListener dataListener) throws InterruptedException {

        Thread.sleep(100);
        Chromosome chromosome = dataListener.getChromosome();
        Vector<Record> records =chromosome.getSchedule();
//        records.forEach(i->{
//            TimetableDetail timetableDetail =timetableDetailRepository.findById(i.getClassId());
//            timetableDetail.setLecturer(lecturerRepository.findById(i.getTeacherId()));
//            timetableDetailRepository.save(timetableDetail);
//        });
//    System.out.println("done");


    }
}
