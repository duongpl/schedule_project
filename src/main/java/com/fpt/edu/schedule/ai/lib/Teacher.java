package com.fpt.edu.schedule.ai.lib;

public class Teacher {
    public final static int FULL_TIME = 1;
    public final static int PART_TIME = 0;
    private String email, name;
    private int id;
    private int type;

    public Teacher(String email, String name, int id, int type) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public Teacher(String email, String name, int id) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.type = PART_TIME;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
