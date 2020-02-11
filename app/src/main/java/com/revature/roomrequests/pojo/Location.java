package com.revature.roomrequests.pojo;

public class Location {
    private String state;
    private String campus;
    private String building;

    public Location() {
    }

    public Location(String state, String campus, String building) {
        this.state = state;
        this.campus = campus;
        this.building = building;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return state + " - " + campus + " - " + building;
    }
}
