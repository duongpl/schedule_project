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
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL)

    private List<TimetableDetail> timetableDetailList;
    public Slot(String name) {
        this.name = name;
    }

    public Slot(int id,String name) {
        this.id = id;
        this.name = name;
    }
}
