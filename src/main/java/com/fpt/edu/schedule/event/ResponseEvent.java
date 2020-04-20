package com.fpt.edu.schedule.event;

import com.fpt.edu.schedule.ai.model.Chromosome;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class ResponseEvent extends ApplicationEvent {
    public String status;
    private Chromosome population;
    int generation;

    public ResponseEvent(Object source, Chromosome population, String status,int generation) {
        super(source);
        this.population =population;
        this.status =  status;
        this.generation = generation;
    }

}
