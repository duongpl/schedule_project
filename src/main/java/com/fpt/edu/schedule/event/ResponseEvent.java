package com.fpt.edu.schedule.event;

import com.fpt.edu.schedule.model.TimetableDetail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;
@Getter
@Setter
public class ResponseEvent extends ApplicationEvent {

    private List<TimetableDetail> timetableDetail;

    public ResponseEvent(Object source, List<TimetableDetail> timetableDetail) {
        super(source);
        this.timetableDetail =timetableDetail;
    }

}
