package com.fpt.edu.schedule.common.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@Setter
public class Mail extends ApplicationEvent {
    private String content;
    private List<String> receiverEmail;
    private String subject;

    public Mail(Object source, String content, List<String>receiverEmail, String subject) {
        super(source);
        this.content=content;
        this.subject=subject;
        this.receiverEmail=receiverEmail;
    }
}
