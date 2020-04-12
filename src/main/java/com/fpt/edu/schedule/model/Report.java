package com.fpt.edu.schedule.model;

import com.fpt.edu.schedule.common.enums.StatusReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

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
    private StatusReport status;
    @Type(type = "text")
    private String content;
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Lecturer lecturer;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
    @Type(type = "text")
    private String replyContent;
}
