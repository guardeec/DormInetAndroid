package com.example.guardeec.dorminet.storage;

import com.example.guardeec.dorminet.pojo.PojoPing;

/**
 * Created by Guardeec on 29.02.16.
 */
public class Statistics {
    private static Statistics ourInstance = new Statistics();

    public static Statistics getInstance() {
        return ourInstance;
    }

    private Statistics() {
    }

    private String textForUser = "disable";
    private float average = 0;
    private float mediana = 0;
    private PojoPing[] pojoPings = null;

    public String getTextForUser() {
        return textForUser;
    }

    public void setTextForUser(String textForUser) {
        this.textForUser = textForUser;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getMediana() {
        return mediana;
    }

    public void setMediana(float mediana) {
        this.mediana = mediana;
    }

    public PojoPing[] getPojoPings() {
        return pojoPings;
    }

    public void setPojoPings(PojoPing[] pojoPings) {
        this.pojoPings = pojoPings;
    }
}
