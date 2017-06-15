package io.btrshop.detailsproduct.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by denis on 03/11/2016.
 */

/**
 *  Class for retaining a quantitative value
 *  A point value or interval for product characteristics and other purposes.
 *  Match with Quantitative value schema
 *  Refer to https://schema.org/QuantitativeValue
 */
public class QuantitativeValue implements Serializable {



    /*@SerializedName("minValue")
    private String minValue;
    @SerializedName("maxValue")
    private String maxValue;*/

    @SerializedName("unitCode")
    private String unitCode;
    @SerializedName("unitText")
    private String unitText;
    @SerializedName("value")
    private double value;


    public QuantitativeValue() {

    }

    public QuantitativeValue(String unitCode, String unitText, double value) {
        this.unitCode = unitCode;
        this.unitText = unitText;
        this.value = value;
    }

    public QuantitativeValue(String unitText, double value) {
        this.unitText = unitText;
        this.value = value;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitText() {
        return unitText;
    }

    public void setUnitText(String unitText) {
        this.unitText = unitText;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuantitativeValue that = (QuantitativeValue) o;

        if (Double.compare(that.value, value) != 0) return false;
        if (unitCode != null ? !unitCode.equals(that.unitCode) : that.unitCode != null)
            return false;
        return unitText != null ? unitText.equals(that.unitText) : that.unitText == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = unitCode != null ? unitCode.hashCode() : 0;
        result = 31 * result + (unitText != null ? unitText.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return value + " " + unitText;
    }
}
