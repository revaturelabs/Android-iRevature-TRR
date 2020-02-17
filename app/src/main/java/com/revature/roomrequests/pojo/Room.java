package com.revature.roomrequests.pojo;

public class Room {
    private String batch;
    private String room;
    private String trainer;
    private String dates;
    private String seats;
    private boolean available;

    public Room() {
    }

    public Room(String batch, String room, String trainer, String dates, String seats, boolean available) {
        this.batch = batch;
        this.room = room;
        this.trainer = trainer;
        this.dates = dates;
        this.seats = seats;
        this.available = available;
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

    public String getSeats() { return seats; }

    public void setSeats(String seats) { this.seats = seats; }

    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }
}
