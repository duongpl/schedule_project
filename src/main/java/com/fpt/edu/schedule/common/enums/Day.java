package com.fpt.edu.schedule.common.enums;

public enum Day {
    MON("Monday"),
    TUE("Tuesday"),
    WED("Wednesday"),
    THU("Thursday"),
    FRI("Friday"),
    SAT("Saturday"),
    SUN("Sunday");


    private String name;

    Day(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
