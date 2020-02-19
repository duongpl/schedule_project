package com.fpt.edu.schedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ExpectedNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numberOfClass;
    private String note;
    @OneToOne(cascade = CascadeType.ALL)
    private UserName userName;
    }
