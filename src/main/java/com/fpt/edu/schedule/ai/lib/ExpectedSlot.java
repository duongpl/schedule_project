package com.fpt.edu.schedule.ai.lib;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpectedSlot {
    private int teacherId;
    private int slotId;
    private double levelOfPreference;
}
