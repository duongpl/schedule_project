package com.fpt.edu.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private String department;
    @JsonIgnore
    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;
    public Subject(String code,String department) {
        this.code = code;
        this.department = department;
    }

}
