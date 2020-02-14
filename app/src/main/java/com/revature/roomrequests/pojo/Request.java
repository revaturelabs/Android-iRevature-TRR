package com.revature.roomrequests.pojo;

public class Request {
    private String type;
    private Room room1;
    private Room room2;

    public Request() {
    }

    public Request(String type, Room room1, Room room2) {
        this.type = type.toLowerCase();
        this.room1 = room1;
        this.room2 = room2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type.toLowerCase();
    }

    public Room getRoom1() {
        return room1;
    }

    public void setRoom1(Room room1) {
        this.room1 = room1;
    }

    public Room getRoom2() {
        return room2;
    }

    public void setRoom2(Room room2) {
        this.room2 = room2;
    }

    public boolean isSwap() {
        return type.equals("swap");
    }

    public boolean isUnassign() {
        return type.equals("unassign");
    }

    public boolean isRequest() {
        return type.equals("request");
    }

}
