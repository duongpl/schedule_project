package com.fpt.edu.schedule.model;

import com.fpt.edu.schedule.common.enums.TimetableStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private TimetableStatus status;
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
    private String reason;
    private boolean confirmed = false;
    public Confirmation(TimetableStatus status, Lecturer lecturer, Semester semester) {
        this.status = status;
        this.lecturer = lecturer;
        this.semester = semester;
    }
}
