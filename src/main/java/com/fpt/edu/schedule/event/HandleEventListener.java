package com.fpt.edu.schedule.event;

import com.fpt.edu.schedule.ai.model.Chromosome;
import com.fpt.edu.schedule.repository.base.LecturerRepository;
import com.fpt.edu.schedule.repository.base.TimetableDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HandleEventListener {
    TimetableDetailRepository timetableDetailRepository;
    LecturerRepository lecturerRepository;
    @Autowired
    ApplicationEventPublisher eventPublisher;


    @Async
    @EventListener
    public void dataEventListener(DataEvent dataEvent) throws InterruptedException {
        Chromosome chromosome = dataEvent.getChromosome();
        if(dataEvent.getStatus().equals("run")){
        }else {
            System.out.println("Generation stop " + dataEvent.getGeneration());
            eventPublisher.publishEvent(new ResponseEvent(this,dataEvent.getGeneration(),chromosome));
        }

//        Vector<Record> records =chromosome.getSchedule();
//        records.forEach(i->{
//            TimetableDetail timetableDetail =timetableDetailRepository.findById(i.getClassId());
//            timetableDetail.setLecturer(lecturerRepository.findById(i.getTeacherId()));
//            timetableDetailRepository.save(timetableDetail);
//        });


    }
}
