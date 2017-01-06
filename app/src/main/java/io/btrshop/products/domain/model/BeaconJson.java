package io.btrshop.products.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by charlie on 6/01/17.
 */

public class BeaconJson {

    @SerializedName("dist")
    private double dist;

    @SerializedName("uuid")
    private String uuid;



    public BeaconJson(String uuid, double distance) {
        this.uuid = uuid;
        this.dist = distance/1000;

    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
