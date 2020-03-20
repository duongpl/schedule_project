package com.fpt.edu.schedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ExpectedSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserName userName;
    private int satisfactionLevel;
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "expected_id")
    private Expected expected;
    private String slotName;

}
