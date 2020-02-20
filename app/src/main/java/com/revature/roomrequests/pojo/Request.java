package com.revature.roomrequests.pojo;

public class Request {

    private int id;
    private String dates;
    private Room room1;
    private Room room2;
    private String reasonForRequest;
    private String status;
    private String dateMade;

    public Request() { }

    public Request(int id, String dates, Room room1, Room room2, String reasonForRequest, String status) {
        this.id = id;
        this.dates = dates;
        this.room1 = room1;
        this.room2 = room2;
        this.reasonForRequest = reasonForRequest;
        this.status = status;
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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getDates() { return dates; }

    public void setDates(String dates) { this.dates = dates; }

    public String getReasonForRequest() { return reasonForRequest; }

    public void setReasonForRequest(String reasonForRequest) { this.reasonForRequest = reasonForRequest; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getDateMade() { return dateMade; }

    public void setDateMade(String dateMade) { this.dateMade = dateMade; }
}
