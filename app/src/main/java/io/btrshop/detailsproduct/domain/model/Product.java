package io.btrshop.detailsproduct.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by charlie on 20/10/16.
 * Modified by Denis on 03/11/16.
 *
 * Immutable model class for a Product.
 */

/**
 *  Class for retaining a product
 *  Any offered product or service.
 *  Match with product schema
 *  Refer to https://schema.org/Product
 */
public final class Product implements Serializable{

    @SerializedName("brand")
    private String brand;

    @SerializedName("category")
    private String category;

    @SerializedName("color")
    private String color;

    @SerializedName("description")
    private String description;

    @SerializedName("depth")
    private QuantitativeValue depth;

    @SerializedName("ean")
    private String ean;

    @SerializedName("height")
    private QuantitativeValue height;

    @SerializedName("logo")
    private String logo;

    @SerializedName("offers")
    private Offer[] offers;

    @SerializedName("model")
    private String model;

    @SerializedName("name")
    private String name;

    @SerializedName("weight")
    private QuantitativeValue weight;

    @SerializedName("width")
    private QuantitativeValue width;


    public Product() {
    }

    public Product(String brand, String category, String color, String description, QuantitativeValue depth, String ean, QuantitativeValue height, String logo, Offer[] offers, String model, String name, QuantitativeValue weight, QuantitativeValue width) {
        this.brand = brand;
        this.category = category;
        this.color = color;
        this.description = description;
        this.depth = depth;
        this.ean = ean;
        this.height = height;
        this.logo = logo;
        this.offers = offers;
        this.model = model;
        this.name = name;
        this.weight = weight;
        this.width = width;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuantitativeValue getDepth() {
        return depth;
    }

    public void setDepth(QuantitativeValue depth) {
        this.depth = depth;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public QuantitativeValue getHeight() {
        return height;
    }

    public void setHeight(QuantitativeValue height) {
        this.height = height;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Offer[] getOffers() {
        return offers;
    }

    public void setOffers(Offer[] offers) {
        this.offers = offers;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QuantitativeValue getWeight() {
        return weight;
    }

    public void setWeight(QuantitativeValue weight) {
        this.weight = weight;
    }

    public QuantitativeValue getWidth() {
        return width;
    }

    public void setWidth(QuantitativeValue width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (brand != null ? !brand.equals(product.brand) : product.brand != null) return false;
        if (category != null ? !category.equals(product.category) : product.category != null)
            return false;
        if (color != null ? !color.equals(product.color) : product.color != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null)
            return false;
        if (depth != null ? !depth.equals(product.depth) : product.depth != null) return false;
        if (ean != null ? !ean.equals(product.ean) : product.ean != null) return false;
        if (height != null ? !height.equals(product.height) : product.height != null) return false;
        if (logo != null ? !logo.equals(product.logo) : product.logo != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(offers, product.offers)) return false;
        if (model != null ? !model.equals(product.model) : product.model != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (weight != null ? !weight.equals(product.weight) : product.weight != null) return false;
        return width != null ? width.equals(product.width) : product.width == null;

    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (depth != null ? depth.hashCode() : 0);
        result = 31 * result + (ean != null ? ean.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(offers);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        return result;
    }

    public static final List<Product> getProductsItems(){
        List<Product> listOfProduct = new ArrayList<>();
        for(int i = 1; i<=20 ; i++){
            Product a = new Product();
            a.setName("Produit n° " + i);
            listOfProduct.add(a);
        }
        return listOfProduct;
    }
}
