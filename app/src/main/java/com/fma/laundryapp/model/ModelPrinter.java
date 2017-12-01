package com.fma.laundryapp.model;

/**
 * Created by fma on 7/30/2017.
 */

public class ModelPrinter {

    private String name;
    private String mac;

    public ModelPrinter(String name, String mac) {
        this.name = name;
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
