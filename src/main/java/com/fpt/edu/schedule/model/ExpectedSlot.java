package com.fpt.edu.schedule.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private int levelOfPrefer;
    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Expected expected;
    @JsonProperty("name")
    private String slotName;

    public ExpectedSlot(String slotName) {
        this.slotName = slotName;
    }

    public ExpectedSlot(String slotName,int levelOfPrefer, Expected expected) {
        this.levelOfPrefer = levelOfPrefer;
        this.expected = expected;
        this.slotName = slotName;
    }
}
