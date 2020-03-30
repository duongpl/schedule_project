package com.fpt.edu.schedule.model;

import com.fpt.edu.schedule.common.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Status status;
    private String content;
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Lecturer lecturer;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
}
