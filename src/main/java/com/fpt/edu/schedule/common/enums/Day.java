package com.fpt.edu.schedule.common.enums;

public enum Day {
    MON("ROLE_ADMIN"),
    TUE("ROLE_USER"),
    WED("ROLE_ADMIN"),
    THU("ROLE_USER"),
    FRI("ROLE_ADMIN"),
    SAT("ROLE_USER"),
    SUN("ROLE_ADMIN");


    private String name;

    Day(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
