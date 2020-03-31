package com.fpt.edu.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String season;
    private String year;


    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Expected> expectedList;
    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> reportList;
    @JsonIgnore
    @OneToOne(mappedBy = "semester",cascade=CascadeType.ALL)
    private Timetable timeTable;
    public Semester(String season,String year) {
        this.season = season;
        this.year = year;
    }
    public boolean now;
}
