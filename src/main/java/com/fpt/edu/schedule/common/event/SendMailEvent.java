package com.fpt.edu.schedule.common.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendMailEvent {

    @Autowired
    private JavaMailSender javaMailSender;
    @Async
    @EventListener
    void sendEmail(Mail mail) {
        SimpleMailMessage msg = new SimpleMailMessage();
        for(int i=0;i<mail.getReceiverEmail().size();i++) {
            msg.setTo(mail.getReceiverEmail().get(i));
            msg.setSubject(mail.getSubject());
            msg.setText(mail.getContent());
            javaMailSender.send(msg);
        }
    }
}
