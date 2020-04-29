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
public class TimetableDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassName className;
    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;
    private int lineId;
    @Transient
    TimetableStatus timetableStatus = TimetableStatus.DRAFT;
}
