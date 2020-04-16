package com.fpt.edu.schedule.ai.lib;

public class Slot {
    private String name;
    private int id;
    private int type;
    private int order;
    private int numberSlotPerWeek;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getNumberSlotPerWeek() {
        return numberSlotPerWeek;
    }

    public void setNumberSlotPerWeek(int numberSlotPerWeek) {
        this.numberSlotPerWeek = numberSlotPerWeek;
    }

    public Slot(String name, int id) {
        this.name = name;
        this.id = id;
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
}
