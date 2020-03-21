package com.fpt.edu.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ExpectedSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int satisfactionLevel;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "expected_id")
    private Expected expected;
    private String slotName;

}
