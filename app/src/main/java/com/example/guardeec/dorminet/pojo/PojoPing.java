package com.example.guardeec.dorminet.pojo;

import java.util.Date;

/**
 * Created by Guardeec on 29.02.16.
 */
public class PojoPing {
    Date date = new Date();
    Double ping;
    Boolean type;

    public PojoPing(Date date, Double ping, Boolean type) {
        if (type){
            this.ping = ping;
        }else {
            this.ping = null;
        }
        this.date = date;

        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public Double getPing() {
        return ping;
    }

    public Boolean getType() {
        return type;
    }
}
