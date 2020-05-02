package com.fpt.edu.schedule.ai.lib;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Teacher {
    public final static int FULL_TIME = 1;
    public final static int PART_TIME = 0;
    private String email, name;
    private int id;
    private int type;
    private int expectedNumberOfClass;
    private int consecutiveSlotLimit;
    private int quota;

    public Teacher(String email, String name, int id, int type) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public Teacher(String email, String name, int id) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.type = PART_TIME;
    }

}
