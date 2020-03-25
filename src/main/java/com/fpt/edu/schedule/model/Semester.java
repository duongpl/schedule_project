package com.fpt.edu.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Expected> expectedList;
    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Schedule> schedules;
    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> reportList;

}
