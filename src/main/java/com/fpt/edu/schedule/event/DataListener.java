package com.fpt.edu.schedule.event;

import com.fpt.edu.schedule.ai.model.Chromosome;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class DataListener extends ApplicationEvent {
    public int data;
    private Chromosome chromosome;

    public DataListener(Object source,Chromosome chromosome) {
        super(source);
        this.chromosome =chromosome;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
