package com.fpt.edu.schedule.ai.lib;

public class Class {
    private String studentGroup;
    private Slot slot;
    private Subject subject;
    private Room room;
    private int id;

    public Class(String studentGroup, Slot slot, Subject subject, Room room, int id) {
        this.studentGroup = studentGroup;
        this.slot = slot;
        this.subject = subject;
        this.room = room;
        this.id = id;
    }

    public String getStudentGroup() {
        return studentGroup;
    }

    public void setStudentGroup(String studentGroup) {
        this.studentGroup = studentGroup;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "(" + studentGroup + " | " + subject.getName() + " | " + room.getName() + ")";
    }
}
