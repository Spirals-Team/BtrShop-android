package io.btrshop.products.domain.model;

/**
 * Created by charlie on 9/12/16.
 */

public class Position {

    private double lat;
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
