package com.fpt.edu.schedule.ai.lib;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Class {
    private String studentGroup;
    private int slotId;
    private int subjectId;
    private Room room;
    private int id;

    @Override
    public String toString() {
        return "(" + studentGroup + " | " + subjectId + " | " + room.getName() + ")";
    }
}
