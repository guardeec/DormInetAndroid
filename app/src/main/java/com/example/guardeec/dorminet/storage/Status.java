package com.example.guardeec.dorminet.storage;

/**
 * Created by Guardeec on 27.02.16.
 */
public class Status {
    private static Status ourInstance = new Status();

    public static Status getInstance() {
        return ourInstance;
    }

    private Integer statusId =0;
    private String status = "Connection lost";

    private Status() {
    }

    public Integer getStatusId() {
        return statusId;
    }
    public String getStatus(){
        return status;
    }

    public void setStatusOn(String mac) {
        this.statusId = 2;
        this.status = "MAC: "+mac;
    }

    public void setStatusOff() {
        this.statusId = 0;
        this.status = "Connection lost";
    }

    public void setStatusWork() {
        this.statusId = 1;
        this.status = "Spoofing in progress";
    }
}
