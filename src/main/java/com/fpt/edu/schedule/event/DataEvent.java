package com.fpt.edu.schedule.event;

import com.fpt.edu.schedule.ai.model.Chromosome;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class DataEvent extends ApplicationEvent {
    public String status;
    private Chromosome chromosome;
    int generation;

    public DataEvent(Object source, Chromosome chromosome, String status,int generation) {
        super(source);
        this.chromosome =chromosome;
        this.status =  status;
        this.generation = generation;
    }
    public DataEvent(Object source, String status) {
        super(source);
        this.chromosome =chromosome;
        this.status =  status;
    }


}
