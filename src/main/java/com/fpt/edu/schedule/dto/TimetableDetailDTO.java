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
    private String lecturerName;
    private String room;
    private String className;
    private String slot;
    private String subjectCode;
}
