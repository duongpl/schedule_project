package com.fpt.edu.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    Semester semester;
    boolean temp;
    @JsonIgnore

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL)
    private List<TimetableDetail> timetableDetails;

    public Timetable() {
        this.timetableDetails = new ArrayList<>();
    }
}
