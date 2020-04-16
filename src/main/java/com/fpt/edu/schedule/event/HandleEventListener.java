package com.fpt.edu.schedule.event;

import com.fpt.edu.schedule.repository.base.LecturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HandleEventListener {
    LecturerRepository lecturerRepository;

@Async
    @EventListener
    public void dataEventListener(DataListener dataListener) throws InterruptedException {

        Thread.sleep(1000);
        System.out.println(String.format("Thread :%s  data:%s", Thread.currentThread().getName(), dataListener.getData()));
        System.out.println(lecturerRepository.findById(1).getEmail());
    }
}
