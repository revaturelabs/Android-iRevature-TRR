package com.revature.roomrequests.pojo;

public class Room {
    private String batch;
    private String room;
    private String trainer;
    private String dates;

    public Room() {
    }

    public Room(String batch, String room, String trainer, String dates) {
        this.batch = batch;
        this.room = room;
        this.trainer = trainer;
        this.dates = dates;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}
