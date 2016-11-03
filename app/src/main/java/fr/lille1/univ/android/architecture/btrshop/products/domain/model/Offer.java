package fr.lille1.univ.android.architecture.btrshop.products.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by denis on 03/11/2016.
 */

public class Offer {



    @SerializedName("price")
    private double price;
    @SerializedName("priceCurrency")
    private String priceCurrency;
    @SerializedName("validFrom")
    private Date validFrom;
    @SerializedName("validThrough")
    private Date validThrough;

    public Offer() {

    }

    public Offer(double price, String priceCurrency, Date validFrom, Date validThrough) {
        this.price = price;
        this.priceCurrency = priceCurrency;
        this.validFrom = validFrom;
        this.validThrough = validThrough;
    }

    public Offer(double price, String priceCurrency, Date validFrom) {
        this.price = price;
        this.priceCurrency = priceCurrency;
        this.validFrom = validFrom;
    }

    public Offer(double price, String priceCurrency) {
        this.price = price;
        this.priceCurrency = priceCurrency;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (Double.compare(offer.price, price) != 0) return false;
        if (priceCurrency != null ? !priceCurrency.equals(offer.priceCurrency) : offer.priceCurrency != null)
            return false;
        if (validFrom != null ? !validFrom.equals(offer.validFrom) : offer.validFrom != null)
            return false;
        return validFrom != null ? validFrom.equals(offer.validFrom) : offer.validFrom == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (priceCurrency != null ? priceCurrency.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        return result;
    }
}
