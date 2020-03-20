package com.fpt.edu.schedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class ExpectedSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int satisfactionLevel;
    @ManyToOne
    @JoinColumn(name = "expected_id")
    private Expected expected;
    private String subjectCode;

}
