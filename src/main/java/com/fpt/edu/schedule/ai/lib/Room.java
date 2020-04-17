package com.fpt.edu.schedule.ai.lib;

public class Room {
    private String name;
    private int roomeType;
    private String building;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Room(String name, int roomeType, String building) {
        this.name = name;
        this.roomeType = roomeType;
        this.building = building;
    }

    public Room(String name) {
        this.name = name;
        if(name.contains("AL")){
            this.building = "AL";
        } else {
            this.building = "BE";
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double distance(Room r1) {
//        System.out.println(this.getBuilding() + " " + r1.getBuilding());
        if (this.getName().equalsIgnoreCase(r1.getName())) {
            return 0;
        }
        if (this.getBuilding().equalsIgnoreCase(r1.getBuilding())) return 1;
        return 5;
    }

    public int getRoomeType() {
        return roomeType;
    }

    public void setRoomeType(int roomeType) {
        this.roomeType = roomeType;
    }
}
