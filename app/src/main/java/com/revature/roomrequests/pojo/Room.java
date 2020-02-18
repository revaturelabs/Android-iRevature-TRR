package com.revature.roomrequests.pojo;

public class Room {

    private int id;
    private String batch;
    private String roomNumber;
    private String trainer;
    private String dates;
    private String capacity;
    private boolean available;

    public Room() {

    }

    public Room(int id, String batch, String roomNumber, String trainer, String dates, String capacity, boolean available) {
        this.id = id;
        this.batch = batch;
        this.roomNumber = roomNumber;
        this.trainer = trainer;
        this.dates = dates;
        this.capacity = capacity;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
