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
public class TimetableDetailDTO {
    private int id;
    private String lecturerShortName;
    private String room;
    private String className;
    private String slot;
    private String subjectCode;
    private int slotNumber;
    private Day day;
    private int lineId;

    public TimetableDetailDTO(int id, String lecturerShortName, String room, String className, String slot, String subjectCode) {
        this.id = id;
        this.lecturerShortName = lecturerShortName;
        this.room = room;
        this.className = className;
        this.slot = slot;
        this.subjectCode = subjectCode;
    }

    public TimetableDetailDTO(int id, String lecturerShortName, String room, String className, String slot, String subjectCode, int slotNumber, Day day) {
        this.id = id;
        this.lecturerShortName = lecturerShortName;
        this.room = room;
        this.className = className;
        this.slot = slot;
        this.subjectCode = subjectCode;
        this.slotNumber = slotNumber;
        this.day = day;
    }

    public TimetableDetailDTO(int id, String lecturerShortName, String room, String className, String slot, String subjectCode, int lineId) {
        this.id = id;
        this.lecturerShortName = lecturerShortName;
        this.room = room;
        this.className = className;
        this.slot = slot;
        this.subjectCode = subjectCode;
        this.lineId=lineId;
    }
}
