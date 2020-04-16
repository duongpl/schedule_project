package com.fpt.edu.schedule.event;

import org.springframework.context.ApplicationEvent;

public class DataListener extends ApplicationEvent {
    public int data;
    public DataListener(Object source,int data) {
        super(source);
        this.data =data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
