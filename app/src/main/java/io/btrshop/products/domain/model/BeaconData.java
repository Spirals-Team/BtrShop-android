package io.btrshop.products.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by charlie on 16/12/16.
 */
public class BeaconData implements Serializable {

    @SerializedName("instanceid")
    private String instanceId;
    @SerializedName("major")
    private double major;
    @SerializedName("minor")
    private double minor;
    @SerializedName("marker")
    private Position marker;
    @SerializedName("namespaceid")
    private String namespaceId;
    @SerializedName("uuid")
    private String uuid;

    public BeaconData(String instanceId, double major, double minor, Position marker, String namespaceId, String uuid) {
        this.instanceId = instanceId;
        this.major = major;
        this.minor = minor;
        this.marker = marker;
        this.namespaceId = namespaceId;
        this.uuid = uuid;
    }

    public BeaconData(String uuid) {
        this.uuid = uuid;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public double getMajor() {
        return major;
    }

    public void setMajor(double major) {
        this.major = major;
    }

    public double getMinor() {
        return minor;
    }

    public void setMinor(double minor) {
        this.minor = minor;
    }

    public Position getMarker() {
        return marker;
    }

    public void setMarker(Position marker) {
        this.marker = marker;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
