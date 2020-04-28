package com.fpt.edu.schedule.ai.lib;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Class {
    public static final int OK = 1;
    public static final int NOT_OK = 0;
    private String studentGroup;
    private int slotId;
    private int subjectId;
    private Room room;
    private int id;
    private int status;

    @Override
    public String toString() {
        return "(" + studentGroup + " | " + subjectId + " | " + room.getName() + ")";
    }
}
