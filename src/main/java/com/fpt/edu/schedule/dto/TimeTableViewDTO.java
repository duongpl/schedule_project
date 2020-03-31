package com.fpt.edu.schedule.dto;

import com.fpt.edu.schedule.common.enums.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableViewDTO {
    Day day;
    TimetableEdit timetableEdit;
}
