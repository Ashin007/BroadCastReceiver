package com.ashitech.broadcastreceiver;

/**
 * Created by Ashin on 4/1/2018.
 */

public class Data {
    private int count;
    private String number;

    public Data(int count, String number) {
        this.count = count;
        this.number = number;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
