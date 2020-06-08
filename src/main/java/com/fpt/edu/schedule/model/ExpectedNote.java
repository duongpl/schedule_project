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
public class ExpectedNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int expectedNumOfClass;
    private int maxConsecutiveSlot;
    private String note;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "expected_id")
    private Expected expected;

    public ExpectedNote(int expectedNumOfClass, int maxConsecutiveSlot, String note, Expected expected) {
        this.expectedNumOfClass = expectedNumOfClass;
        this.maxConsecutiveSlot = maxConsecutiveSlot;
        this.note = note;
        this.expected = expected;
    }
}
