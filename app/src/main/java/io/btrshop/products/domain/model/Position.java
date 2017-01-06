package io.btrshop.products.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by charlie on 9/12/16.
 */

public class Position implements Serializable{

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    public Position(double x, double y) {
        this.lat = x;
        this.lng = y;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
