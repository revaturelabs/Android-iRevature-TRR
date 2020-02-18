package com.revature.roomrequests.pojo;

import java.io.Serializable;

public class Location implements Serializable {
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
        return "Location: " + state + " > " + campus + " > " + building;
    }
}
