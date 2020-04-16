package com.fpt.edu.schedule.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HandleEventListener {



    @EventListener
    public void dataEventListener(DataListener dataListener) throws InterruptedException {

        Thread.sleep(1000);
        System.out.println(String.format("Thread :%s  data:%s", Thread.currentThread().getName(), dataListener.getData()));
    }
}
