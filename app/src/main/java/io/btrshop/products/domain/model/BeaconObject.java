package io.btrshop.products.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by charlie on 2/12/16.
 */

public final class BeaconObject implements Serializable{

    private double distance;

    @SerializedName("department_id")
    private String department_id;

    @SerializedName("data")
    private BeaconData data;

    @SerializedName("floor_id")
    private String floorId;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("organization_id")
    private String organization_id;

    @SerializedName("type")
    private String type;

    @SerializedName("triggerVenueChange")
    private boolean triggerVenueChange;

    @SerializedName("triggerFloorChange")
    private boolean triggerFloorChange;

    @SerializedName("geopoint")
    private Position geopoint;

    public BeaconObject(double distance, String department_id, BeaconData data, String floorId,
                        String id, String name, String placeId, String organization_id, String type,
                        boolean triggerVenueChange, boolean triggerFloorChange, Position geopoint) {
        this.distance = distance;
        this.department_id = department_id;
        this.data = data;
        this.floorId = floorId;
        this.id = id;
        this.name = name;
        this.placeId = placeId;
        this.organization_id = organization_id;
        this.type = type;
        this.triggerVenueChange = triggerVenueChange;
        this.triggerFloorChange = triggerFloorChange;
        this.geopoint = geopoint;
    }

    public BeaconObject(double distance, BeaconData beaconData) {
        this.distance = distance;
        this.data = beaconData;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public BeaconData getData() {
        return data;
    }

    public void setData(BeaconData data) {
        this.data = data;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isTriggerVenueChange() {
        return triggerVenueChange;
    }

    public void setTriggerVenueChange(boolean triggerVenueChange) {
        this.triggerVenueChange = triggerVenueChange;
    }

    public boolean isTriggerFloorChange() {
        return triggerFloorChange;
    }

    public void setTriggerFloorChange(boolean triggerFloorChange) {
        this.triggerFloorChange = triggerFloorChange;
    }

    public Position getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(Position geopoint) {
        this.geopoint = geopoint;
    }
}
