package com.fpt.edu.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpectedView {
    private String lecturerName;
    private List<TimetableDetailDTO> timetable ;

}
