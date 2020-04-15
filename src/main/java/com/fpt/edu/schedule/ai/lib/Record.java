package com.fpt.edu.schedule.ai.lib;

public class Record {
    private int teacherId, classId, subjectId, slotId;

    public Record(int teacherId, int classId, int subjectId, int slotId) {
        this.teacherId = teacherId;
        this.classId = classId;
        this.subjectId = subjectId;
        this.slotId = slotId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }
}
