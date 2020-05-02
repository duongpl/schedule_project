package com.fpt.edu.schedule.ai.lib;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpectedSubject {
    private int teacherId;
    private int subjectId;
    private double levelOfPreference;
}
