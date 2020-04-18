package com.fpt.edu.schedule.event;

import com.fpt.edu.schedule.ai.model.Chromosome;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class ResponseEvent extends ApplicationEvent {
    int number;
    Chromosome chromosome;
    public ResponseEvent(Object source,int number,Chromosome chromosome) {
        super(source);
        this.number = number;
        this.chromosome =chromosome;
    }
}
