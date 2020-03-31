package com.fpt.edu.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDetailDTO {
    private int id;
    private String lecturerShortName;
    private String room;
    private String studentGroup;
    private String slot;
    private String subjectCode;
}
