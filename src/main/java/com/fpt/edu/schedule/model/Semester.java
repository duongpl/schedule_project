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
    private List<Request> requestList;
    @JsonIgnore
    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    private List<Timetable> timeTable;
    @JsonIgnore
    @OneToMany(mappedBy = "semester", cascade = CascadeType.ALL)
    private List<Confirmation> confirmations;

    public Semester(String season, String year, boolean now) {
        this.season = season;
        this.year = year;
        this.now = now;
    }
    public boolean now;
    @Transient
    private boolean hasData;

    public boolean isHasData() {
        if(this.timeTable.size() >0 ){
           return true;
        }
        return false;
    }


}
